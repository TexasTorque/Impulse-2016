package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.BangBang;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	private static Shooter instance;

	private double tiltSpeed;
	private double flywheelSpeed;

	// sensor
	private double tiltAngle;
	private double flywheelVelocity;

	// flywheel profiling
	private BangBang flywheelControl;
	private double flywheelSetpoint;

	// tilt profiling
	private TorquePID tiltPID;

	private double tiltSetpoint;

	@Override
	public void initSystem() {
		flywheelControl = new BangBang(0.5, .75);

		tiltPID = new TorquePID(Constants.S_TILT_P.getDouble(), Constants.S_TILT_I.getDouble(),
				Constants.S_TILT_D.getDouble());
		tiltPID.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		tiltPID.setMaxOutput(.4);
	}

	@Override
	public void runSystem() {
		tiltAngle = feedback.getTiltAngle();
		flywheelVelocity = feedback.getFlywheelVelocity();

		if (input.isVisionLock()) {
			tiltSetpoint = feedback.getRequiredTilt();
			flywheelSetpoint = Constants.S_VISION_FLYWHEEL.getDouble();
		} else if (input.isLongShot()) {
			tiltSetpoint = Constants.S_LONG_SHOT_SETPOINT.getDouble();
			flywheelSetpoint = Constants.S_LONG_FLYWHEEL.getDouble();
		} else if (input.isLayupShot()) {
			tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
			flywheelSetpoint = Constants.S_LAYUP_FLYWHEEL.getDouble();
		} else if (input.isBatterShot()) {
			tiltSetpoint = Constants.S_BATTER_SHOT_SETPOINT.getDouble();
			flywheelSetpoint = Constants.S_BATTER_FLYWHEEL.getDouble();
		} else {
			tiltSetpoint = Constants.S_DOWN_SETPOINT.getDouble();
			flywheelSetpoint = 0;
		}

		if (input.isHoodOverrideReset()) {
			feedback.resetTiltEncoder();
		}

		if (input.isOverride() || input.isHoodOverride()) {
			tiltSpeed = input.getTiltOverrideSpeed();
		} else {
			tiltPID.setSetpoint(tiltSetpoint);

			tiltSpeed = tiltPID.calculate(tiltAngle);
		}

		if (flywheelSetpoint != 0) {
			flywheelControl.setSetpoint(flywheelSetpoint);
			flywheelSpeed = flywheelControl.calculate(flywheelVelocity);
		} else {
			flywheelSpeed = 0.0;
		}
	}

	@Override
	protected void output() {
		output.setTiltSpeeds(tiltSpeed);
		output.setFlywheelSpeed(flywheelSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("TiltSpeed", tiltSpeed);
		SmartDashboard.putNumber("TiltAngle", tiltAngle);
		SmartDashboard.putNumber("TiltSetpoint", tiltSetpoint);

		SmartDashboard.putNumber("FlywheelSetpointVelocity", flywheelSetpoint);
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelSpeed);
		SmartDashboard.putNumber("FlywheelVelocity", flywheelVelocity);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}