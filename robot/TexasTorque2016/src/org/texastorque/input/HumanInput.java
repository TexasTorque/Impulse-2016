package org.texastorque.input;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.VisionFeedback;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private TorqueToggle brakes;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakes = new TorqueToggle();
	}

	public void update() {
		// driver
		leftDriveSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		rightDriveSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();

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
		// longShot = operator.getYButton();

		if (operator.getDPADUp()) {
			armSetpoint = Constants.ARM_UP_SETPOINT.getDouble();
		} else if (operator.getDPADDown()) {
			armSetpoint = Constants.ARM_DOWN_SETPOINT.getDouble();
		}
		armSpeed = operator.getRightYAxis();

		prevVisionLock = visionLock;
		visionLock = operator.getXButton();
		if (prevVisionLock != visionLock && visionLock == true) {
			VisionFeedback.init();
		}

		flywheelActive = operator.getAButton();

		tiltSetpoint += -operator.getLeftYAxis() / 3.0;
		if (tiltSetpoint >= 35) {
			tiltSetpoint = 35;
		} else if (tiltSetpoint <= -7) {
			tiltSetpoint = -7;
		}

		tiltMotorSpeed = -operator.getLeftYAxis() / 3.0;

		if (layupShot) {
			tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
		}
		if (operator.getBButton()) {
			tiltSetpoint = -6.0;
		}
		// if (longShot) {
		// tiltSetpoint = Constants.S_LONG_SHOT_ANGLE_SETPOINT.getDouble();
		// }
		if (visionLock) {
			tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
		}

		overrideReset = operator.getRightStickClick();
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
