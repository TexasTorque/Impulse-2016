package org.texastorque.subsystem;

import org.texastorque.feedback.Feedback;
import org.texastorque.input.Input;
import org.texastorque.output.RobotOutput;

public abstract class Subsystem {

	protected Input input;
	protected Feedback feedback;
	protected RobotOutput output;

	public final void setInput(Input _input) {
		input = _input;
	}

	public abstract void init();

	public abstract void run();

	protected abstract void output();

	public abstract void pushToDashboard();
}
