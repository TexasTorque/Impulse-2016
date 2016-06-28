package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class MoatAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(90.0);

		drive(150);
		pause(3.0);
		postDefenseVision();
	}
}
