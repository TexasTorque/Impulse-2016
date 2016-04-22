package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class DriveForwardAuto extends AutoMode {

	@Override
	protected void run() {
		drive(100);
	}

	@Override
	public double getLinearMaxSpeed() {
		return 100.0;
	}
}