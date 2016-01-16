package org.texastorque.feedback.vision;

import org.texastorque.constants.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionManager {

	private static VisionManager instance;

	private final NetworkTable visionTable;
	private final double pitchAngleRange;
	private final double yawAngleRange;
	
	//input values
	private double centerX;
	private double centerY;
	private double width;
	private double height;
	
	//calculated values
	private double necessaryPitch;
	private double necessaryYaw;

	public VisionManager() {
		visionTable = NetworkTable.getTable("visionReport");
		pitchAngleRange = Constants.CAMERA_MAX_PITCH_FOV_ANGLE.getDouble() - Constants.CAMERA_MIN_PITCH_FOV_ANGLE.getDouble();
		yawAngleRange = Constants.CAMERA_MAX_YAW_FOV_ANGLE.getDouble() - Constants.CAMERA_MIN_YAW_FOV_ANGLE.getDouble();
	}

	public void update() {
		centerX = visionTable.getNumber("centerX", 0.0);
		centerY = visionTable.getNumber("centerY", 0.0);
		width = visionTable.getNumber("width", 0.0);
		height = visionTable.getNumber("height", 0.0);
		
		necessaryPitch = ((centerY / height) * pitchAngleRange) + pitchAngleRange;
		necessaryYaw = ((centerX / width) * yawAngleRange) + yawAngleRange;
	}
	
	public double getNecessaryPitch() {
		return necessaryPitch;
	}
	
	public double getNecessaryYaw() {
		return necessaryYaw;
	}

	public void pushToDashboard() {
		SmartDashboard.putBoolean("VisionAvaliable", visionTable.isConnected());
		SmartDashboard.putNumber("VisionCenterX", centerX);
		SmartDashboard.putNumber("VisionCenterY", centerY);
	}

	// singleton
	public static VisionManager getInstance() {
		return instance == null ? instance = new VisionManager() : instance;
	}
}
