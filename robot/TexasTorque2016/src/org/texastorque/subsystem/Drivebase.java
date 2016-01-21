package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem {

	private double MAX_SPEED;
	
	private static Drivebase instance;

	private double leftSpeed;
	private double rightSpeed;
	
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
		leftSpeed = input.getLeftDriveSpeed();
		rightSpeed = input.getRightDriveSpeed();
		
		output();
	}

	protected void output() {
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
