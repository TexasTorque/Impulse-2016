package org.texastorque.auto;

import org.texastorque.auto.AutoModes.DefensePosition;
import org.texastorque.feedback.Feedback;
import org.texastorque.input.DriveControlType;
import org.texastorque.input.Input;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoMode extends Input {

	public static DefensePosition currentDefense;

	private Thread thread;

	public final void start() {
		SmartDashboard.putString("RunningAutoMode", getClass().getSimpleName());
		SmartDashboard.putString("RunningDefensePosition", currentDefense.toString());
		thread = new Thread(() -> run());
		thread.start();
	}

	public final void stop() {
		try {
			thread.join(500);
		} catch (Exception e) {
		}
	}

	protected abstract void run();

	protected abstract double getLinearMaxSpeed();

	@Override
	public void update() {
		// auto modes do not update
	}

	protected final void pause(double seconds) {
		double start = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - start < seconds) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		}
	}

	protected final void drive(double distance) {
		driveControlType = DriveControlType.LINEAR;
		driveSetpoint = distance;
	}

	protected final void turn(double degrees) {
		driveControlType = DriveControlType.TURN;
		turnSetpoint = degrees;
	}

	protected final void drive_manual(double left, double right, double pauseTime) {
		override = true;
		leftDriveSpeed = left;
		rightDriveSpeed = right;
		pause(pauseTime);
		leftDriveSpeed = 0.0;
		rightDriveSpeed = 0.0;
		override = false;
	}

	protected final void vision() {
		driveControlType = DriveControlType.VISION;
		visionLock = true;
		pause(5.0);
		visionLock = false;
	}

	protected final void postDefenseVision() {
		turn(-Feedback.getInstance().getAngle());
		pause(1.0);
		drive(84);
		pause(3.0);
		switch (currentDefense) {
		case ZERO:
			// do nothing just cross
			break;
		case ONE:
			turn(60);
			pause(3.0);
			drive(63);
			pause(2.0);
			break;
		case TWO:
			turn(90);
			pause(3.0);
			drive(72);
			pause(2.0);
			turn(-90);
			pause(3.0);
			break;
		case THREE:
			break;
		case FOUR:
			break;
		case FIVE:
			break;
		}
		vision();
	}
}
