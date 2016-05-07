package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class LowBarBackAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(50.0);
		
		armUp = false;
		drive(110);
		pause(5);
		drive(-105);
	}
}
