package org.texastorque.input;

import org.texastorque.torquelib.util.GenericController;

public class HumanInput extends Input {

	private static HumanInput instance;

	// controllers
	private GenericController driver;
	private GenericController operator;
	
	private HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);
	}

	public void update() {
		leftDriveSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		rightDriveSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();

		topIntakeSpeed = operator.getLeftYAxis();
		bottomIntakeSpeed = operator.getLeftYAxis();
		
		if (driver.getLeftCenterButton()) {
			override = true;
		} else if (driver.getRightCenterButton()) {
			override = false;
		}
		
		driveBoost = driver.getXButton();
		
		visionLock = driver.getAButton();
	}

	// singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
