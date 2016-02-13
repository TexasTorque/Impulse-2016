package org.texastorque.input;

public abstract class Input {

	// dynamic variables
	// drivebase
	protected boolean driveBoost;
	protected double leftDriveSpeed = 0.0;
	protected double rightDriveSpeed = 0.0;

	protected double driveSetpoint = 0.0;
	protected double turnSetpoint = 0.0;

	// override variables
	protected boolean override = false;
	protected boolean visionLock = false;

	// intake variables
	protected double intakeSpeed = 0.0;

	// shooter variable
	protected boolean flywheelActive;

	protected double leftTiltMotorSpeed = 0.0;
	protected double rightTiltMotorSpeed = 0.0;

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

	public boolean isDriveBoost() {
		return driveBoost;
	}

	public boolean isOverride() {
		return override;
	}

	public boolean isVisionLock() {
		return visionLock;
	}

	public double getIntakeSpeed() {
		return intakeSpeed;
	}

	public double getLeftTiltMotorSpeed() {
		return leftTiltMotorSpeed;
	}

	public double getRightTiltMotorSpeed() {
		return rightTiltMotorSpeed;
	}

	public boolean isFlywheelActive() {
		return flywheelActive;
	}
}
