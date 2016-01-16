package org.texastorque.feedback;

import org.texastorque.feedback.vision.VisionManager;

public class Feedback {

	private static Feedback instance;

	private VisionManager visionManager;

	public Feedback() {
		visionManager = VisionManager.getInstance();
	}

	// sensors

	// values

	public void update() {
		visionManager.update();
	}

	public void pushToDashboard() {
		visionManager.pushToDashboard();
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
