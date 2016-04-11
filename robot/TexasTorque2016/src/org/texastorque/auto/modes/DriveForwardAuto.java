package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class DriveForwardAuto extends AutoMode {

	@Override
	protected void run() {
		drive(Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble());
	}

	@Override
	public double getLinearMaxSpeed() {
		return 100.0;
	}
}