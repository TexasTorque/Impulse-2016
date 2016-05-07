package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends Subsystem {

	private static Pneumatics instance;

	private boolean braking;
	private boolean other;

	@Override
	public void initSystem() {
	}

	@Override
	public void runSystem() {
		braking = input.isBraking();
		other = input.isOther();
	}

	@Override
	protected void output() {
		output.setBrakes(braking);
		output.setCompressionTesting(other);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("Braking", braking);
		SmartDashboard.putBoolean("Other", other);
	}

	public static Pneumatics getInstance() {
		return instance == null ? instance = new Pneumatics() : instance;
	}

}
