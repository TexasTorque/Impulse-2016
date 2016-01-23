package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intakes extends Subsystem{

	private double MAX_SPEED;
	
	private double leftSpeed;
	private double rightSpeed;
	
	@Override
	public void init() {
		MAX_SPEED = Constants.I_MAX_SPEED.getDouble();
	}

	@Override
	public void run() {
		leftSpeed = feedback.getLeftIntakeSpeed();
		rightSpeed = feedback.getRightIntakeSpeed();
		output();
	}

	@Override
	protected void output() {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, MAX_SPEED);
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, MAX_SPEED);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("INTAKE_LEFT_SPEED", leftSpeed);
		SmartDashboard.putNumber("INTAKE_RIGHT_SPEED", rightSpeed);
	}

	
	
}
