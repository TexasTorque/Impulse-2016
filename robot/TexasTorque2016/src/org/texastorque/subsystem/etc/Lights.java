package org.texastorque.subsystem.etc;

import org.texastorque.subsystem.Subsystem;

public class Lights extends Subsystem {

	public enum State {
		READY, LOADING, NOT_READY;
	}

	private static Lights instance;

	private State state;

	@Override
	protected void initSystem() {
		state = State.NOT_READY;
	}

	@Override
	protected void runSystem() {
	}

	@Override
	protected void output() {
	}

	@Override
	public void pushToDashboard() {
	}

	// singleton
	public static Lights getInstance() {
		return instance == null ? instance = new Lights() : instance;
	}
}
