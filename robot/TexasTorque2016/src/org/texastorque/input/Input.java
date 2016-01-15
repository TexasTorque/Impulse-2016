package org.texastorque.input;

public abstract class Input {
	
	//dynamic variables
	protected double leftDriveSpeed = 0.0;
	protected double rightDriveSpeed = 0.0;
	
	//override variables
	protected boolean override = false;
	
	public abstract void update();
	
	public double getLeftDriveSpeed() {
		return leftDriveSpeed;
	}
	
	public double getRightDriveSpeed() {
		return rightDriveSpeed;
	}
	
	public boolean isOverride() {
		return false;
	}
}
