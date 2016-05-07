package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RockWallAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(65.0);

		drive(120);
		pause(3.0);
		postDefenseVision();
	}
}
