package org.texastorque.input;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.feedback.VisionFeedback;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueMathUtil;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.Timer;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private TorqueToggle brakes;
	private TorqueToggle compressionTester;

	private double y;
	private double lastControl;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakes = new TorqueToggle();
		compressionTester = new TorqueToggle();

		lastControl = Timer.getFPGATimestamp();
	}

	public void update() {
		// driver
		if (Timer.getFPGATimestamp() - lastControl < 1) {
		} else if (Math.abs(driver.getLeftYAxis() - y) > .5 && leftDriveSpeed != 0.0 && rightDriveSpeed != 0.0) {
			y += TorqueMathUtil.addSign(leftDriveSpeed, .01);
			y += TorqueMathUtil.addSign(rightDriveSpeed, .01);
			lastControl = Timer.getFPGATimestamp();
			y = driver.getLeftYAxis();
		} else {
			y = driver.getLeftYAxis();
		}

		leftDriveSpeed = -y + driver.getRightXAxis();
		rightDriveSpeed = -y - driver.getRightXAxis();

		brakes.calc(driver.getAButton());
		braking = brakes.get();

		// operator
		if (operator.getLeftCenterButton()) {
			override = true;
		} else if (operator.getRightCenterButton()) {
			override = false;
		}

		intaking = operator.getRightBumper();
		outtaking = operator.getRightTrigger();

		conveyorIntaking = operator.getLeftBumper();
		conveyorOuttaking = operator.getLeftTrigger();

		layupShot = operator.getYButton();
		longShot = operator.getBButton();

		compressionTester.calc(operator.getRightStickClick());
		compressionTesting = compressionTester.get();

		prevVisionLock = visionLock;
		visionLock = operator.getXButton();
		if (prevVisionLock != visionLock && visionLock == true) {
			VisionFeedback.init();
		}
		if (operator.getAButton()) {
			flywheelActive = true;
			if (Feedback.getInstance().getFlywheelVelocity() > Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble()
					* .95) {
				conveyorIntaking = true;
				intaking = true;
			}
		} else {
			flywheelActive = false;
		}

		tiltSetpoint += -operator.getLeftYAxis();
		if (tiltSetpoint >= 30) {
			tiltSetpoint = 30;
		} else if (tiltSetpoint <= -3) {
			tiltSetpoint = -3;
		}
		tiltMotorSpeed = -operator.getLeftYAxis();
		
		if (layupShot) {
			tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
		}
		if (longShot) {
			tiltSetpoint = Constants.S_LONG_SHOT_ANGLE_SETPOINT.getDouble();
		}
		if (visionLock) {
			tiltSetpoint = Feedback.getInstance().getRequiredTilt();
		}
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
