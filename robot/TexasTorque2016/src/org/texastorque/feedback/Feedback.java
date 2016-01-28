package org.texastorque.feedback;

public class Feedback {

	private static Feedback instance;

	private static final double DRIVEBASE_CONVERSION = 8 * Math.PI;// inches

	// sensors

	private double leftDrivePosition;
	private double leftDriveVelocity;
	private double leftDriveAcceleration;

	private double rightDrivePosition;
	private double rightDriveVelocity;
	private double rightDriveAcceleration;

	// values

	public void update() {
	}

	public void resetDriveEncoders() {

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

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
