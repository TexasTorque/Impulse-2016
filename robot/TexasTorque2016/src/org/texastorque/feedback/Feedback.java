package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.constants.PortsBravo;
import org.texastorque.input.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_DISTANCE_CONVERSION = 0.084883;
	private static final double FLYWHEEL_VELOCITY_CONVERSION = .24;

	private Input currentInput;

	// sensors
	private VisionFeedback vision;

	private ADXRS450_Gyro gyro;

	private TorqueEncoder leftDriveEncoder;
	private TorqueEncoder rightDriveEncoder;
	private TorqueEncoder leftArmEncoder;
	private TorqueEncoder rightArmEncoder;
	private TorqueEncoder flywheelEncoder;
	private TorqueEncoder tiltEncoder;

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

	// a mechanism values
	private double leftArmAngle;
	private double rightArmAngle;

	public Feedback() {
		vision = VisionFeedback.getInstance();

		gyro = new ADXRS450_Gyro();

		leftDriveEncoder = new TorqueEncoder(PortsBravo.DRIVE_LEFT_ENCODER_A, PortsBravo.DRIVE_LEFT_ENCODER_B, true,
				EncodingType.k4X);
		rightDriveEncoder = new TorqueEncoder(PortsBravo.DRIVE_RIGHT_ENCODER_A, PortsBravo.DRIVE_RIGHT_ENCODER_B, false,
				EncodingType.k4X);
		leftArmEncoder = new TorqueEncoder(PortsBravo.ARM_LEFT_ENCODER_A, PortsBravo.ARM_LEFT_ENCODER_B, false,
				EncodingType.k4X);
		rightArmEncoder = new TorqueEncoder(PortsBravo.ARM_RIGHT_ENCODER_A, PortsBravo.ARM_RIGHT_ENCODER_B, false,
				EncodingType.k4X);
		flywheelEncoder = new TorqueEncoder(PortsBravo.FLYWHEEL_ENCODER_A, PortsBravo.FLYWHEEL_ENCODER_B, false,
				EncodingType.k4X);
		tiltEncoder = new TorqueEncoder(PortsBravo.TILT_ENCODER_A, PortsBravo.TILT_ENCODER_B, false, EncodingType.k4X);
	}

	public void setInput(Input input) {
		currentInput = input;
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

		if (currentInput.isVisionLock() && !visionShotReady()) {
			vision.calc();
		}
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

	public double getRequiredTurn() {
		return vision.getTurn();
	}

	public double getRequiredTilt() {
		return vision.getTilt();
	}

	public double getVisionDistance() {
		return vision.getDistance();
	}

	public int getVisionState() {
		return vision.getVisionState();
	}

	public boolean visionShotReady() {
		if (!TorqueMathUtil.near(getRequiredTurn(), 0, 2.0)) {
			// must be within 2 degrees of required turn
			return false;
		}
		if (!TorqueMathUtil.near(getTiltAngle(), getRequiredTilt(), 0.5)) {
			// must be within .5 degrees of tilt angle
			return false;
		}
		if (!TorqueMathUtil.near(getFlywheelVelocity(), Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble(), 100)) {
			// must be within 100 rpm of flywheel velocity
			return false;
		}
		return true;
	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("VISION_STATE", getVisionState());

		SmartDashboard.putNumber("Turn", vision.getTurn());
		SmartDashboard.putNumber("Tilt", vision.getTilt());
		SmartDashboard.putNumber("Distance", vision.getDistance());
		SmartDashboard.putNumber("Tilt1", vision.getTilt1());
		SmartDashboard.putNumber("Tilt2", vision.getTilt2());
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
