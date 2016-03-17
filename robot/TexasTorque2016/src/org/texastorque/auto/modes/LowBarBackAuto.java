package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class LowBarBackAuto extends AutoMode {

	@Override
	protected void run() {
		driveSetpoint = Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble();
		pause(5);
		driveSetpoint = Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble() * -.75; 
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 50.0;
	}
}
