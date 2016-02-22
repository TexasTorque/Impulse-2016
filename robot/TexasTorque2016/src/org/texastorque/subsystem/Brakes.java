package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Brakes extends Subsystem {

	private static Brakes instance;

	private boolean braking;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		braking = input.isBraking();
	}

	@Override
	protected void output() {
		output.setBrakes(braking);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("Braking", braking);
	}

	public static Brakes getInstance() {
		return instance == null ? instance = new Brakes() : instance;
	}

}
