package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class CDFAuto extends AutoMode {

	@Override
	protected void run() {
		drive(-42.0);
		pause(4.0);
		armUp = false;
		pause(0.5);
		drive(-76.0);
		pause(4.0);
		turn(180);
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 20.0;
	}

}
