package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	private static Shooter instance;

	private double leftTiltMotorSpeed;
	private double rightTiltMotorSpeed;
	private double flywheelMotorSpeed;
	
	@Override
	public void init() {
	}

	@Override
	public void run() {
		leftTiltMotorSpeed = input.getLeftTiltMotorSpeed();
		rightTiltMotorSpeed = input.getLeftTiltMotorSpeed();
		flywheelMotorSpeed = input.getFlywheelMotorSpeed();
	}

	@Override
	protected void output() {
		output.setTiltSpeeds(leftTiltMotorSpeed, rightTiltMotorSpeed);
		output.setFlywheelSpeed(flywheelMotorSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelMotorSpeed);
		SmartDashboard.putNumber("LeftTiltMotorSpeed", leftTiltMotorSpeed);
		SmartDashboard.putNumber("RightTiltMotorSpeed", rightTiltMotorSpeed);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}