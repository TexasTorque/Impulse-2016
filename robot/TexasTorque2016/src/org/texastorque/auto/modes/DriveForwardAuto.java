package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class DriveForwardAuto extends AutoMode {

	@Override
	protected void run() {
		driveSetpoint = Constants.A_DRIVE_FORWARD_DISTANCE.getDouble();
	}
	
	@Override
	public String getName() {
		return "DriveForwardAuto";
	}
}