package org.texastorque.input;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
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

		// operator
		if (driver.getLeftCenterButton()) {
			override = true;
		} else if (driver.getRightCenterButton()) {
			override = false;
		}

		brakes.calc(driver.getAButton());
		brakeing = brakes.get();

		intaking = operator.getRightBumper();
		outtaking = operator.getRightTrigger();

		conveyorIntaking = operator.getLeftBumper();
		conveyorOuttaking = operator.getLeftTrigger();

		if (operator.getAButton()) {
			flywheelActive = true;
			if (Feedback.getInstance().getFlywheelVelocity() > Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble() * .9) {
				conveyorIntaking = true;
				intaking = true;
			}
		} else {
			flywheelActive = false;
		}

		tiltMotorSpeed = -operator.getLeftYAxis();
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
