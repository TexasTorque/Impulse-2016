package org.texastorque.subsystem;

public class Drivebase extends Subsystem {
	
	private static Drivebase instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	public void init() {
	}
	
	public void run() {
		leftSpeed = input.getLeftDriveSpeed();
		rightSpeed = input.getRightDriveSpeed();
	}
	
	public void output() {
		output.setDriveSpeeds(leftSpeed, rightSpeed);
	}
	
	public void pushToDashboard() {
	}
	
	//singleton
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}
