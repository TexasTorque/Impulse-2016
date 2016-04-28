package org.texastorque.subsystem.etc;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jssc.SerialPort;
import jssc.SerialPortList;

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
		for (String s : SerialPortList.getPortNames()) {
			System.out.println(s);
		}
		arduino = new SerialPort("/dev/ttyS0");
		try {
			arduino.openPort();
			arduino.setParams(9600, 8, 1, 0);
			Thread.sleep(100);// necessary?
			System.out.println("Connected to serial " + arduino.getPortName());
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		try {
			arduino.writeString("" + state.value);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		SmartDashboard.putString("LightState", state.toString());
	}

	// singleton
	public static Lights getInstance() {
		return instance == null ? instance = new Lights() : instance;
	}
}
