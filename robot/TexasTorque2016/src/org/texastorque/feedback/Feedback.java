package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_DISTANCE_CONVERSION = 0.084883;
	private static final double FLYWHEEL_VELOCITY_CONVERSION = .24;

	// sensors
	private VisionFeedback vision;

	private TorqueEncoder leftDriveEncoder;
	private TorqueEncoder rightDriveEncoder;
	private TorqueEncoder leftArmEncoder;
	private TorqueEncoder rightArmEncoder;
	private TorqueEncoder flywheelEncoder;
	private TorqueEncoder tiltEncoder;

//	private TorqueGyro gyro;
	private ADXRS450_Gyro gyro;

	private BuiltInAccelerometer accel;

	// drivebase values
	private double leftDrivePosition;
	private double leftDriveVelocity;
	private double leftDriveAcceleration;

	private double rightDrivePosition;
	private double rightDriveVelocity;
	private double rightDriveAcceleration;

	private double angle;
	private double angularVelocity;

	private double robotPitch;

	// shooter values
	private double flywheelVelocity;

	private double tiltAngle;

	// arm values
	private double leftArmAngle;
	private double rightArmAngle;

	// misc
	private double prevTime;
	private double prevVisionDistance;

	public Feedback() {
		vision = VisionFeedback.getInstance();

		leftDriveEncoder = new TorqueEncoder(Ports.DRIVE_LEFT_ENCODER_A, Ports.DRIVE_LEFT_ENCODER_B, true,
				EncodingType.k4X);
		rightDriveEncoder = new TorqueEncoder(Ports.DRIVE_RIGHT_ENCODER_A, Ports.DRIVE_RIGHT_ENCODER_B, true,
				EncodingType.k4X);
		leftArmEncoder = new TorqueEncoder(Ports.ARM_LEFT_ENCODER_A, Ports.ARM_LEFT_ENCODER_B, true, EncodingType.k4X);
		rightArmEncoder = new TorqueEncoder(Ports.ARM_RIGHT_ENCODER_A, Ports.ARM_RIGHT_ENCODER_B, true,
				EncodingType.k4X);
		flywheelEncoder = new TorqueEncoder(Ports.FLYWHEEL_ENCODER_A, Ports.FLYWHEEL_ENCODER_B, true,
				EncodingType.k4X);
		tiltEncoder = new TorqueEncoder(Ports.TILT_ENCODER_A, Ports.TILT_ENCODER_B, false, EncodingType.k4X);

		gyro = new ADXRS450_Gyro();

		accel = new BuiltInAccelerometer();

		prevTime = Timer.getFPGATimestamp();
	}

	public void update() {
		leftDriveEncoder.calc();
		rightDriveEncoder.calc();
		flywheelEncoder.calc();

		leftDrivePosition = leftDriveEncoder.get() * DRIVEBASE_DISTANCE_CONVERSION;
		leftDriveVelocity = leftDriveEncoder.getRate() * DRIVEBASE_DISTANCE_CONVERSION;
		leftDriveAcceleration = leftDriveEncoder.getAcceleration() * DRIVEBASE_DISTANCE_CONVERSION;

		rightDrivePosition = rightDriveEncoder.get() * DRIVEBASE_DISTANCE_CONVERSION;
		rightDriveVelocity = rightDriveEncoder.getRate() * DRIVEBASE_DISTANCE_CONVERSION;
		rightDriveAcceleration = rightDriveEncoder.getAcceleration() * DRIVEBASE_DISTANCE_CONVERSION;

		angle = gyro.getAngle();
		angularVelocity = gyro.getRate();

		flywheelVelocity = flywheelEncoder.getRate() * FLYWHEEL_VELOCITY_CONVERSION;

		// down setpoint is negative
		tiltAngle = (tiltEncoder.getRaw() * .04234) + Constants.S_DOWN_SETPOINT.getDouble();

		leftArmAngle = leftArmEncoder.get();
		rightArmAngle = rightArmEncoder.get();

		robotPitch = accel.getX() / (accel.getY() * accel.getY() + accel.getZ() + accel.getZ());
		robotPitch = Math.toDegrees(Math.atan(robotPitch));
	}

	public void resetDriveEncoders() {
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void resetTiltEncoder() {
		tiltEncoder.reset();
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

	public double getLeftArmAngle() {
		return leftArmAngle;
	}

	public double getRightArmAngle() {
		return rightArmAngle;
	}

	public double getRobotPitch() {
		return robotPitch;
	}

	public double getRequiredTurn() {
		return vision.getTurn();
	}

	public boolean visionShotReady() {
		if (!TorqueMathUtil.near(getRequiredTurn(), 1.65 , 0.1)) {
			return false;
		}
		if (!TorqueMathUtil.near(getFlywheelVelocity(), Constants.S_VISION_FLYWHEEL.getDouble(), 1000)) {
			return false;
		}
		return true;
	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("Turn", vision.getTurn());
		SmartDashboard.putBoolean("HasTarget", vision.hasTarget());
		SmartDashboard.putBoolean("VisionShotReady", visionShotReady());

		SmartDashboard.putNumber("RobotPitch", robotPitch);
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
