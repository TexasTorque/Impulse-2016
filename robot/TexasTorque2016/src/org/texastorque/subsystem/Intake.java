package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private static Intake instance;

	private double topSpeed;
	private double bottomSpeed;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		topSpeed = input.getTopIntakeSpeed();
		bottomSpeed = input.getBottomIntakeSpeed();
	}

	@Override
	protected void output() {
		output.setIntakeSpeed(topSpeed, bottomSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("TopIntakeSpeed", topSpeed);
		SmartDashboard.putNumber("BottomIntakeSpeed", bottomSpeed);
	}

	// singleton
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}
}