package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	private static Shooter instance;

	private double tiltSpeed;
	private double flywheelSpeed;

	// sensor
	private double tiltAngle;
	private double flywheelVelocity;
	private boolean hoodReady;

	// flywheel profiling
	private TorquePID flywheelControl;
	private double flywheelSetpoint;

	// tilt profiling
	private TorquePID tiltPID;

	private double tiltSetpoint;

	@Override
	public void initSystem() {
		flywheelControl = new TorquePID(0.6, 0.05, 0.1, 0.8, 0.75);
		flywheelControl.setControllingSpeed(true);
		flywheelControl.setEpsilon(250);

		tiltPID = new TorquePID(Constants.S_TILT_P.getDouble(), Constants.S_TILT_I.getDouble(),
				Constants.S_TILT_D.getDouble());
		tiltPID.setMaxOutput(.4);
	}

	@Override
	public void runSystem() {
		tiltAngle = feedback.getTiltAngle();
		flywheelVelocity = feedback.getFlywheelVelocity();

		hoodReady = input.isHoodReady();

		if (input.isRPMDownshift()) {
			Constants.S_LONG_FLYWHEEL.override(Constants.S_LONG_FLYWHEEL.getDouble() - 500);
		}
		if (input.isRPMUpshift()) {
			Constants.S_LONG_FLYWHEEL.override(Constants.S_LONG_FLYWHEEL.getDouble() + 500);
		}

		if (input.isVisionLock()) {
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

		tiltSetpoint = hoodReady || input.isVisionLock() ? tiltSetpoint : Constants.S_DOWN_SETPOINT.getDouble();

		if (input.isTiltOverride()) {
			tiltSpeed = input.getTiltOverrideSpeed();

			if (input.isTiltOverride()) {
				feedback.resetTiltEncoder();
			}
		} else {
			tiltPID.setSetpoint(tiltSetpoint);

			tiltSpeed = tiltPID.calculate(tiltAngle);
		}

		if (flywheelSetpoint != 0) {
			flywheelControl.setSetpoint(flywheelSetpoint);
			flywheelSpeed = flywheelControl.calculate(flywheelVelocity);
		} else {
			flywheelControl.reset();
			flywheelSpeed = 0.0;
		}
	}

	@Override
	protected void output() {
		output.setTiltSpeeds(tiltSpeed);
		output.setFlywheelSpeed(flywheelSpeed);
		output.setRPMReadyState(flywheelVelocity, flywheelSetpoint);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("TiltSpeed", tiltSpeed);
		SmartDashboard.putNumber("TiltAngle", tiltAngle);
		SmartDashboard.putNumber("TiltSetpoint", tiltSetpoint);
		SmartDashboard.putBoolean("HoodReady", hoodReady);

		SmartDashboard.putNumber("FlywheelSetpointVelocity", flywheelSetpoint);
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelSpeed);
		SmartDashboard.putNumber("FlywheelVelocity", flywheelVelocity);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}