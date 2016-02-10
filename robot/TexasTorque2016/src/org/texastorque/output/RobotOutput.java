package org.texastorque.output;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private static final boolean OUTPUT_MUTED = false;

	private TorqueMotor leftTopDrive;
	private TorqueMotor leftBottomDrive;
	private TorqueMotor leftBoostDrive;
	private TorqueMotor rightTopDrive;
	private TorqueMotor rightBottomDrive;
	private TorqueMotor rightBoostDrive;

	public RobotOutput() {
		leftTopDrive = new TorqueMotor(new VictorSP(Ports.LEFT_TOP_DRIVE), false);
		leftBottomDrive = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_MID), false);
		leftBoostDrive = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_BOT), false);

		rightTopDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_TOP), true);
		rightBottomDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_MID), true);
		rightBoostDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_BOT), true);
	}

	public void setDriveSpeeds(double left, double right) {
		if (OUTPUT_MUTED) {
			leftTopDrive.set(0.0);
			leftBottomDrive.set(0.0);
			rightTopDrive.set(0.0);
			rightBottomDrive.set(0.0);
			return;
		}
		leftTopDrive.set(left);
		leftBottomDrive.set(left);
		rightBottomDrive.set(right);
		rightBottomDrive.set(right);
	}
	
	public void setBoostDriveSpeed(double left, double right) {
		if (OUTPUT_MUTED) {
			leftBoostDrive.set(0.0);
			rightBoostDrive.set(0.0);
			return;
		}
		leftBoostDrive.set(left);
		rightBoostDrive.set(right);
	}

	// singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
