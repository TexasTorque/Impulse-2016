package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;

public class RampartsAuto extends AutoMode {

	@Override
	protected void run() {
		drive(Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble());
		pause(4.0);
		turn(-Feedback.getInstance().getAngle());
		pause(2.0);
		visionLock = true;
	}

	@Override
	protected double getLinearMaxSpeed() {
		return 70.0;// TODO
	}
}
