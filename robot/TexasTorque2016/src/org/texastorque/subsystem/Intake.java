package org.texastorque.subsystem;

public class Intake extends Subsystem {

	private static Intake instance;

	@Override
	public void init() {
	}

	@Override
	public void run() {
	}

	@Override
	protected void output() {
	}

	@Override
	public void pushToDashboard() {
	}

	// singleton
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}
}