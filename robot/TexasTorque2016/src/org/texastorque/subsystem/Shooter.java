package org.texastorque.subsystem;

public class Shooter extends Subsystem {

	private static Shooter instance;

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
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}