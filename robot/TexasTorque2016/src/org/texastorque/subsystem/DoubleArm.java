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

	private boolean armUp;

	// profiling
	private TorquePID leftArmPID;
	private TorquePID rightArmPID;

	@Override
	public void initSystem() {
		leftArmPID = new TorquePID();
		leftArmPID.setPIDGains(Constants.ARM_P.getDouble(), Constants.ARM_I.getDouble(), Constants.ARM_D.getDouble());
		leftArmPID.setMaxOutput(.6);

		rightArmPID = new TorquePID();
		rightArmPID.setPIDGains(Constants.ARM_P.getDouble(), Constants.ARM_I.getDouble(), Constants.ARM_D.getDouble());
		rightArmPID.setMaxOutput(.6);
	}

	@Override
	public void runSystem() {
		leftArmAngle = feedback.getLeftArmAngle();
		rightArmAngle = feedback.getRightArmAngle();
		armUp = input.isArmUp();

		if (armUp) {
			armSetpoint = Constants.ARM_UP_SETPOINT.getDouble();
		} else {
			armSetpoint = Constants.ARM_DOWN_SETPOINT.getDouble();
		}

		if (input.isOverride()) {
			leftArmSpeed = rightArmSpeed = input.getArmOverrideSpeed();
		} else {
			leftArmPID.setSetpoint(armSetpoint);
			rightArmPID.setSetpoint(armSetpoint);

			leftArmSpeed = leftArmPID.calculate(leftArmAngle);
			rightArmSpeed = rightArmPID.calculate(rightArmAngle);
		}
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
		SmartDashboard.putBoolean("ArmUp", armUp);

		SmartDashboard.putNumber("LeftArmSpeed", leftArmSpeed);
		SmartDashboard.putNumber("RightArmSpeed", rightArmSpeed);
	}

	// singleton
	public static DoubleArm getInstance() {
		return instance == null ? instance = new DoubleArm() : instance;
	}
}
