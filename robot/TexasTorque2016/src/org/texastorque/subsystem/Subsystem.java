package org.texastorque.subsystem;

import org.texastorque.feedback.Feedback;
import org.texastorque.input.Input;
import org.texastorque.output.RobotOutput;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class Subsystem {

	protected Input input;
	protected Feedback feedback;
	protected RobotOutput output;
	protected DriverStation driverStation;

	public Subsystem() {
		feedback = Feedback.getInstance();
		output = RobotOutput.getInstance();
		driverStation = DriverStation.getInstance();
	}

	public final void setInput(Input _input) {
		input = _input;
	}

	public abstract void init();

	public final void run() {
		_run();
		output();
	}

	public abstract void _run();

	protected abstract void output();

	public abstract void pushToDashboard();
}
