package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem {

	private static Drivebase instance;

	private double leftSpeed;
	private double rightSpeed;

	// sensor
	private double leftPosition;
	private double rightPosition;

	private double leftVelocity;
	private double rightVelocity;

	private double leftAcceleration;
	private double rightAcceleration;

	private double angle;
	private double angularVelocity;

	// generic profile variables
	private double prevTime;

	// linear profile
	private TorqueTMP profile;
	private TorquePV leftPV;
	private TorquePV rightPV;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;

	private double setpoint;
	private double previousSetpoint;

	// angular profile
	private TorqueTMP angularProfile;
	private TorquePV angularPV;

	private double targetAngle;
	private double targetAngularVelocity;

	private double turnSetpoint;
	private double turnPreviousSetpoint;
	private double yawSetpoint;

	@Override
	public void init() {
		// linear
		profile = new TorqueTMP(Constants.D_MAX_VELOCITY.getDouble(), Constants.D_MAX_ACCELERATION.getDouble());
		leftPV = new TorquePV();
		rightPV = new TorquePV();

		feedback.resetDriveEncoders();
		setpoint = 0.0;
		previousSetpoint = 0.0;

		leftPV.setGains(Constants.D_LEFT_PV_P.getDouble(), Constants.D_LEFT_PV_V.getDouble(),
				Constants.D_LEFT_PV_ffP.getDouble(), Constants.D_LEFT_PV_ffV.getDouble());
		leftPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		rightPV.setGains(Constants.D_RIGHT_PV_P.getDouble(), Constants.D_RIGHT_PV_V.getDouble(),
				Constants.D_RIGHT_PV_ffP.getDouble(), Constants.D_RIGHT_PV_ffV.getDouble());
		rightPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		// angular
		angularProfile = new TorqueTMP(0.0, 0.0);
		angularPV = new TorquePV();

		prevTime = Timer.getFPGATimestamp();
		turnSetpoint = 0.0;
		turnPreviousSetpoint = 0.0;

		angularPV.setGains(0.0, 0.0, 0.0, 0.0);
		angularPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
	}

	@Override
	public void run() {
		leftPosition = feedback.getLeftDrivePosition();
		rightPosition = feedback.getRightDrivePosition();

		leftVelocity = feedback.getLeftDriveVelocity();
		rightVelocity = feedback.getRightDriveVelocity();

		leftAcceleration = feedback.getLeftDriveAcceleration();
		rightAcceleration = feedback.getRightDriveAcceleration();

		angle = feedback.getAngle();
		angularVelocity = feedback.getAngularVelocity();

		if (input.isOverride() || !driverStation.isAutonomous()) {// normal driving
			leftSpeed = input.getLeftDriveSpeed();
			rightSpeed = input.getRightDriveSpeed();
		} else if (input.getDriveSetpoint() != 0.0) {// linear driving
			setpoint = input.getDriveSetpoint();
			if (setpoint != previousSetpoint) {
				previousSetpoint = setpoint;
				feedback.resetDriveEncoders();
				profile.generateTrapezoid(setpoint, 0.0, 0.0);
			}

			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			profile.calculateNextSituation(dt);

			targetPosition = profile.getCurrentPosition();
			targetVelocity = profile.getCurrentVelocity();
			targetAcceleration = profile.getCurrentAcceleration();

			leftSpeed = leftPV.calculate(profile, leftPosition, leftVelocity);
			rightSpeed = rightPV.calculate(profile, rightPosition, rightVelocity);
		} else if (input.getTurnSetpoint() != 0.0) {
			if (input.isVisionLock()) {
				turnSetpoint = feedback.getRequiredTurn();
			} else {
				turnSetpoint = input.getTurnSetpoint();
			}
			if (turnSetpoint != input.getTurnSetpoint()) {
				turnPreviousSetpoint = turnSetpoint;
				feedback.resetGyro();
				angularProfile.generateTrapezoid(turnSetpoint, 0.0, 0.0);
			}

			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			angularProfile.calculateNextSituation(dt);

			targetAngle = profile.getCurrentPosition();
			targetAngularVelocity = profile.getCurrentVelocity();

			leftSpeed = angularPV.calculate(angularProfile, angle, angularVelocity);
			leftSpeed = -rightSpeed;
		}

		output();
	}

	@Override
	protected void output() {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, 1.0);
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, 1.0);

		output.setDriveSpeeds(leftSpeed, rightSpeed);
	}

	@Override
	public void pushToDashboard() {
		// targets
		SmartDashboard.putNumber("DrivebaseTargetPosition", targetPosition);
		SmartDashboard.putNumber("DrivebaseTargetVelocity", targetVelocity);
		SmartDashboard.putNumber("DrivebaseTargetAcceleration", targetAcceleration);

		SmartDashboard.putNumber("DrivebaseTargetAngle", targetAngle);
		SmartDashboard.putNumber("DrivebaseTargetAngularVelocity", targetAngularVelocity);

		// values
		SmartDashboard.putNumber("DrivebaseLeftPosition", leftPosition);
		SmartDashboard.putNumber("DrivebaseRightPosition", rightPosition);

		SmartDashboard.putNumber("DrivebsaeLeftVelocity", leftVelocity);
		SmartDashboard.putNumber("DrivebaseRightVelocity", rightVelocity);

		SmartDashboard.putNumber("DrivebaseLeftAcceleration", leftAcceleration);
		SmartDashboard.putNumber("DrivebaseRightAcceleration", rightAcceleration);

		SmartDashboard.putNumber("DrivebaseAngle", angle);
		SmartDashboard.putNumber("DrivebaseAngularVelocity", angularVelocity);

		// setpoints
		SmartDashboard.putNumber("YawSetpoint", yawSetpoint);
		SmartDashboard.putNumber("DriveSetpoint", setpoint);
		SmartDashboard.putNumber("TurnSetpoint", turnPreviousSetpoint);

		// output
		SmartDashboard.putNumber("DrivebaseLeftSpeed", leftSpeed);
		SmartDashboard.putNumber("DrivebaseRightSpeed", rightSpeed);
	}

	// singleton
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}