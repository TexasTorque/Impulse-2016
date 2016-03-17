package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;
import org.texastorque.constants.Constants;

public class LowBarShootAuto extends AutoMode {

	@Override
	protected void run() {
		driveSetpoint = Constants.A_LBS_DRIVE_DISTANCE.getDouble();
		pause(6);
		driveSetpoint = 0.0;
		turnSetpoint = 60.0;
		pause(1.0);
		turnSetpoint = 0.0;
		driveSetpoint = 70.0;
		tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
		pause(3);
		flywheelActive = true;
		pause(2);
		conveyorIntaking = true;
		pause(1.0);
		flywheelActive = false;
		conveyorIntaking = false;
	}

	@Override
	public double getLinearMaxSpeed() {
		return 0.0;// TODO
	}
}
