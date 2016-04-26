package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RampartsAuto extends AutoMode {

	@Override
	protected void run() {
		drive(120);
		pause(3.0);
		postDefenseVision();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 85.0;
	}
}
