package org.texastorque.feedback;

public class Feedback {

	private static Feedback instance;

	// sensors

	// values

	public Feedback() {
	}

	public void update() {
	}

	public void pushToDashboard() {
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
