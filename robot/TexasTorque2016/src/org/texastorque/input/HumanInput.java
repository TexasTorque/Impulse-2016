package org.texastorque.input;

import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueMathUtil;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private TorqueToggle brakesToggle;
	private TorqueToggle flashlightToggle;

	private double y;
	private double prevY;

	private boolean prevHoodOverride;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakesToggle = new TorqueToggle();
		flashlightToggle = new TorqueToggle();
	}

	public void update() {
		hoodOverrideReset = false;

		// driver
		prevY = y;
		y = driver.getLeftYAxis();

		if (Math.abs(prevY - y) > .5 && !TorqueMathUtil.near(Math.abs(y), 0, .1)) {
			y = 0.0;
		}

		leftDriveSpeed = -y + driver.getRightXAxis();
		rightDriveSpeed = -y - driver.getRightXAxis();

		brakesToggle.calc(driver.getAButton());
		braking = brakesToggle.get();

		flashlightToggle.calc(driver.getRightTrigger() || driver.getRightBumper());
		flashlight = flashlightToggle.get();

		visionLock = driver.getXButton();

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
		batterShot = operator.getBButton();
		layupShot = operator.getAButton();

		visionLock = visionLock || operator.getXButton();

		if (operator.getDPADUp()) {
			armUp = true;
		} else if (operator.getDPADDown()) {
			armUp = false;
		}
		armSpeed = operator.getRightYAxis() / 4.0;

		tiltMotorSpeed = -operator.getLeftYAxis() / 3.0;

		prevHoodOverride = hoodOverride;
		hoodOverride = operator.getLeftStickClick();

		// post operations
		if (prevHoodOverride != hoodOverride && !hoodOverride) {
			hoodOverrideReset = true;
		}

		if (visionLock) {
			driveControlType = DriveControlType.VISION;
		} else {
			driveControlType = DriveControlType.MANUAL;
		}
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
