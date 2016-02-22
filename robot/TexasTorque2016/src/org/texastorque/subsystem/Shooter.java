package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.BangBang;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	private static Shooter instance;

	private double tiltSpeed;
	private double flywheelSpeed;

	// sensor
	private double tiltAngle;
	private double flywheelVelocity;

	// generic profile variables
	private double prevTime;

	// flywheel profiling
	private BangBang flywheelControl;

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
		tiltPID.setMaxOutput(1.0);
		tiltPID.reset();

		prevTime = Timer.getFPGATimestamp();
	}

	@Override
	public void run() {
		tiltAngle = feedback.getTiltAngle();
		flywheelVelocity = feedback.getFlywheelVelocity();

		if (input.getTiltSetpoint() != 0.0 && !input.isOverride()) {
			if (input.isVisionLock()) {
				tiltSetpoint = feedback.getRequiredTilt();
			} else {
				tiltSetpoint = input.getTiltSetpoint();
			}
			tiltPID.setSetpoint(tiltSetpoint);

			tiltSpeed = tiltPID.calculate(tiltAngle);
		} else {
			tiltSpeed = input.getTiltMotorSpeed();
		}

		if (input.isFlywheelActive()) {
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
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelSpeed);
		SmartDashboard.putNumber("TiltSpeed", tiltSpeed);

		SmartDashboard.putNumber("TiltAngle", tiltAngle);
		SmartDashboard.putNumber("FlywheelVelocity", flywheelVelocity);

		SmartDashboard.putNumber("TiltSetpoint", tiltSetpoint);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}