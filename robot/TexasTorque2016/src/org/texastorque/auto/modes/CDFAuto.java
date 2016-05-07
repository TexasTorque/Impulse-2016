package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class CDFAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(20.0);

		drive(-42.0);
		pause(4.0);
		armUp = false;
		pause(0.5);

		setLinearMaxSpeed(75.0);

		drive(-80.0);
		pause(4.0);
		turn(180);
	}
}
