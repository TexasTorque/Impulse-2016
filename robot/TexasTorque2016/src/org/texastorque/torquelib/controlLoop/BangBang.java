package org.texastorque.torquelib.controlLoop;

import edu.wpi.first.wpilibj.Timer;

/**
 * A controller that is either on or off depending on if the setpoint is
 * reached.
 *
 * @author TexasTorque
 */
public class BangBang extends ControlLoop {

	private double lastTime;

	/**
	 * Create a new BangBang controller.
	 */
	public BangBang() {
		super();
	}

	/**
	 * Calculate output based off of the current sensor value.
	 *
	 * @param current
	 *            the current sensor feedback.
	 * @return Motor ouput to the system.
	 */
	public double calculate(double current) {
		currentValue = current;
		if (currentValue < setPoint) {
			if (lastTime - Timer.getFPGATimestamp() > .5) {
				return 1.0;
			}
			return 0.7;
		} else {
			lastTime = Timer.getFPGATimestamp();
			return 0.0;
		}
	}
}
