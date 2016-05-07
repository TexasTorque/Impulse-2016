package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class PortDeCullisAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(15.0);

		armUp = false;
		drive(-110);
	}
}
