package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DoubleArm extends Subsystem {

	private static DoubleArm instance;

	private double leftArmSpeed;
	private double rightArmSpeed;

	private double armSetpoint;
	private double leftArmAngle;
	private double rightArmAngle;

	private boolean armHold;

	// profiling
	private TorquePID leftArmPID;
	private TorquePID rightArmPID;

	@Override
	public void init() {
		leftArmPID = new TorquePID();
		leftArmPID.setPIDGains(Constants.ARM_P.getDouble(), Constants.ARM_I.getDouble(), Constants.ARM_D.getDouble());
		leftArmPID.setMaxOutput(1.0);

		rightArmPID = new TorquePID();
		rightArmPID.setPIDGains(Constants.ARM_P.getDouble(), Constants.ARM_I.getDouble(), Constants.ARM_D.getDouble());
		rightArmPID.setMaxOutput(1.0);
	}

	@Override
	public void run() {
		leftArmAngle = feedback.getLeftArmAngle();
		rightArmAngle = feedback.getRightArmAngle();

		if (input.isOverride()) {
			leftArmSpeed = rightArmSpeed = input.getArmOverrideSpeed() / 3.0;
		} else {
			armSetpoint = input.getArmSetpoint();
			leftArmPID.setSetpoint(armSetpoint);
			rightArmPID.setSetpoint(armSetpoint);

			leftArmSpeed = leftArmPID.calculate(leftArmAngle);
			rightArmSpeed = rightArmPID.calculate(rightArmAngle);
		}

		output();
	}

	@Override
	protected void output() {
		output.setArmSpeeds(leftArmSpeed, rightArmSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("LeftArmAngle", leftArmAngle);
		SmartDashboard.putNumber("RightArmAngle", rightArmAngle);

		SmartDashboard.putNumber("ArmSetpoint", armSetpoint);
		SmartDashboard.putBoolean("ArmHold", armHold);

		SmartDashboard.putNumber("LeftArmSpeed", leftArmSpeed);
		SmartDashboard.putNumber("RightArmSpeed", rightArmSpeed);
	}

	// singleton
	public static DoubleArm getInstance() {
		return instance == null ? instance = new DoubleArm() : instance;
	}
}
