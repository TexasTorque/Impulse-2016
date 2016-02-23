package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class TiltAuto extends AutoMode {

	@Override
	protected void run() {
		tiltSetpoint = 10.0;
	}

}
