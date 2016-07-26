package org.texastorque.feedback;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class VisionFeedback {

	private static VisionFeedback instance;

	private double turn;
	
	private DigitalInput pixyExists;
	private AnalogInput pixyX;

	public VisionFeedback() {
		pixyExists = new DigitalInput(Ports.PIXY_PIN_3_DI);
		pixyX = new AnalogInput(Ports.PIXY_PIN_1_AI);
	}

	public void run() {
		if(pixyExists.get()) {
			turn = pixyX.getVoltage();
		} else {
			turn = 0;
		}
	}

	public double getTurn() {
		return turn;
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}
