package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class MoatAuto extends AutoMode {

	@Override
	protected void run() {
		drive(100);
		pause(3.0);
		postDefenseVision();
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 70.0;
	}

}
