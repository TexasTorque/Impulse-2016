package org.texastorque.auto;

import org.texastorque.auto.modes.*;

public enum AutoModes {
	DO_NOTHING_AUTO(0, DoNothingAuto.class),
	DRIVE_FORWARD_AUTO(1, DriveForwardAuto.class),
	TURN_AUTO(2, TurnAuto.class),
	TILT_AUTO(3, TiltAuto.class),
	LBS_AUTO(4, LowBarShootAuto.class),
	LOW_BAR_AUTO(5, LowBarAuto.class),
	RAMPARTS_AUTO(6, RampartsAuto.class),
	ROCK_WALL_AUTO(7, RockWallAuto.class),
	ROUGH_TERRAIN_AUTO(8, RoughTerrainAuto.class),
	LOW_BAR_BACK_AUTO(9, LowBarBackAuto.class),
	PORT_DE_CULLIS_AUTO(10, PortDeCullisAuto.class),
	ONLY_VISION_AUTO(11, OnlyVisionAuto.class);

	public int pass;
	public Class<?> call;

	AutoModes(int _pass, Class<?> _call) {
		pass = _pass;
		call = _call;
	}

	public AutoMode create() {
		try {
			return (AutoMode) call.newInstance();
		} catch (Exception e) {
			return new DoNothingAuto();
		}
	}

	public static AutoModes convert(int pass) {
		for (AutoModes m : AutoModes.values()) {
			if (pass == m.pass) {
				return m;
			}
		}
		return DO_NOTHING_AUTO;
	}
}
