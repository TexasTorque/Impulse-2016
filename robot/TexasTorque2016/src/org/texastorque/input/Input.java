package org.texastorque.input;

public abstract class Input {

	// drivebase
	protected double leftDriveSpeed = 0.0;
	protected double rightDriveSpeed = 0.0;

	protected double driveSetpoint = 0.0;
	protected double turnSetpoint = 0.0;
	protected double tiltSetpoint = -5.0;

	// brakeing
	protected boolean braking = false;

	// override variables
	protected boolean override = false;
	protected boolean visionLock = false;
	protected boolean prevVisionLock = false;

	// intake variables
	protected boolean intaking = false;
	protected boolean outtaking = false;

	// conveyor variables
	protected boolean conveyorIntaking = false;
	protected boolean conveyorOuttaking = false;

	// shooter variable
	protected boolean flywheelActive = false;
	protected boolean layupShot = false;
	protected boolean longShot = false;

	protected double tiltMotorSpeed = 0.0;
	
	// compression testing
	protected boolean compressionTesting = false;
	
	// mechanism
	protected double mechanismSetpoint = 1.0;
	protected double mechanismSpeed = 0.0;
	protected boolean mechanismHold;
	
	public abstract void update();

	public boolean getCompressionTesting(){
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
	
	public double getMechanismSetpoint() {
		return mechanismSetpoint;
	}

	public boolean isLayupShot() {
		return layupShot;
	}

	public boolean isLongShot() {
		return longShot;
	}
	
	public boolean isMechanismHold() {
		return mechanismHold;
	}

	public boolean isOverride() {
		return override;
	}

	public boolean isVisionLock() {
		return visionLock;
	}

	public double getTiltOverrideSpeed() {
		return tiltMotorSpeed;
	}
	
	public double getMechanismOverrideSpeed() {
		return mechanismSpeed;
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
}
