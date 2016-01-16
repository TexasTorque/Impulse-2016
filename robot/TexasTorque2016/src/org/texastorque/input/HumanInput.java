package org.texastorque.input;

import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	// helper variables
	private TorqueToggle overrideToggle;

	private HumanInput() {
		overrideToggle = new TorqueToggle();
	}

	public void update() {
		leftDriveSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		rightDriveSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();

		if (driver.getLeftCenterButton()) {
			override = true;
		} else if (driver.getRightCenterButton()) {
			override = false;
		}
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
