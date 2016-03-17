package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class RampartsAuto extends AutoMode {

	@Override
	protected void run() {
		driveSetpoint = Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 70.0;// TODO
	}
}
