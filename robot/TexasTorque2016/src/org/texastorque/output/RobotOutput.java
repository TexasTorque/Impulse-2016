package org.texastorque.output;

import org.texastorque.constants.PortsBravo;
import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private static boolean OUTPUT_ENABLED = true;

	// drivebase
	private TorqueMotor leftTopDrive;
	private TorqueMotor leftBottomDrive;
	private TorqueMotor leftBoostDrive;
	private TorqueMotor rightTopDrive;
	private TorqueMotor rightBottomDrive;
	private TorqueMotor rightBoostDrive;

	// brakeing
	private DoubleSolenoid brakes;

	// intake
	private TorqueMotor intakeMotor;

	// conveyor
	private TorqueMotor conveyorMotor;

	// shooter
	private TorqueMotor tiltMotor;
	private TorqueMotor flywheelMotor;

	// mechanism
	private TorqueMotor mechanismMotor;

	// compression testing
	private DoubleSolenoid compressionTesting;

	public RobotOutput() {
		leftTopDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_LEFT_TOP), false);
		leftBottomDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_LEFT_BOTTOM), false);
		leftBoostDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_LEFT_BOOST), false);

		rightTopDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_RIGHT_TOP), true);
		rightBottomDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_RIGHT_BOTTOM), true);
		rightBoostDrive = new TorqueMotor(new VictorSP(PortsBravo.DRIVE_RIGHT_BOOST), true);

		intakeMotor = new TorqueMotor(new VictorSP(PortsBravo.INTAKE), true);

		conveyorMotor = new TorqueMotor(new VictorSP(PortsBravo.CONVEYOR), false);

		tiltMotor = new TorqueMotor(new VictorSP(PortsBravo.TILT), false);
		flywheelMotor = new TorqueMotor(new VictorSP(PortsBravo.FLYWHEEL), true);

		mechanismMotor = new TorqueMotor(new VictorSP(PortsBravo.A_MECHANISM), false);

		brakes = new DoubleSolenoid(PortsBravo.BRAKES_SOLENOID_PORT_A, PortsBravo.BRAKES_SOLENOID_PORT_B);
		compressionTesting = new DoubleSolenoid(PortsBravo.COMPRESSION_TESTING_A, PortsBravo.COMPRESSION_TESTING_B);
	}

	public void setDriveSpeeds(double left, double right) {
		if (OUTPUT_ENABLED) {
			leftTopDrive.set(left);
			leftBottomDrive.set(left);
			leftBoostDrive.set(left);
			rightTopDrive.set(right);
			rightBottomDrive.set(right);
			rightBoostDrive.set(right);
		} else {
			leftTopDrive.set(0.0);
			leftBottomDrive.set(0.0);
			leftBoostDrive.set(0.0);
			rightTopDrive.set(0.0);
			rightBottomDrive.set(0.0);
			rightBoostDrive.set(0.0);
		}
	}

	public void setIntakeSpeed(double speed) {
		if (OUTPUT_ENABLED) {
			intakeMotor.set(speed);
		} else {
			intakeMotor.set(0.0);
		}
	}

	public void setConveyorSpeed(double speed) {
		if (OUTPUT_ENABLED) {
			conveyorMotor.set(speed);
		} else {
			conveyorMotor.set(0.0);
		}
	}

	public void setTiltSpeeds(double speed) {
		if (OUTPUT_ENABLED) {
			tiltMotor.set(speed);
		} else {
			tiltMotor.set(0.0);
		}
	}

	public void setFlywheelSpeed(double speed) {
		if (OUTPUT_ENABLED) {
			flywheelMotor.set(speed);
		} else {
			flywheelMotor.set(0.0);
		}
	}

	public void setMechanismSpeed(double speed) {
		if (OUTPUT_ENABLED) {
			mechanismMotor.set(speed);
		} else {
			mechanismMotor.set(0.0);
		}
	}

	public void setBrakes(boolean on) {
		if (OUTPUT_ENABLED) {
			brakes.set(on ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		} else {
			brakes.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void setCompressionTesting(boolean doCompressionTesting) {
		if (OUTPUT_ENABLED) {
			compressionTesting
					.set(doCompressionTesting ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
		} else {
			compressionTesting.set(DoubleSolenoid.Value.kForward);
		}
	}

	// singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
