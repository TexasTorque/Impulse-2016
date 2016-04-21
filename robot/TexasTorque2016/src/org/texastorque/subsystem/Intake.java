package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private static Intake instance;

	private double intakeSpeed;
	
	@Override
	public void initSystem() {
	}

	@Override
	public void runSystem() {
		if (input.isIntaking() || (feedback.visionShotReady()) && input.isVisionLock()) {
			intakeSpeed = 1.0;
		} else if (input.isOuttaking()) {
			intakeSpeed = -1.0;
		} else {
			intakeSpeed = 0.0;
		}
	}

	@Override
	protected void output() {
		output.setIntakeSpeed(intakeSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("IntakeSpeed", intakeSpeed);
	}

	// singleton
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}
}