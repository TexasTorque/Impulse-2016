package org.texastorque.feedback;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class VisionFeedback {

	private static VisionFeedback instance;

	private double turn;
	private boolean hasTarget;
	
	private DigitalInput pixyExists;
	private AnalogInput pixyX;
	static final int kOversampleBits = 10;
	static final int kAverageBits = 0;
	static final double kSamplesPerSecond = 50.0;
	static final double kCalibrationSampleTime = 5.0;

	private Thread pixyThread;
	
	public VisionFeedback() {
		pixyExists = new DigitalInput(Ports.PIXY_PIN_3_DI);
		pixyX = new AnalogInput(Ports.PIXY_PIN_1_AI);
		
		pixyThread = new Thread(() -> {
			while(true) {
				run();
			}
		});
		pixyThread.start();
	}

	public void run() {
		if(pixyExists.get()) {
			hasTarget = true;
			turn = pixyX.getVoltage();
		} else {
			hasTarget = false;
			turn = 0;
		}
	}

	public double getTurn() {
		return turn;
	}
	
	public boolean hasTarget() {
		return hasTarget;
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}
