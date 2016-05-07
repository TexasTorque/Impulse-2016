package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class TurnAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(0.0);
		
		turn(Constants.A_TURN_AUTO_ANGLE.getDouble());
	}
}