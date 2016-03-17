package org.texastorque.input;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.feedback.VisionFeedback;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private TorqueToggle brakes;
	private TorqueToggle compressionTester;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakes = new TorqueToggle();
		compressionTester = new TorqueToggle();
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
		longShot = operator.getBButton();

//		compressionTester.calc(operator.getRightStickClick());
//		compressionTesting = compressionTester.get();

		mechanismSetpoint += -operator.getRightYAxis() * .01;
		if (mechanismSetpoint >= 1) { 
			mechanismSetpoint = 1;
		} else if (mechanismSetpoint <= 0) {
			mechanismSetpoint = 0;
		}
		mechanismHold = operator.getRightStickClick();
		if (mechanismHold) {
			mechanismSetpoint = Constants.AMECH_HOLD_SETPOINT.getDouble();
		}
		mechanismSpeed = -operator.getRightYAxis();

		prevVisionLock = visionLock;
		visionLock = operator.getXButton();
		if (prevVisionLock != visionLock && visionLock == true) {
			VisionFeedback.init();
		}
		if (operator.getAButton()) {
			flywheelActive = true;
			if (Feedback.getInstance().getFlywheelVelocity() > Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble()) {
				conveyorIntaking = true;
				intaking = true;
			}
		} else {
			flywheelActive = false;
		}

		tiltSetpoint += -operator.getLeftYAxis();
		if (tiltSetpoint >= 37) {
			tiltSetpoint = 37;
		} else if (tiltSetpoint <= -10) {
			tiltSetpoint = -10;
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
