package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class LowBarBackAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(100);
		pause(5);
		drive(-90);
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50.0;
	}
}
