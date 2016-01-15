package org.texastorque.feedback.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionManager {
	
	private static VisionManager instance;
	
	private NetworkTable visionTable;
	
	public void update() {
		visionTable = NetworkTable.getTable("visionReport");
	}
	
	public void pushToDashboard() {
		SmartDashboard.putBoolean("VisionAvaliable", visionTable.isConnected());
	}
	
	// singleton
	public static VisionManager getInstance() {
		return instance == null ? instance = new VisionManager() : instance;
	}
}
