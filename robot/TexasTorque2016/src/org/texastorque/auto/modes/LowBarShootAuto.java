package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class LowBarShootAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		drive(Constants.A_LBS_DRIVE_DISTANCE.getDouble());
		pause(4.5);
		turn(60);
		pause(2.0);
		drive(63);
		pause(2.0);
		vision();

		// armUp = false;
		// leftDriveSetpoint = rightDriveSetpoint =
		// Constants.A_DRIVE_FORWARD_AUTO_DISTANCE.getDouble();
		// pause(3.0);
		// leftDriveSetpoint = Constants.A_LBS_LEFT_CURVE_DISTANCE.getDouble();
		// rightDriveSetpoint =
		// Constants.A_LBS_RIGHT_CURVE_DISTANCE.getDouble();
		// pause(5.0);
		// visionLock = true;
	}

	@Override
	public double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
