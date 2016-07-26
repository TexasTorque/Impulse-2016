package org.texastorque.constants;

public class Ports {

	private static final boolean CHARLIE = true;

	// motors
	public static final int DRIVE_LEFT_TOP = 6;
	public static final int DRIVE_LEFT_BOTTOM = 7;
	public static final int DRIVE_LEFT_BOOST = 5;

	public static final int DRIVE_RIGHT_TOP = 11;
	public static final int DRIVE_RIGHT_BOTTOM = CHARLIE ? 2 : 1;
	public static final int DRIVE_RIGHT_BOOST = 10;

	public static final int TILT = 8;
	public static final int FLYWHEEL_LEFT = 4;
	public static final int FLYWHEEL_RIGHT = CHARLIE ? 19 : 14;

	public static final int INTAKE = CHARLIE ? 3 : 0;

	public static final int CONVEYOR = 9;

	public static final int ARM_LEFT = CHARLIE ? 1 : 2;
	public static final int ARM_RIGHT = CHARLIE ? 0 : 3;

	// pneumatics
	public static final int BRAKES_SOLENOID_PORT_A = CHARLIE ? 4 : 0;
	public static final int BRAKES_SOLENOID_PORT_B = CHARLIE ? 3 : 5;
	public static final int COMPRESSION_TESTING_A = CHARLIE ? 0 : 3;
	public static final int COMPRESSION_TESTING_B = CHARLIE ? 5 : 4;

	// sensors
	public static final int DRIVE_LEFT_ENCODER_A = 4;
	public static final int DRIVE_LEFT_ENCODER_B = 5;
	public static final int DRIVE_RIGHT_ENCODER_A = 0;
	public static final int DRIVE_RIGHT_ENCODER_B = 1;

	public static final int FLYWHEEL_ENCODER_A = 6;
	public static final int FLYWHEEL_ENCODER_B = 7;

	public static final int TILT_ENCODER_A = CHARLIE ? 12 : 15;
	public static final int TILT_ENCODER_B = CHARLIE ? 13 : 16;

	public static final int ARM_LEFT_ENCODER_A = 8;
	public static final int ARM_LEFT_ENCODER_B = 9;
	public static final int ARM_RIGHT_ENCODER_A = 2;
	public static final int ARM_RIGHT_ENCODER_B = 3;
	
	// relay
	public static final int FLASHLIGHT = 3;

	// misc
	public static final int ARDUINO_ANALOG_PIN = 0;
	public static final int PIXY_PIN_1_AI = 3;
	public static final int PIXY_PIN_3_DI = 22;
}