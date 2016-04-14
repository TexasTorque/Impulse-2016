package org.texastorque.torquelib.controlLoop;

import edu.wpi.first.wpilibj.Timer;

/**
 * A controller that is either on or off depending on if the setpoint is
 * reached.
 *
 * @author TexasTorque
 */
public class BangBang extends ControlLoop {

	private final boolean limited;
	private final double resetTime;
	private final double firstOutput;

	private boolean reset;
	private double lastTime;

	/**
	 * Create a new BangBang controller.
	 */
	public BangBang() {
		limited = false;
		resetTime = 0.0;
		firstOutput = 0.0;
	}

	/**
	 * Create a new BangBang controller with a limited output for a specific
	 * time interval.
	 * 
	 * @param time
	 *            The time the controller should wait until full output is sent.
	 * @param firstOut
	 *            The output sent until the time interval has ended.
	 */
	public BangBang(double time, double firstOut) {
		limited = true;
		resetTime = time;
		firstOutput = firstOut;
	}

	/**
	 * Reset the time interval.
	 */
	public void reset() {
		reset = true;
	}

	/**
	 * Calculate output based off of the current sensor value.
	 *
	 * @param current
	 *            the current sensor feedback.
	 * @return Motor ouput to the system.
	 */
	public double calculate(double current) {
		if (reset) {
			reset = false;
			lastTime = Timer.getFPGATimestamp();
		}

		if (current < setPoint) {
			if (!limited || lastTime - Timer.getFPGATimestamp() > resetTime) {
				return 1.0;
			}
			return firstOutput;
		} else {
			return 0.0;
		}
	}
}
