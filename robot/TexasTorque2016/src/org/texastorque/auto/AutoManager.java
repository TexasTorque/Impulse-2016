package org.texastorque.auto;

import org.texastorque.auto.modes.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;

	private final int DO_NOTHING_AUTO = 0;
	private final int DRIVE_FORWARD_AUTO = 1;
	private final int TURN_AUTO = 2;
	private final int TILT_AUTO = 3;

	private AutoMode currentMode;

	public void reset() {
		if (currentMode != null) {
			currentMode.interrupt();
		}
		SmartDashboard.putNumber("AutoMode", 0);
		SmartDashboard.putString("RunningAutoMode", "N/A");
	}

	public AutoMode createAutoMode() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");

		switch (dashboardMode) {
		default:
		case DO_NOTHING_AUTO:
			currentMode = new DoNothingAuto();
			SmartDashboard.putString("RunningAutoMode", currentMode.getName());
			return currentMode;
		case DRIVE_FORWARD_AUTO:
			currentMode = new DriveForwardAuto();
			SmartDashboard.putString("RunningAutoMode", currentMode.getName());
			return currentMode;
		case TURN_AUTO:
			currentMode = new TurnAuto();
			SmartDashboard.putString("RunningAutoMode", currentMode.getName());
			return currentMode;
		case TILT_AUTO:
			currentMode = new TiltAuto();
			SmartDashboard.putString("RunningAutoMode", currentMode.getName());
			return currentMode;
		}
	}

	public void runAutoMode() {
		currentMode.start();
	}

	// singleton
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
}
