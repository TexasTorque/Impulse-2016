package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.input.HumanInput;
import org.texastorque.input.Input;
import org.texastorque.subsystem.Drivebase;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Shooter;
import org.texastorque.subsystem.Subsystem;
import org.texastorque.torquelib.base.TorqueIterative;
import org.texastorque.torquelib.util.Parameters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private int numCycles;
	private ArrayList<Subsystem> subsystems;

	private AutoManager autoManager;
	private Input input;
	private Feedback feedback;

	@Override
	public void robotInit() {
		Parameters.load();
		numCycles = 0;

		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Intake.getInstance());
		subsystems.add(Shooter.getInstance());

		autoManager = AutoManager.getInstance();
		feedback = Feedback.getInstance();
	}

	// auto
	@Override
	public void autonomousInit() {
		Parameters.load();
		numCycles = 0;
		autoManager.init();
		subsystems.forEach((subsystem) -> subsystem.init());

		input = autoManager.createAutoMode();
		subsystems.forEach((subsystem) -> subsystem.setInput(input));
		autoManager.runAutoMode();
	}

	@Override
	public void autonomousContinuous() {
		input.update();
		feedback.update();
		subsystems.forEach((subsystem) -> subsystem.run());
	}

	@Override
	public void autonomousPeriodic() {
		updateDashboard();
	}

	// teleop
	@Override
	public void teleopInit() {
		Parameters.load();
		numCycles = 0;
		subsystems.forEach((subsystem) -> subsystem.init());

		input = HumanInput.getInstance();
		subsystems.forEach((subsystem) -> subsystem.setInput(input));
	}

	@Override
	public void teleopContinuous() {
		input.update();
		feedback.update();
		subsystems.forEach((subsystem) -> subsystem.run());
	}

	@Override
	public void teleopPeriodic() {
		updateDashboard();
	}

	// disabled
	@Override
	public void disabledInit() {
		numCycles = 0;
	}

	@Override
	public void disabledContinuous() {
		feedback.update();
	}

	@Override
	public void disabledPeriodic() {
		updateDashboard();
	}

	// private
	private void updateDashboard() {
		subsystems.forEach((subsystem) -> subsystem.pushToDashboard());
		SmartDashboard.putNumber("NumCycles", numCycles++);
		SmartDashboard.putNumber("ThreadCount", Thread.activeCount());
		SmartDashboard.putNumber("VISION_STATE", feedback.getVisionState());
	}
}
