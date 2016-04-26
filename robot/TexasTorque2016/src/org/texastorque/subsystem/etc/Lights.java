package org.texastorque.subsystem.etc;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights {

	private static Lights instance;

	public enum State {
		OFF, NOT_READY, LOADING, READY, DISABLED;
	}

	private State state;
	private DriverStation ds;

	public Lights() {
		state = State.OFF;
		ds = DriverStation.getInstance();
	}

	public void set(double setpoint, double value) {
		if (value >= setpoint / 2.0) {
			state = State.LOADING;
		} else if (value >= setpoint) {
			state = State.READY;
		} else {
			state = State.NOT_READY;
		}
	}

	public void off() {
		state = State.OFF;
	}

	public void disable() {
		state = State.DISABLED;
	}

	private void publish() {
		switch (state) {
		case OFF:
			// push 0
			break;
		case NOT_READY:
			if (ds.getAlliance().compareTo(Alliance.Invalid) == 0) {
				// push 0
			} else if (ds.getAlliance().compareTo(Alliance.Blue) == 0) {
				// push 1
			} else {
				// push 2
			}
			break;
		case LOADING:
			// push 3
			break;
		case READY:
			// push 4
			break;
		case DISABLED:
			// push 5
			break;
		}
	}

	public void pushToDashboard() {
		publish();
		SmartDashboard.putString("LightState", state.toString());
	}

	// singleton
	public static Lights getInstance() {
		return instance == null ? instance = new Lights() : instance;
	}
}
