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
//	private TorqueMotor topIntakeMotor;
	private TorqueMotor bottomIntakeMotor;
	
	// shooter
	private TorqueMotor tiltMotor;
	private TorqueMotor flywheelMotor;

	public RobotOutput() {
		leftTopDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_TOP), false);
		leftBottomDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_BOTTOM), false);
		leftBoostDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_BOOST), false);

		rightTopDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_TOP), true);
		rightBottomDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_BOTTOM), true);
		rightBoostDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_BOOST), true);
		
		tiltMotor = new TorqueMotor(new VictorSP(Ports.TILT), false);
		flywheelMotor = new TorqueMotor(new VictorSP(Ports.FLYWHEEL), false);
		
//		topIntakeMotor = new TorqueMotor(new VictorSP(Ports.INTAKE_TOP), false);
		bottomIntakeMotor = new TorqueMotor(new VictorSP(Ports.INTAKE_BOTTOM), false);
		
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
	
	public void setIntakeSpeed(double speed) {
		if (OUTPUT_MUTED) {
//			topIntakeMotor.set(0.0);
			bottomIntakeMotor.set(0.0);
			return;
		}
//		topIntakeMotor.set(speed);
		bottomIntakeMotor.set(speed);
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
			tiltMotor.set(0.0);
			return;
		}
		tiltMotor.set(left);
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
