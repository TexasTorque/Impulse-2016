package org.texastorque.input;

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

		flashlight = driver.getRightBumper();

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

		longShot = operator.getYButton();
		batterShot = operator.getAButton();
		layupShot = operator.getBButton();

		if (operator.getDPADUp()) {
			armUp = true;
		} else if (operator.getDPADDown()) {
			armUp = false;
		}
		armSpeed = operator.getRightYAxis();

		visionLock = operator.getXButton();

		flywheelActive = operator.getAButton();

		tiltSetpoint += -operator.getLeftYAxis() / 3.0;
		tiltMotorSpeed = -operator.getLeftYAxis() / 3.0;

		overrideReset = operator.getRightStickClick();
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
