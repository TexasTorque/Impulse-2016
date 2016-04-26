package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RockWallAuto extends AutoMode {

	@Override
	protected void run() {
		drive(115);
		pause(3.0);
		postDefenseVision();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 65.0;
	}
}
