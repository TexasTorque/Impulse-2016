package org.texastorque.subsystem.etc;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights {

	private static Lights instance;

	public enum State {
		OFF(0), NOT_READY_RED(1), NOT_READY_BLUE(2), LOADING(3), READY(4), PARTY(5), PANIC(6);

		public final int value;

		State(int _value) {
			value = _value;
		}
	}

	private State state;
	private DriverStation ds;
	private SerialPort arduino;

	public Lights() {
		arduino = new SerialPort(9600, Port.kUSB);
		arduino.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
		arduino.setTimeout(0.1);// necessary?

		ds = DriverStation.getInstance();
		off();
	}

	public void set(double value, double setpoint) {
		if (value > setpoint) {
			state = State.READY;
		} else if (value > setpoint / 2.0) {
			state = State.LOADING;
		} else {
			if (Timer.getMatchTime() > 120) {
				state = State.PANIC;
			} else if (ds.getAlliance() == Alliance.Red) {
				state = State.NOT_READY_RED;
			} else if (ds.getAlliance() == Alliance.Blue) {
				state = State.NOT_READY_BLUE;
			} else {
				state = State.OFF;
			}
		}
	}

	public void off() {
		state = State.OFF;
	}

	public void party() {
		state = State.PARTY;
	}

	public void update() {
		arduino.writeString("" + state.value);
		SmartDashboard.putString("LightState", state.toString());
	}

	// singleton
	public static Lights getInstance() {
		return instance == null ? instance = new Lights() : instance;
	}
}
