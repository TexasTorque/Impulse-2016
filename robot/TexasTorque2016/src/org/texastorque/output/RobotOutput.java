package org.texastorque.output;

public class RobotOutput {
	private static RobotOutput instance;
	
	private static final boolean OUTPUT_MUTED = false;
	
	public void setDriveSpeeds(double left, double right) {
		if (OUTPUT_MUTED) {
			return;
		}
	}
	
	//singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
