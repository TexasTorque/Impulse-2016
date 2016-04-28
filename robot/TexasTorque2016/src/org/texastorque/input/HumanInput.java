package org.texastorque.input;

import org.texastorque.subsystem.Drivebase.DriveControlType;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private TorqueToggle brakesToggle;
	private TorqueToggle flashlightToggle;
	private TorqueToggle armToggle;

	private boolean tempRPMFix = false;
	private boolean tempRPMReturn = false;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakesToggle = new TorqueToggle();
		flashlightToggle = new TorqueToggle();

		armToggle = new TorqueToggle(true);
	}

	public void update() {
		// driver
		flipCheck = -driver.getLeftYAxis() > .25;

		leftDriveSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		rightDriveSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();

		brakesToggle.calc(driver.getAButton());
		braking = brakesToggle.get();

		flashlightToggle.calc(driver.getRightTrigger() || driver.getRightBumper());
		flashlight = flashlightToggle.get();

		rpmFix = driver.getDPADDown() && !tempRPMFix;
		tempRPMFix = driver.getDPADDown();

		rpmReturn = driver.getDPADUp() && !tempRPMReturn;
		tempRPMReturn = driver.getDPADUp();

		visionLock = driver.getXButton();

		armToggle.calc(driver.getBButton());
		armUp = armToggle.get();

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

		hoodReady = operator.getDPADUp();

		visionLock = visionLock || operator.getXButton();

		armOverrideSpeed = operator.getRightYAxis() / 4.0;
		armOverride = operator.getRightStickClick();

		tiltOverrideSpeed = -operator.getLeftYAxis() / 3.0;
		tiltOverride = operator.getLeftStickClick();

		// post operations
		if (visionLock) {
			driveControlType = DriveControlType.VISION;
			hoodReady = true;
		} else {
			driveControlType = DriveControlType.MANUAL;
		}
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
