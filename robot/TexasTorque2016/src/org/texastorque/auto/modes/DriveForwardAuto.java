package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class DriveForwardAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(100.0);
		
		drive(110);
	}
}