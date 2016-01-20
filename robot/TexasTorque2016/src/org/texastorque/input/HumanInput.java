package org.texastorque.input;

import org.texastorque.torquelib.util.GenericController;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;

	private HumanInput() {
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
