package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.constants.PortsBravo;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.component.TorqueGyro;
import org.texastorque.torquelib.component.TorquePotentiometer;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_CONVERSION = 0.084883;
	private static final double FLYWHEEL_CONVERSION = .24;

	// sensors
	private VisionFeedback vision;

	// private I2C lidar;

	// private ADXRS450_Gyro gyro;
	private TorqueGyro gyro;

	private TorqueEncoder leftDriveEncoder;
	private TorqueEncoder rightDriveEncoder;

	private TorqueEncoder flywheelEncoder;

	private TorquePotentiometer tiltPot;
	private TorquePotentiometer compressionPot;

	private DigitalInput compressionSensor;
	private DigitalInput tiltLimitSwitch;

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

	// other values

	private double lidarDistance;
	
	private boolean tiltLimitSwitchActive;

	public Feedback() {
		vision = VisionFeedback.getInstance();

		// lidar = new I2C(I2C.Port.kMXP, 62);

		// gyro = new ADXRS450_Gyro();
		gyro = new TorqueGyro(PortsBravo.TURNING_GYRO_A, PortsBravo.TURNING_GYRO_B);
		leftDriveEncoder = new TorqueEncoder(PortsBravo.DRIVE_LEFT_ENCODER_A, PortsBravo.DRIVE_LEFT_ENCODER_B, false,
				EncodingType.k4X);
		rightDriveEncoder = new TorqueEncoder(PortsBravo.DRIVE_RIGHT_ENCODER_A, PortsBravo.DRIVE_RIGHT_ENCODER_B, false,
				EncodingType.k4X);

		flywheelEncoder = new TorqueEncoder(PortsBravo.FLYWHEEL_ENCODER_A, PortsBravo.FLYWHEEL_ENCODER_B, true,
				EncodingType.k4X);
		tiltPot = new TorquePotentiometer(PortsBravo.TILT_POT_PORT);

		compressionPot = new TorquePotentiometer(PortsBravo.COMPRESSION_POT);
		compressionSensor = new DigitalInput(PortsBravo.COMPRESSON_SENSOR);

		tiltLimitSwitch = new DigitalInput(PortsBravo.TILT_LIMIT_SWITCH);
	}

	public void init() {
		tiltPot.setInputRange(Constants.S_TILT_MIN_VOLTAGE.getDouble(), Constants.S_TILT_MAX_VOLTAGE.getDouble());
		tiltPot.setPositionRange(Constants.S_TILT_MIN_ANGLE.getDouble(), Constants.S_TILT_MAX_ANGLE.getDouble());
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

		flywheelVelocity = flywheelEncoder.getRate() * FLYWHEEL_CONVERSION;

		SmartDashboard.putNumber("POTVALUE", tiltPot.getRaw());

		tiltAngle = tiltPot.getPosition();

		// byte[] temp = new byte[8];
		// lidar.readOnly(temp, 8);
		// lidarDistance = (double) temp[0];
		// lidarDistance = ByteBuffer.wrap(temp).getDouble();
		
		tiltLimitSwitchActive = tiltLimitSwitch.get();

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

	public double getCompressionValue() {
		return compressionPot.getRaw();
	}

	public boolean isCompressionTestReady() {
		return compressionSensor.get();
	}
	
	public boolean isTiltLimitSwitchActive() {
		return tiltLimitSwitchActive;
	}

	public double getLidarDistance() {
		return lidarDistance;
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

	public boolean visionShotReady() {
		if (!TorqueMathUtil.near(getRequiredTilt(), 0, 5.0)) {
			// must be within 5 degrees of required tilt
			return false;
		}
		if (!TorqueMathUtil.near(getRequiredTurn(), 0, 5.0)) {
			// must be with 5 degrees of required turn
			return false;
		}
		if (!TorqueMathUtil.near(getFlywheelVelocity(), Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble(), 500)) {
			// must be near flywheel velocity
			return false;
		}
		return false;
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
