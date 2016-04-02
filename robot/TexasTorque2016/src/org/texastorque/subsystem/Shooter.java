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
	private boolean flywheelActive;

	// tilt profiling
	private TorquePID tiltPID;

	private double tiltSetpoint;

	@Override
	public void init() {
		flywheelControl = new BangBang();
		flywheelControl.setSetpoint(Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble());

		tiltPID = new TorquePID(Constants.S_TILT_P.getDouble(), Constants.S_TILT_I.getDouble(),
				Constants.S_TILT_D.getDouble());
		tiltPID.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		tiltPID.setMaxOutput(.5);
	}

	@Override
	public void run() {
		tiltAngle = feedback.getTiltAngle();
		flywheelVelocity = feedback.getFlywheelVelocity();
		flywheelActive = input.isFlywheelActive() || input.isVisionLock() || input.isShooting();

		if (input.isOverride()) {
			tiltSpeed = input.getTiltOverrideSpeed();
			if (input.isOverrideReset()) {
				feedback.resetTiltEncoder();
			}
		} else {
			if (input.isVisionLock()) {
				tiltSetpoint = feedback.getRequiredTilt();
			} else if (input.isLongShot()) {
				tiltSetpoint = Constants.S_LONG_SHOT_SETPOINT.getDouble();
			} else if (input.isLayupShot()) {
				tiltSetpoint = Constants.S_LAYUP_ANGLE_SETPOINT.getDouble();
			} else if (input.isBatterShot()) {
				tiltSetpoint = Constants.S_BATTER_SHOT_SETPOINT.getDouble();
			} else {
				tiltSetpoint = Constants.S_DOWN_SETPOINT.getDouble();
			}
			if (tiltSetpoint >= 38) {
				tiltSetpoint = 38;
			} else if (tiltSetpoint <= Constants.S_DOWN_SETPOINT.getDouble()) {
				tiltSetpoint = Constants.S_DOWN_SETPOINT.getDouble();
			}

			tiltPID.setSetpoint(tiltSetpoint);

			tiltSpeed = tiltPID.calculate(tiltAngle);
		}

		if (flywheelActive)

		{// 8000 max rpm
			flywheelSpeed = flywheelControl.calculate(flywheelVelocity);
		} else {
			flywheelSpeed = 0.0;
		}

		output();

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

		SmartDashboard.putBoolean("FlywheelActive", flywheelActive);
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelSpeed);
		SmartDashboard.putNumber("FlywheelVelocity", flywheelVelocity);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}