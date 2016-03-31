package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class PortDeCullisAuto extends AutoMode {

	@Override
	protected void run() {
		armSetpoint = Constants.ARM_DOWN_SETPOINT.getDouble();
		driveSetpoint = -Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 15.0;
	}

}
