package org.texastorque.auto;

import org.texastorque.auto.AutoModes.DefensePosition;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;

	private AutoMode currentMode;

	public void reset() {
		if (currentMode != null) {
			currentMode.stop();
		}
		AutoMode.currentDefense = DefensePosition.ZERO;
		SmartDashboard.putNumber("AutoMode", 0);
		SmartDashboard.putNumber("AutoDefensePosition", 0);
		SmartDashboard.putString("RunningAutoMode", "N/A");
		SmartDashboard.putNumber("RunningDefensePosition", 0);
	}

	public void updateDashboard() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");
		int defensePosition = (int) SmartDashboard.getNumber("AutoDefensePosition");

		SmartDashboard.putString("WillRunAutoMode", AutoModes.autoModeToString(dashboardMode));
		SmartDashboard.putString("WillRunDefensePosition", AutoModes.defenseToString(defensePosition));
	}

	public AutoMode createAutoMode() {
		int dashboardMode = (int) SmartDashboard.getNumber("AutoMode");
		int defensePosition = (int) SmartDashboard.getNumber("AutoDefensePosition");

		return currentMode = AutoModes.create(dashboardMode, defensePosition);
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
