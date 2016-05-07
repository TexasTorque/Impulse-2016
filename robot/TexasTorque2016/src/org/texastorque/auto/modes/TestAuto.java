package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class TestAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(25.0);
		drive(24.0);
		pause(3.0);
		setLinearMaxSpeed(100.0);
		drive(24.0);
	}
}
