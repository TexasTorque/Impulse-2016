package org.texastorque.output;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private static final boolean OUTPUT_MUTED = false;

	// drivebase
	private TorqueMotor leftTopDrive;
	private TorqueMotor leftBottomDrive;
	private TorqueMotor leftBoostDrive;
	private TorqueMotor rightTopDrive;
	private TorqueMotor rightBottomDrive;
	private TorqueMotor rightBoostDrive;

	// intake
	private TorqueMotor topIntakeMotor;
	private TorqueMotor bottomIntakeMotor;
	// shooter
	private TorqueMotor leftTiltMotor;
	private TorqueMotor rightTiltMotor;
	private TorqueMotor flywheelMotor;

	public RobotOutput() {
		leftTopDrive = new TorqueMotor(new VictorSP(Ports.LEFT_TOP_DRIVE), false);
		leftBottomDrive = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_MID), false);
		leftBoostDrive = new TorqueMotor(new VictorSP(Ports.LEFT_DRIVE_BOT), false);

		rightTopDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_TOP), true);
		rightBottomDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_MID), true);
		rightBoostDrive = new TorqueMotor(new VictorSP(Ports.RIGHT_DRIVE_BOT), true);
		
		leftTiltMotor = new TorqueMotor(new VictorSP(Ports.LEFT_TILT), false);
		rightTiltMotor = new TorqueMotor(new VictorSP(Ports.RIGHT_TILT), false);
		flywheelMotor = new TorqueMotor(new VictorSP(Ports.FLYWHEEL), false);
		
		topIntakeMotor = new TorqueMotor(new VictorSP(-1), false);
		bottomIntakeMotor = new TorqueMotor(new VictorSP(-1), false);
		
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
	
	public void setIntakeSpeed(double top, double bottom) {
		topIntakeMotor.set(top);
		bottomIntakeMotor.set(bottom);
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
	
	public void setTiltSpeeds(double left, double right) {
		if (OUTPUT_MUTED) {
			leftTiltMotor.set(0.0);
			rightTiltMotor.set(0.0);
			return;
		}
		leftTiltMotor.set(left);
		rightTiltMotor.set(right);
	}
	
	public void setFlywheelSpeed(double speed) {
		if (OUTPUT_MUTED) {
			flywheelMotor.set(0.0);
			return;
		}
		flywheelMotor.set(speed);
	}

	// singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
