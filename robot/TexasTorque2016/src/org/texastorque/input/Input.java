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

	public void resetSetpoints() {
		driveSetpoint = 0.0;
		turnSetpoint = 0.0;
	}

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
		return override;
	}

	public boolean isVisionLock() {
		return visionLock;
	}
}
