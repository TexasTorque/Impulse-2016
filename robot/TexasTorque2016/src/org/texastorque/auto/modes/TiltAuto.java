package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class TiltAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(0.0);

		tiltSetpoint = Constants.A_TILT_AUTO_ANGLE.getDouble();
	}
}
