package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.feedback.VisionFeedback;
import org.texastorque.input.HumanInput;
import org.texastorque.input.Input;
import org.texastorque.subsystem.Brakes;
import org.texastorque.subsystem.Conveyor;
import org.texastorque.subsystem.DoubleArm;
import org.texastorque.subsystem.Drivebase;
import org.texastorque.subsystem.Flashlight;
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
		subsystems.add(Conveyor.getInstance());
		subsystems.add(Brakes.getInstance());
		subsystems.add(DoubleArm.getInstance());
		subsystems.add(Flashlight.getInstance());

		autoManager = AutoManager.getInstance();
		feedback = Feedback.getInstance();

		VisionFeedback.init();

		autoManager.reset();

		Feedback.getInstance().resetTiltEncoder();
	}

	@Override
	public void autonomousInit() {
		Parameters.load();
		numCycles = 0;

		VisionFeedback.init();

		input = autoManager.createAutoMode();
		feedback.setInput(input);
		subsystems.forEach((subsystem) -> subsystem.init(input));
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

	@Override
	public void teleopInit() {
		Parameters.load();
		numCycles = 0;

		VisionFeedback.init();

		input = HumanInput.getInstance();
		feedback.setInput(input);
		subsystems.forEach((subsystem) -> subsystem.init(input));
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

	@Override
	public void disabledInit() {
		numCycles = 0;
		autoManager.reset();
	}

	@Override
	public void disabledContinuous() {
		autoManager.updateDashboard();
	}

	private void updateDashboard() {
		subsystems.forEach((subsystem) -> subsystem.pushToDashboard());

		SmartDashboard.putNumber("NumCycles", numCycles++);
		SmartDashboard.putNumber("ThreadCount", Thread.activeCount());
		SmartDashboard.putBoolean("Override", input.isOverride());

		feedback.pushToDashboard();
	}
}
