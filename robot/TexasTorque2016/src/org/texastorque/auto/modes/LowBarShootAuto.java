package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class LowBarShootAuto extends AutoMode {

	@Override
	protected void run() {
		armSetpoint = Constants.ARM_DOWN_SETPOINT.getDouble();
		driveSetpoint = Constants.A_LBS_DRIVE_DISTANCE.getDouble();
		pause(4);
		driveSetpoint = 0.0;
		turnSetpoint = 60.0;
		pause(1.0);
		turnSetpoint = 0.0;
		driveSetpoint = 15.0;
		pause(2);
		visionLock = true;
		flywheelActive = true;
		pause(2);
		conveyorIntaking = true;
		pause(1.0);
		visionLock = false;
		flywheelActive = false;
		conveyorIntaking = false;
	}

	@Override
	public double getLinearMaxSpeed() {
		return 50.0;// TODO
	}
}
