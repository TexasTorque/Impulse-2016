package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class LowBarShootAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(Constants.A_LBS_DRIVE_DISTANCE.getDouble());
		pause(4.5);
		turn(60);
		pause(3.0);
		drive(63);
		pause(2.0);
		vision();
		pause(5.0);
		visionLock = false;
	}

	@Override
	public double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
