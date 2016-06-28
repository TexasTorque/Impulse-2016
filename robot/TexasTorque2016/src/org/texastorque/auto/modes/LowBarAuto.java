package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class LowBarAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(50.0);
		
		armUp = false;
		drive(150);
		pause(3.0);
		postDefenseVision();
	}
}
