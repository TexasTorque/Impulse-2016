package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class LowBarAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(100);
		pause(3.0);
		postDefenseVision();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
