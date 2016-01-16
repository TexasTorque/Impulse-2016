package org.texastorque.feedback.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionManager {

	private static VisionManager instance;

	private NetworkTable visionTable;

	private double centerX;
	private double centerY;

	public VisionManager() {
		visionTable = NetworkTable.getTable("visionReport");
	}

	public void update() {
		centerX = visionTable.getNumber("centerX", 0.0);
		centerY = visionTable.getNumber("centerY", 0.0);
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
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
