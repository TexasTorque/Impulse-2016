package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class PortDeCullisAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(-100);
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 15.0;
	}

}
