package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.BangBang;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	private static Shooter instance;

	private double leftTiltSpeed;
	private double rightTiltSpeed;
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

	@Override
	public void init() {
		flywheelControl = new BangBang();
		flywheelControl.setSetpoint(Constants.S_FLYWHEEL_SETPOINT.getDouble());

		tiltPID = new TorquePID(Constants.S_TILT_P.getDouble(), Constants.S_TILT_I.getDouble(),
				Constants.S_TILT_D.getDouble());
		tiltPID.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		prevTime = Timer.getFPGATimestamp();
	}

	@Override
	public void run() {
		tiltAngle = feedback.getTiltAngle();
		flywheelVelocity = feedback.getFlywheelVelocity();

		if (input.isFlywheelActive()) {
			flywheelSpeed = flywheelControl.calculate(flywheelVelocity);
		} else {
			flywheelSpeed = 0.0;
		}
		
		leftTiltSpeed = input.getLeftTiltMotorSpeed();
		rightTiltSpeed = input.getLeftTiltMotorSpeed();
	}

	@Override
	protected void output() {
		output.setTiltSpeeds(leftTiltSpeed, rightTiltSpeed);
		output.setFlywheelSpeed(flywheelSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("FlywheelMotorSpeed", flywheelSpeed);
		SmartDashboard.putNumber("LeftTiltMotorSpeed", leftTiltSpeed);
		SmartDashboard.putNumber("RightTiltMotorSpeed", rightTiltSpeed);

		SmartDashboard.putNumber("TiltAngle", tiltAngle);
		SmartDashboard.putNumber("FlywheelVelocity", flywheelVelocity);
	}

	// singleton
	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}
}