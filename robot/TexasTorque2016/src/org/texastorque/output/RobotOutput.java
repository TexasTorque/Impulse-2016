package org.texastorque.output;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private static final boolean OUTPUT_MUTED = false;

	private TorqueMotor leftDriveTop;
	private TorqueMotor leftDriveMid;
	private TorqueMotor leftDriveBot;
	private TorqueMotor rightDriveTop;
	private TorqueMotor rightDriveMid;
	private TorqueMotor rightDriveBot;

	public RobotOutput() {
		leftDriveTop = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_TOP), false);
		leftDriveMid = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_MID), false);
		leftDriveBot = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_BOT), false);

		rightDriveTop = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_TOP), true);
		rightDriveMid = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_MID), true);
		rightDriveBot = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_BOT), true);
	}

	public void setDriveSpeeds(double left, double right) {
		if (OUTPUT_MUTED) {
			leftDriveTop.set(0.0);
			leftDriveMid.set(0.0);
			leftDriveBot.set(0.0);
			rightDriveTop.set(0.0);
			rightDriveMid.set(0.0);
			rightDriveBot.set(0.0);
			return;
		}
		leftDriveTop.set(left);
		leftDriveMid.set(left);
		leftDriveBot.set(left);
		rightDriveTop.set(right);
		rightDriveMid.set(right);
		rightDriveBot.set(right);
	}

	// singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
