package org.texastorque.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;

	private AutoMode currentMode;

	public void reset() {
		if (currentMode != null) {
			currentMode.stop();
		}
		SmartDashboard.putNumber("AutoMode", 0);
		SmartDashboard.putString("RunningAutoMode", "N/A");
	}

	public void updateDashboard() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");

		SmartDashboard.putString("WillRunAutoMode", AutoModes.convert(dashboardMode).call.getSimpleName());
	}

	public AutoMode createAutoMode() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");

		return currentMode = AutoModes.convert(dashboardMode).create();
	}

	public void runAutoMode() {
		currentMode.start();
	}

	public double getAutoMaxSpeed() {
		return currentMode.getLinearMaxSpeed();
	}

	// singleton
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
}
