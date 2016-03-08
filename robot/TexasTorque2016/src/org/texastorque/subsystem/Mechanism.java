package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Mechanism extends Subsystem {
	
	private static Mechanism instance;
	
	private double mechanismSpeed;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		mechanismSpeed = input.getMechanismSpeed();
		
		output();
	}

	@Override
	protected void output() {
		output.setMechanismSpeed(mechanismSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("MechanismSpeed", mechanismSpeed);
	}
	
	//singleton
	public static Mechanism getInstance() {
		return instance == null ? instance = new Mechanism() : instance;
	}
}
