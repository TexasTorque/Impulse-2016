package org.texastorque.input;

public abstract class Input {

	// dynamic variables
	// drivebase
	protected double leftDriveSpeed = 0.0;
	protected double rightDriveSpeed = 0.0;

	protected double driveSetpoint = 0.0;
	protected double turnSetpoint = 0.0;

	// override variables
	protected boolean override = false;
	protected boolean visionLock = false;
	
	public abstract void update();

	public double getLeftDriveSpeed() {
		return leftDriveSpeed;
	}

	public double getRightDriveSpeed() {
		return rightDriveSpeed;
	}

	public double getDriveSetpoint() {
		return driveSetpoint;
	}
	
	public double getTurnSetpoint() {
		return turnSetpoint;
	}

	public boolean isOverride() {
		return false;
	}
	
	public boolean isVisionLock() {
		return visionLock;
	}
}
