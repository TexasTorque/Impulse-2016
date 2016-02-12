package org.texastorque.feedback;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_CONVERSION = 8 * Math.PI;// meters

	// sensors
	private VisionFeedback vision;
	private ADXRS450_Gyro gyro;

	// values
	private double leftDrivePosition;
	private double leftDriveVelocity;
	private double leftDriveAcceleration;

	private double rightDrivePosition;
	private double rightDriveVelocity;
	private double rightDriveAcceleration;

	private double angle;
	private double angularVelocity;

	public Feedback() {
		vision = VisionFeedback.getInstance();
		gyro = new ADXRS450_Gyro();
	}

	public void update() {
		vision.update();
		angle = gyro.getAngle() % 360.0;
		angularVelocity = gyro.getRate();
	}

	public void resetDriveEncoders() {
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
