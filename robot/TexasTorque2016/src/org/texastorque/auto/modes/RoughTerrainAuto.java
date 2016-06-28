package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RoughTerrainAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(50.0);

		drive(150);
		pause(3.0);
		postDefenseVision();
	}
}
