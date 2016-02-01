package org.texastorque.feedback;

import org.texastorque.constants.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class VisionFeedback {
	
	private static VisionFeedback instance;
	private static double WIDTH = Constants.V_WIDTH.getDouble() / 2.0;
	private static double HEIGHT = Constants.V_HEIGHT.getDouble() / 2.0;
	private static double FOV = Constants.V_CAMERA_FOV.getDouble() / 2.0;
	
	private ITable visionTable;
	private double goalCenterX;
	private double goalCenterY;
	private double yaw;
	private double pitch;
	
	public VisionFeedback() {
		visionTable = NetworkTable.getTable("GRIP").getSubTable("visionTable");
	}
	
	public void update() {
		goalCenterX = visionTable.getNumber("centerX", WIDTH);
		goalCenterY = visionTable.getNumber("centerY", HEIGHT);
		
		yaw = ((goalCenterX / WIDTH) - 1) * FOV;
		pitch = ((goalCenterY / HEIGHT) - 1) * FOV;
	}
	
	public double getYaw() {
		return yaw;
	}
	
	public double getPitch() {
		return pitch;
	}
	
	//singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}
