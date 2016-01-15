package org.texastorque.subsystem;

public class Drivebase extends Subsystem {
	
	private static Drivebase instance;
	
	public void init() {
	}
	
	public void run() {
	}
	
	//singleton
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}
