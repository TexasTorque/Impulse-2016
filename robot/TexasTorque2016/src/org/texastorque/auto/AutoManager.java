package org.texastorque.auto;

import org.texastorque.auto.modes.DoNothingAuto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;

	private final int DO_NOTHING_AUTO = 0;
	private final int DRIVE_FORWARD_AUTO = 1;

	private AutoMode currentMode;

	public void init() {
		if (currentMode != null) {
			currentMode.interrupt();
		}
		SmartDashboard.putNumber("AutoMode", 0);
		SmartDashboard.putString("RunningAutoMode", "N/A");
	}

	public AutoMode runAutoMode() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");

		switch (dashboardMode) {
		default:
		case DO_NOTHING_AUTO:
			SmartDashboard.putString("RunningAutoMode", "DoNothingAuto");
			currentMode = new DoNothingAuto();
			currentMode.run();
			return currentMode;
		case DRIVE_FORWARD_AUTO:
			SmartDashboard.putString("RunningAutoMode", "DriveForwardAuto");
			break;
		}
		return null;
	}

	// singleton
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
}
