package org.texastorque.feedback;

public class Feedback {
	
	private static Feedback instance;
	
	//sensors
	
	//values
	
	public void update() {
	}
	
	//singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
