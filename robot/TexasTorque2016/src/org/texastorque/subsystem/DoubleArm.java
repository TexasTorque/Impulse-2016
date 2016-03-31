package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Mechanism extends Subsystem {

	private static Mechanism instance;

	private double mechanismSpeed;

	private double mechanismSetpoint;
	private double mechanismAngle;
	private double mechanismPotRaw;
	private boolean mechanismHold;

	// profiling
	private TorquePID mechanismPID;

	@Override
	public void init() {
		mechanismPID = new TorquePID();
		mechanismPID.setPIDGains(Constants.AMECH_P.getDouble(), Constants.AMECH_I.getDouble(),
				Constants.AMECH_D.getDouble());
		mechanismPID.setMaxOutput(1.0);
	}

	@Override
	public void run() {
		mechanismAngle = feedback.getMechanismAngle();
		mechanismPotRaw = feedback.getMechanismPotRaw();

//		if (input.isOverride()) {
			mechanismSpeed = input.getMechanismOverrideSpeed() / 2.0;
//		} else {
//			mechanismHold = input.isMechanismHold();
//			if (mechanismHold) {
//				mechanismSetpoint = Constants.AMECH_HOLD_SETPOINT.getDouble();
//			} else {
//				mechanismSetpoint = input.getMechanismSetpoint();
//			}
//			mechanismPID.setSetpoint(mechanismSetpoint);

//			mechanismSpeed = mechanismPID.calculate(mechanismAngle);
//		}

		output();
	}

	@Override
	protected void output() {
		output.setMechanismSpeed(mechanismSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("MechanismAngle", mechanismAngle);
		SmartDashboard.putNumber("MechanismPotRaw", mechanismPotRaw);
		SmartDashboard.putNumber("MechanismSetpoint", mechanismSetpoint);
		SmartDashboard.putBoolean("MechanismHold", mechanismHold);

		SmartDashboard.putNumber("MechanismSpeed", mechanismSpeed);
	}

	// singleton
	public static Mechanism getInstance() {
		return instance == null ? instance = new Mechanism() : instance;
	}
}
