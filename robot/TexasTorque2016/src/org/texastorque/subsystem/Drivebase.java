package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		SmartDashboard.putNumber("DrivebaseLeftSpeed", leftSpeed);
		SmartDashboard.putNumber("DrivebaseRightSpeed", rightSpeed);
	}
	
	//singleton
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}
