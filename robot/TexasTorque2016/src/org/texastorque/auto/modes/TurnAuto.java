package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class TurnAuto extends AutoMode {

	@Override
	protected void run() {
		turnSetpoint = Constants.A_TURN_AUTO_ANGLE.getDouble();
	}
	
	@Override
	public double getLinearMaxSpeed() {
		return 0.0;
	}
}