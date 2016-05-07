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
	private TorqueToggle otherToggle;

	private boolean tempRPMDownshift = false;
	private boolean tempRPMUpshift = false;

	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		brakesToggle = new TorqueToggle();
		flashlightToggle = new TorqueToggle();
		armToggle = new TorqueToggle(true);
		otherToggle = new TorqueToggle();
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

		rpmDownshift = driver.getDPADDown() && !tempRPMDownshift;
		tempRPMDownshift = driver.getDPADDown();

		rpmUpshift = driver.getDPADUp() && !tempRPMUpshift;
		tempRPMUpshift = driver.getDPADUp();

		visionLock = driver.getXButton();

		armToggle.calc(driver.getBButton());
		armUp = armToggle.get();

		if (driver.getLeftCenterButton()) {
			armOverride = true;
		} else if (driver.getRightCenterButton()) {
			armOverride = false;
		}
		if (driver.getLeftBumper()) {
			armOverrideLeftSpeed = -0.2;
		} else if (driver.getLeftTrigger()) {
			armOverrideLeftSpeed = 0.2;
		} else {
			armOverrideLeftSpeed = 0.0;
		}
		if (driver.getRightBumper()) {
			armOverrideRightSpeed = -0.2;
		} else if (driver.getRightTrigger()) {
			armOverrideRightSpeed = 0.2;
		} else {
			armOverrideRightSpeed = 0.0;
		}

		otherToggle.calc(driver.getRightStickClick());
		other = otherToggle.get();

		// operator
		intaking = operator.getRightBumper();
		outtaking = operator.getRightTrigger();

		conveyorIntaking = operator.getLeftBumper();
		conveyorOuttaking = operator.getLeftTrigger();

		longShot = operator.getYButton();
		batterShot = operator.getBButton();
		layupShot = operator.getAButton();

		hoodReady = operator.getDPADUp() || operator.getDPADUpLeft() || operator.getDPADUpRight();

		visionLock = visionLock || operator.getXButton();

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

	public void rumbleCalc(double value, double setpoint) {
		operator.setRumble(value > setpoint && setpoint != 0.0);
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
