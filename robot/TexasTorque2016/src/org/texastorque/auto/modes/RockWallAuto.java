package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RockWallAuto extends AutoMode {

	@Override
	protected void run() {
		drive(100);
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
