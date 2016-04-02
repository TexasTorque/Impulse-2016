package org.texastorque.input;

import org.texastorque.constants.Constants;

public abstract class Input {

	// drivebase
	protected double leftDriveSpeed = 0.0;
	protected double rightDriveSpeed = 0.0;

	protected double driveSetpoint = 0.0;
	protected double turnSetpoint = 0.0;
	protected double tiltSetpoint = Constants.S_DOWN_SETPOINT.getDouble();

	// brakeing
	protected boolean braking = false;

	// override variables
	protected boolean override = false;
	protected boolean visionLock = false;
	protected boolean overrideReset = false;

	// intake variables
	protected boolean intaking = false;
	protected boolean outtaking = false;

	// conveyor variables
	protected boolean conveyorIntaking = false;
	protected boolean conveyorOuttaking = false;

	// shooter variable
	protected boolean flywheelActive = false;
	protected boolean layupShot = false;
	protected boolean batterShot = false;
	protected boolean longShot = false;

	protected double tiltMotorSpeed = 0.0;

	// compression testing
	protected boolean compressionTesting = false;

	// arm
	protected boolean armUp = true;
	protected double armSetpoint = 0.0;
	protected double armSpeed = 0.0;

	// flashlight
	protected boolean flashlight = false;

	public abstract void update();

	public boolean getCompressionTesting() {
		return compressionTesting;
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

	public double getTiltSetpoint() {
		return tiltSetpoint;
	}

	public double getArmSetpoint() {
		return armSetpoint;
	}

	public boolean isLayupShot() {
		return layupShot;
	}

	public boolean isBatterShot() {
		return batterShot;
	}

	public boolean isLongShot() {
		return longShot;
	}

	public boolean isShooting() {
		return longShot || batterShot || layupShot;
	}

	public boolean isOverride() {
		return override;
	}
	
	public boolean isArmUp() {
		return armUp;
	}

	public boolean isVisionLock() {
		return visionLock;
	}

	public double getTiltOverrideSpeed() {
		return tiltMotorSpeed;
	}

	public double getArmOverrideSpeed() {
		return armSpeed;
	}

	public boolean isBraking() {
		return braking;
	}

	public boolean isIntaking() {
		return intaking;
	}

	public boolean isOuttaking() {
		return outtaking;
	}

	public boolean isConveyorIntaking() {
		return conveyorIntaking;
	}

	public boolean isConveyorOuttaking() {
		return conveyorOuttaking;
	}

	public boolean isFlywheelActive() {
		return flywheelActive;
	}

	public boolean isOverrideReset() {
		return overrideReset;
	}

	public boolean isFlashlightOn() {
		return flashlight;
	}
}
