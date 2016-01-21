package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem {

	private double MAX_SPEED;
	
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
	
	
	// profiling variables
	
	private double prevTime;
	
	private TorqueTMP profile;
	private TorquePV leftPV;
	private TorquePV rightPV;
	
	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;
	
	private double setpoint;
	private double previousSetpoint;
	
		
	public void init() {
		MAX_SPEED = Constants.D_MAX_SPEED.getDouble();
		
		profile = new TorqueTMP(Constants.D_MAX_VELOCITY.getDouble(),
				 				Constants.D_MAX_ACCELERATION.getDouble());
		leftPV = new TorquePV();
		rightPV = new TorquePV();
		
		feedback.resetDriveEncoders();
		setpoint = 0.0;
		
		leftPV.setGains(Constants.D_LEFT_PV_P.getDouble(), 
						Constants.D_LEFT_PV_V.getDouble(),
						Constants.D_LEFT_PV_ffP.getDouble(),
						Constants.D_LEFT_PV_ffV.getDouble());
		leftPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		
		rightPV.setGains(Constants.D_RIGHT_PV_P.getDouble(), 
						 Constants.D_RIGHT_PV_V.getDouble(),
						 Constants.D_RIGHT_PV_ffP.getDouble(), 
						 Constants.D_RIGHT_PV_ffV.getDouble());
		rightPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		
		prevTime = Timer.getFPGATimestamp();
	}

	public void run() {
		
		leftPosition = feedback.getLeftDrivePosition();
		rightPosition = feedback.getRightDrivePosition();
		
		leftVelocity = feedback.getLeftDriveVelocity();
		rightVelocity = feedback.getRightDriveVelocity();
		
		leftAcceleration = feedback.getLeftDriveAcceleration();
		rightAcceleration = feedback.getRightDriveAcceleration();
		
		if(driverStation.isAutonomous()){
			if(input.isOverride()){
				leftSpeed = input.getLeftDriveSpeed();
				rightSpeed = input.getRightDriveSpeed();
			} else {
				setpoint = input.getDrivebaseSetpoint();
				if (setpoint != previousSetpoint){
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
			}
		} else {
		
			leftSpeed = input.getLeftDriveSpeed();
			rightSpeed = input.getRightDriveSpeed();
		
		}
		
		output();
	}

	protected void output() {
		
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, MAX_SPEED);
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, MAX_SPEED);
		
		output.setDriveSpeeds(leftSpeed, rightSpeed);
	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("DrivebaseLeftSpeed", leftSpeed);
		SmartDashboard.putNumber("DrivebaseRightSpeed", rightSpeed);
	}
	
	

	// singleton
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}
