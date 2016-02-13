package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.component.TorquePotentiometer;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_CONVERSION = 8 * Math.PI;// converts to inches
	private static final double TILT_CONVERSION = 1.05 / 9.875;// converts to degrees (shooter tilt)

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

		leftDriveEncoder = new TorqueEncoder(Ports.DRIVE_LEFT_ENCODER_A, Ports.DRIVE_LEFT_ENCODER_B, false,
				EncodingType.k4X);
		rightDriveEncoder = new TorqueEncoder(Ports.DRIVE_RIGHT_ENCODER_A, Ports.DRIVE_RIGHT_ENCODER_B, false,
				EncodingType.k4X);

		flywheelEncoder = new TorqueEncoder(Ports.FLYWHEEL_ENCODER_A, Ports.FLYWHEEL_ENCODER_B, false,
				EncodingType.k4X);

		tiltPot = new TorquePotentiometer(Ports.TILT_POT_PORT);
		tiltPot.setInputRange(Constants.S_TILT_MIN_VOLTAGE.getDouble(), Constants.S_TILT_MAX_VOLTAGE.getDouble());
		tiltPot.setPositionRange(0, Constants.S_TILT_MAX_ANGLE.getDouble());
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

		tiltAngle = tiltPot.getPosition() * TILT_CONVERSION;

		vision.calc();
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
		return vision.getTurn();
	}

	public double getRequiredTilt() {
		return vision.getTilt();
	}

	public int getVisionState() {
		return vision.getVisionState();
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
