package org.texastorque.output;

public class RobotOutput {
	private static RobotOutput instance;
	
	//singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
