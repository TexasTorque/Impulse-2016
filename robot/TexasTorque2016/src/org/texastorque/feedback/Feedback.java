package org.texastorque.feedback;

public class Feedback {
	
	private static Feedback instance;
	
	//sensors
	
	//values
	
	private void update0() {
	}
	
	//singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
	
	public static void update() {
		getInstance().update0();
	}
}
