package org.texastorque.input;

import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput {
	
	private static HumanInput instance;
	
	//controllers
	private GenericController driver;
	private GenericController operator;
	
	//variables
	private double leftDriveSpeed;
	private double rightDriveSpeed;
	
	private boolean override;
	private TorqueToggle overrideToggle;
	
	private HumanInput() {
		driver = new GenericController(0, .12);
		operator = new GenericController(1, .12);
		
		override = false;
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
	
	//singleton
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
