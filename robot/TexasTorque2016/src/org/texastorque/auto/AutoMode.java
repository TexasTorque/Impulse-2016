package org.texastorque.auto;

import org.texastorque.auto.AutoModes.DefensePosition;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.input.Input;
import org.texastorque.subsystem.Drivebase.DriveControlType;
import org.texastorque.torquelib.controlLoop.TorqueTMP;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoMode extends Input {

	public static DefensePosition currentDefense;

	private Thread thread;

	protected TorqueTMP linearProfile;

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

	protected final void setLinearMaxSpeed(double speed) {
		linearProfile = new TorqueTMP(speed, Constants.D_MAX_ACCELERATION.getDouble());
	}

	protected final TorqueTMP getLinearProfile() {
		return linearProfile;
	}

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
		pause(10.0);
		visionLock = false;
	}

	protected final void postDefenseVision() {
		if (Math.abs(Feedback.getInstance().getAngle()) > 1.0) {
			turn(-Feedback.getInstance().getAngle() * 1.2);
			pause(0.5);
		}

		setLinearMaxSpeed(85.0);
		if(Constants.DEBUG_DO_POST_DEFENSE_DRIVING.getBoolean()) {
			switch (currentDefense) {
				case ZERO:
					// do nothing just cross
					break;
				case ONE:
					drive(80);
					pause(3.0);
					turn(60);
					pause(3.0);
					drive(60);
					pause(2.0);
					break;
				case TWO:
					drive(80);
					pause(3.0);
					turn(90);
					pause(3.0);
					drive(96);
					pause(2.0);
					turn(-90);
					pause(3.0);
					break;
				case THREE:
					turn(20);
					pause(1.0);
					drive(57);
					pause(3.0);
					turn(-10);
					pause(1.0);
					break;
				case FOUR:
					turn(5.0);
					pause(0.5);
					drive(80);
					pause(3.0);
					break;
				case FIVE:
					drive(75);
					pause(3.0);
					turn(-90);
					pause(3.0);
					drive(62);
					pause(2.0);
					turn(90);
					pause(3.0);
					break;
				} 
			} else {
				switch (currentDefense) {
				case ZERO:
					// do nothing just cross
					break;
				case ONE:
					turn(60);
					pause(3.0);
					break;
				case TWO:
					turn(90);
					pause(3.0);
					drive(96);
					pause(2.0);
					turn(-90);
					pause(3.0);
					break;
				case THREE:
					break;
				case FOUR:
					turn(5.0);
					pause(0.5);
					break;
				case FIVE:
					drive(75);
					pause(3.0);
					turn(-90);
					pause(3.0);
					drive(62);
					pause(2.0);
					turn(90);
					pause(3.0);
					break;
				}
			}
		if (currentDefense == DefensePosition.ZERO) {
			driveControlType = DriveControlType.MANUAL;
		} else {
			vision();
		}
	}
}
