package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class OnlyVisionAuto extends AutoMode {

	@Override
	protected void run() {
		visionLock = true;
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50;
	}

}
