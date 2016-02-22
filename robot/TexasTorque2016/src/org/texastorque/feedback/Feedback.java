package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.input.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.component.TorquePotentiometer;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_CONVERSION = 1.0 / (3.75 * Math.PI);

	private Input input;

	// sensors
	private VisionFeedback vision;
	private ADXRS450_Gyro gyro;

	private TorqueEncoder leftDriveEncoder;
	private TorqueEncoder rightDriveEncoder;

	private TorqueEncoder flywheelEncoder;

	private TorquePotentiometer tiltPot;

	// drivebase values
	private double leftDrivePosition;
	private double leftDriveVelocity;
	private double leftDriveAcceleration;

	private double rightDrivePosition;
	private double rightDriveVelocity;
	private double rightDriveAcceleration;

	private double angle;
	private double angularVelocity;

	// shooter values
	private double flywheelVelocity;

	private double tiltAngle;

	public Feedback() {
		vision = VisionFeedback.getInstance();
		gyro = new ADXRS450_Gyro();

		leftDriveEncoder = new TorqueEncoder(Ports.DRIVE_LEFT_ENCODER_A, Ports.DRIVE_LEFT_ENCODER_B, true,
				EncodingType.k4X);
		rightDriveEncoder = new TorqueEncoder(Ports.DRIVE_RIGHT_ENCODER_A, Ports.DRIVE_RIGHT_ENCODER_B, false,
				EncodingType.k4X);

		flywheelEncoder = new TorqueEncoder(Ports.FLYWHEEL_ENCODER_A, Ports.FLYWHEEL_ENCODER_B, true, EncodingType.k4X);

		tiltPot = new TorquePotentiometer(Ports.TILT_POT_PORT);
		// tiltPot.setInputRange(Constants.S_TILT_MIN_VOLTAGE.getDouble(),
		// Constants.S_TILT_MAX_VOLTAGE.getDouble());
		// tiltPot.setPositionRange(Constants.S_TILT_MIN_ANGLE.getDouble(),
		// Constants.S_TILT_MAX_ANGLE.getDouble());
		tiltPot.setInputRange(2035, 1746);
		tiltPot.setPositionRange(0, 31);
	}

	public void setInput(Input _input) {
		input = _input;
	}

	public void update() {
		leftDriveEncoder.calc();
		rightDriveEncoder.calc();
		flywheelEncoder.calc();

		leftDrivePosition = leftDriveEncoder.get() * DRIVEBASE_CONVERSION;
		leftDriveVelocity = leftDriveEncoder.getRate() * DRIVEBASE_CONVERSION;
		leftDriveAcceleration = leftDriveEncoder.getAcceleration() * DRIVEBASE_CONVERSION;

		rightDrivePosition = rightDriveEncoder.get() * DRIVEBASE_CONVERSION;
		rightDriveVelocity = rightDriveEncoder.getRate() * DRIVEBASE_CONVERSION;
		rightDriveAcceleration = rightDriveEncoder.getAcceleration() * DRIVEBASE_CONVERSION;

		angle = gyro.getAngle() % 360.0;
		angularVelocity = gyro.getRate();

		flywheelVelocity = flywheelEncoder.getRate();

		SmartDashboard.putNumber("POTVALUE", tiltPot.getRaw());

		tiltAngle = tiltPot.getPosition();

		if (input.isVisionLock()) {
			vision.calc();
		}
	}

	public void resetDriveEncoders() {
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}

	public void resetFlywheelEncoder() {
		flywheelEncoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

	// getters
	public double getLeftDrivePosition() {
		return leftDrivePosition;
	}

	public double getLeftDriveVelocity() {
		return leftDriveVelocity;
	}

	public double getLeftDriveAcceleration() {
		return leftDriveAcceleration;
	}

	public double getRightDrivePosition() {
		return rightDrivePosition;
	}

	public double getRightDriveVelocity() {
		return rightDriveVelocity;
	}

	public double getRightDriveAcceleration() {
		return rightDriveAcceleration;
	}

	public double getAngle() {
		return angle;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public double getFlywheelVelocity() {
		return flywheelVelocity;
	}

	public double getTiltAngle() {
		return tiltAngle;
	}

	public double getRequiredTurn() {
		if (input.isVisionLock()) {
			return vision.getTurn();
		} else {
			return 0.0;
		}
	}

	public double getRequiredTilt() {
		if (input.isVisionLock()) {
			return vision.getTilt();
		} else {
			return 0.0;
		}
	}

	public int getVisionState() {
		if (input.isVisionLock()) {
			return vision.getVisionState();
		} else {
			return 0;
		}
	}

	public boolean visionShotReady() {
		if (input.isVisionLock()) {
			boolean ready = true;
			if (!TorqueMathUtil.near(getRequiredTilt(), 0, 5.0)) {
				// must be within 5 degrees of required tilt
				ready = false;
			}
			if (!TorqueMathUtil.near(getRequiredTurn(), 0, 5.0)) {
				// must be with 5 degrees of required turn
				ready = false;
			}
			if (!TorqueMathUtil.near(getFlywheelVelocity(), Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble(), 500)) {
				// must be near flywheel velocity
				ready = false;
			}
			return ready;
		} else {
			return false;
		}
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
