package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flashlight extends Subsystem {
	
	private static Flashlight instance;

	private boolean on;

	@Override
	public void init() {
	}

	@Override
	public void runSystem() {
		on = input.isFlashlightOn();
	}

	@Override
	protected void output() {
		output.setFlashlight(on);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("Flashlight", on);
	}
	
	//singleton
	public static Flashlight getInstance() {
		return instance == null ? instance = new Flashlight() : instance;
	}
}
