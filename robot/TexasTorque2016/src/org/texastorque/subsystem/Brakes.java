package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Brakes extends Subsystem {

	private static Brakes instance;

	private boolean brakeing;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		brakeing = input.isBrakeing();
	}

	@Override
	protected void output() {
		output.setBrakes(brakeing);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("Brakeing", brakeing);
	}

	public static Brakes getInstance() {
		return instance == null ? instance = new Brakes() : instance;
	}

}
