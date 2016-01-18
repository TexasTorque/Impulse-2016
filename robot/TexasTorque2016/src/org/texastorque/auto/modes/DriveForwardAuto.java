package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class DriveForwardAuto extends AutoMode {

	protected void run() {
		driveSetpoint = Constants.DRIVE_FORWARD_AUTO_DISTANCE.getDouble();
	}

}
