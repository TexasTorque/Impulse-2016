package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RoughTerrainAuto extends AutoMode {

	@Override
	protected void run() {
		drive(120);
		pause(3.0);
		postDefenseVision();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50.0;
	}
}
