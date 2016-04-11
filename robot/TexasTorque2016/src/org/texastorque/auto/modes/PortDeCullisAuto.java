package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class PortDeCullisAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(-Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble());
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 15.0;
	}

}
