package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class RampartsAuto extends AutoMode {

	@Override
	protected void run() {
		setLinearMaxSpeed(80.0);
		
		drive(125);
		pause(3.0);
		
		postDefenseVision();
	}
}
