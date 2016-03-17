package org.texastorque.auto;

import org.texastorque.auto.modes.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;

	private final int DO_NOTHING_AUTO = 0;
	private final int DRIVE_FORWARD_AUTO = 1;
	private final int TURN_AUTO = 2;
	private final int TILT_AUTO = 3;
	private final int LBS_AUTO = 4;
	private final int LOW_BAR_AUTO = 5;
	private final int RAMPARTS_AUTO = 6;
	private final int ROCK_WALL_AUTO = 7;
	private final int ROUGH_TERRAIN_AUTO = 8;
	private final int LOW_BAR_BACK_AUTO = 9;

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
			return currentMode = new DoNothingAuto();
		case DRIVE_FORWARD_AUTO:
			return currentMode = new DriveForwardAuto();
		case LOW_BAR_AUTO:
			return currentMode = new LowBarAuto();
		case LOW_BAR_BACK_AUTO:
			return currentMode = new LowBarBackAuto();
		case LBS_AUTO:
			return currentMode = new LowBarShootAuto();
		case RAMPARTS_AUTO:
			return currentMode = new RampartsAuto();
		case ROCK_WALL_AUTO:
			return currentMode = new RockWallAuto();
		case ROUGH_TERRAIN_AUTO:
			return currentMode = new RoughTerrainAuto();
		case TURN_AUTO:
			return currentMode = new TurnAuto();
		case TILT_AUTO:
			return currentMode = new TiltAuto();

		}
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
