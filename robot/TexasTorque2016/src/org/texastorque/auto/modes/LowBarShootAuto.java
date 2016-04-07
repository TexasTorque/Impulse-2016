package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;

public class LowBarShootAuto extends AutoMode {

	@Override
	protected void run() {
		armUp = false;
		driveSetpoint = Constants.A_LBS_DRIVE_DISTANCE.getDouble();
		pause(4.5);
		driveSetpoint = 0.0;
		turnSetpoint = 50.0;
		pause(2.0);
		turnSetpoint = 0.0;
//		driveSetpoint = 15.0;
		driveSetpoint = 60;
		pause(2.0);
		override = true;
		leftDriveSpeed = 0.25;
		rightDriveSpeed = -0.25;
		pause(0.75);
		leftDriveSpeed = 0.0;
		rightDriveSpeed = 0.0;
		override = false;
		pause(0.5);
		visionLock = true;
	}

	@Override
	public double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
