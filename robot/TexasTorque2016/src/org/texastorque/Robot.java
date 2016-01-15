package org.texastorque;

import java.util.ArrayList;

import org.texastorque.feedback.Feedback;
import org.texastorque.input.HumanInput;
import org.texastorque.subsystem.Drivebase;
import org.texastorque.subsystem.Subsystem;
import org.texastorque.torquelib.base.TorqueIterative;
import org.texastorque.torquelib.util.Parameters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {
	
	private int numCycles;
	private ArrayList<Subsystem> subsystems;
	private HumanInput input;
	private Feedback feedback;
	
	public void robotInit() {
		Parameters.load();
		numCycles = 0;
		
		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		
		input = HumanInput.getInstance();
		feedback = Feedback.getInstance();
	}
	
	//teleop
	public void teleopInit() {
		subsystems.forEach((subsystem) -> subsystem.init());
	}
	
	public void teleopContinuous() {
		subsystems.forEach((subsystem) -> subsystem.run());
	}
	
	public void teleopPeriodic() {
		updateDashboard();
	}
	
	//auto
	public void autonomousInit() {
	}
	
	public void autonomousContinuous() {
		input.update();
		subsystems.forEach((subsystem) -> subsystem.run());
	}
	
	public void autonomousPeriodic() {
		updateDashboard();
	}
	
	//disabled
	public void disabledInit() {
		numCycles = 0;
	}
	
	public void disabledContinuous() {
		updateDashboard();
	}
	
	//private
	private void updateDashboard() {
		SmartDashboard.putNumber("numCycles", numCycles++);
	}
}
