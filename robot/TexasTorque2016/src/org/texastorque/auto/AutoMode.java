package org.texastorque.auto;

import org.texastorque.auto.AutoModes.DefensePosition;
import org.texastorque.feedback.Feedback;
import org.texastorque.input.Input;
import org.texastorque.subsystem.Drivebase.DriveControlType;

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
			thread.interrupt();
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

	protected final void vision() {
		driveControlType = DriveControlType.VISION;
		visionLock = true;
		pause(5.0);
		visionLock = false;
	}

	protected final void postDefenseVision() {
		turn(-Feedback.getInstance().getAngle() * 1.2);
		pause(1.0);
		switch (currentDefense) {
		case ZERO:
			// do nothing just cross
			break;
		case ONE:
			drive(84);
			pause(3.0);
			turn(60);
			pause(3.0);
			drive(63);
			pause(2.0);
			break;
		case TWO:
			drive(84);
			pause(3.0);
			turn(90);
			pause(3.0);
			drive(96);
			pause(2.0);
			turn(-90);
			pause(3.0);
			break;
		case THREE:
			drive(84);
			pause(3.0);
			turn(90);
			pause(3.0);
			drive(54);
			pause(2.0);
			turn(-90);
			pause(3.0);
			break;
		case FOUR:
			turn(5.0);
			pause(0.5);
			drive(85);
			pause(3.0);
			break;
		case FIVE:
			drive(84);
			pause(3.0);
			turn(-90);
			pause(3.0);
			drive(54);
			pause(2.0);
			turn(90);
			pause(3.0);
			break;
		}
		vision();
	}
}
