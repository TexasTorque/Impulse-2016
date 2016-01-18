package org.texastorque.output;

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
		leftDriveTop = new TorqueMotor(new VictorSP(0), false);
		leftDriveMid = new TorqueMotor(new VictorSP(0), false);
		leftDriveBot = new TorqueMotor(new VictorSP(0), false);

		rightDriveTop = new TorqueMotor(new VictorSP(0), true);
		rightDriveMid = new TorqueMotor(new VictorSP(0), true);
		rightDriveBot = new TorqueMotor(new VictorSP(0), true);
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
