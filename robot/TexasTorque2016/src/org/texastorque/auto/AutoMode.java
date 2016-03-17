package org.texastorque.auto;

import org.texastorque.input.Input;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoMode extends Input {

	private Thread thread;
	
	public AutoMode() {
		SmartDashboard.putString("RunningAutoMode", getClass().getSimpleName());
	}
	
	public final void start() {
		thread = new Thread(() -> run());
		thread.start();
	}

	public final void interrupt() {
		try {
			thread.join(500);// .5 seconds to kill auto
		} catch (Exception e) {
		}
	}

	protected abstract void run();
	
	protected abstract double getLinearMaxSpeed();
	
	@Override
	public void update() {
		// auto modes do not update
	}

	protected final void pause(double seconds) {
		double start = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - start < seconds) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		}
	}
}
