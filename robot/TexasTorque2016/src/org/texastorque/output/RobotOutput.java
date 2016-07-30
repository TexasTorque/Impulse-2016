package org.texastorque.output;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.input.HumanInput;
import org.texastorque.subsystem.etc.Lights;
import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private static boolean OUTPUT_ENABLED = true;

	private Compressor compressor;

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
	private TorqueMotor flywheelLeftMotor;
	private TorqueMotor flywheelRightMotor;

	// double arms
	private TorqueMotor leftArmMotor;
	private TorqueMotor rightArmMotor;

	// compression testing
	private DoubleSolenoid compressionTesting;

	// flashlight
	private Relay flashlight;

	// lights
	private Lights lights;

	public RobotOutput() {
		compressor = new Compressor();
		compressor.start();

		leftTopDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_TOP), false);
		leftBottomDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_BOTTOM), false);
		leftBoostDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_LEFT_BOOST), false);

		rightTopDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_TOP), true);
		rightBottomDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_BOTTOM), true);
		rightBoostDrive = new TorqueMotor(new VictorSP(Ports.DRIVE_RIGHT_BOOST), true);

		intakeMotor = new TorqueMotor(new VictorSP(Ports.INTAKE), true);

		conveyorMotor = new TorqueMotor(new VictorSP(Ports.CONVEYOR), false);

		tiltMotor = new TorqueMotor(new VictorSP(Ports.TILT), false);
		flywheelLeftMotor = new TorqueMotor(new VictorSP(Ports.FLYWHEEL_LEFT), true);
		flywheelRightMotor = new TorqueMotor(new VictorSP(Ports.FLYWHEEL_RIGHT), false);

		leftArmMotor = new TorqueMotor(new VictorSP(Ports.ARM_LEFT), false);
		rightArmMotor = new TorqueMotor(new VictorSP(Ports.ARM_RIGHT), true);

		brakes = new DoubleSolenoid(Ports.BRAKES_SOLENOID_PORT_A, Ports.BRAKES_SOLENOID_PORT_B);
		compressionTesting = new DoubleSolenoid(Ports.COMPRESSION_TESTING_A, Ports.COMPRESSION_TESTING_B);

		flashlight = new Relay(Ports.FLASHLIGHT, Relay.Direction.kBoth);

		lights = Lights.getInstance();
	}

	public void setDriveSpeeds(double left, double right) {
		if (OUTPUT_ENABLED) {
			leftTopDrive.set(left);
			leftBottomDrive.set(left);
			rightTopDrive.set(right);
			rightBottomDrive.set(right);
			if(Constants.D_DO_BOOST.getBoolean()) {
				leftBoostDrive.set(left);
				rightBoostDrive.set(right);
			} else {
				leftBoostDrive.set(0.0);
				rightBoostDrive.set(0.0);
			}
		} else {
			leftTopDrive.set(0.0);
			leftBottomDrive.set(0.0);
			rightTopDrive.set(0.0);
			rightBottomDrive.set(0.0);
			rightBoostDrive.set(0.0);
			leftBoostDrive.set(0.0);
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
			flywheelLeftMotor.set(speed);
			flywheelRightMotor.set(speed);
		} else {
			flywheelLeftMotor.set(0.0);
			flywheelRightMotor.set(0.0);
		}
	}

	public void setArmSpeeds(double left, double right) {
		if (OUTPUT_ENABLED) {
			leftArmMotor.set(left);
			rightArmMotor.set(right);
		} else {
			leftArmMotor.set(0.0);
			rightArmMotor.set(0.0);
		}
	}

	public void setBrakes(boolean on) {
		if (OUTPUT_ENABLED) {
			brakes.set(on ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		} else {
			brakes.set(DoubleSolenoid.Value.kForward);
		}
	}

	public void setCompressionTesting(boolean doCompressionTesting) {
		if (OUTPUT_ENABLED) {
			compressionTesting
					.set(doCompressionTesting ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
		} else {
			compressionTesting.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void setFlashlight(boolean on) {
		if (OUTPUT_ENABLED) {
			flashlight.set(on ? Value.kOn : Value.kOff);
		} else {
			flashlight.set(Value.kOff);
		}
	}

	public void setRPMReadyState(double value, double setpoint) {
		if (OUTPUT_ENABLED) {
			lights.set(value, setpoint);
			HumanInput.getInstance().rumbleCalc(value, setpoint);
		} else {
			lights.off();
			HumanInput.getInstance().rumbleCalc(0, 0);
		}
	}

	// singleton
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
