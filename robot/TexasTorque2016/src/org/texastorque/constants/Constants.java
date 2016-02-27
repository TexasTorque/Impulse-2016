package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

	// auto
	public static final Constant A_DRIVE_FORWARD_AUTO_DISTANCE = new Constant("A_DRIVE_FORWARD_DISTANCE", 0.0);
	public static final Constant A_TURN_AUTO_ANGLE = new Constant("A_TURN_ANGLE", 0.0);
	public static final Constant A_TILT_AUTO_ANGLE = new Constant("A_TILT_AUTO_ANGLE", 0.0);

	// vision
	public static final Constant V_CAMERA_FOV = new Constant("V_CAMERA_FOV", 67.0);
	public static final Constant V_CAMERA_WIDTH = new Constant("V_CAMERA_WIDTH", 800.0);
	public static final Constant V_CAMERA_HEIGHT = new Constant("V_CAMERA_HEIGHT", 600.0);

	public static final Constant V_G = new Constant("V_G", 9.81);
	public static final Constant V_H = new Constant("V_H", 2.44);

	// drivebase
	public static final Constant D_MAX_VELOCITY = new Constant("D_MAX_VELOCITY", 0.0);
	public static final Constant D_MAX_ACCELERATION = new Constant("D_MAX_ACCELERATION", 0.0);
	public static final Constant D_MAX_ANGULAR_VELOCITY = new Constant("D_MAX_ANGULAR_VELOCITY", 0.0);
	public static final Constant D_MAX_ANGULAR_ACCELERATION = new Constant("D_MAX_ANGULAR_ACCELERATION", 0.0);

	public static final Constant D_RIGHT_PV_P = new Constant("D_RIGHT_PV_P", 0.0);
	public static final Constant D_RIGHT_PV_V = new Constant("D_RIGHT_PV_V", 0.0);
	public static final Constant D_RIGHT_PV_ffV = new Constant("D_RIGHT_PV_ffV", 0.0);
	public static final Constant D_RIGHT_PV_ffA = new Constant("D_RIGHT_PV_ffA", 0.0);

	public static final Constant D_LEFT_PV_P = new Constant("D_LEFT_PV_P", 0.0);
	public static final Constant D_LEFT_PV_V = new Constant("D_LEFT_PV_V", 0.0);
	public static final Constant D_LEFT_PV_ffV = new Constant("D_LEFT_PV_ffV", 0.0);
	public static final Constant D_LEFT_PV_ffA = new Constant("D_LEFT_PV_ffA", 0.0);

	public static final Constant D_TURN_PV_P = new Constant("D_TURN_PV_P", 0.0);
	public static final Constant D_TURN_PV_V = new Constant("D_TURN_PV_V", 0.0);
	public static final Constant D_TURN_PV_ffV = new Constant("D_TURN_PV_ffV", 0.0);
	public static final Constant D_TURN_PV_ffA = new Constant("D_TURN_PV_ffA", 0.0);
	
	public static final Constant D_VISION_P = new Constant("D_VISION_P", 0.0);
	public static final Constant D_VISION_I = new Constant("D_VISION_I", 0.0);
	public static final Constant D_VISION_D = new Constant("D_VISION_D", 0.0);

	public static final Constant TUNED_VOLTAGE = new Constant("TUNED_VOLTAGE", 12.7);

	// shooter
	public static final Constant S_TILT_P = new Constant("S_TILT_P", 0.0);
	public static final Constant S_TILT_I = new Constant("S_TILT_I", 0.0);
	public static final Constant S_TILT_D = new Constant("S_TILT_D", 0.0);

	public static final Constant S_TILT_MIN_VOLTAGE = new Constant("S_TILT_MIN_VOLTAGE", 0.0);
	public static final Constant S_TILT_MAX_VOLTAGE = new Constant("S_TILT_MAX_VOLTAGE", 0.0);
	public static final Constant S_TILT_MIN_ANGLE = new Constant("S_TILT_MIN_ANGLE", 0.0);
	public static final Constant S_TILT_MAX_ANGLE = new Constant("S_TILT_MAX_ANGLE", 0.0);

	public static final Constant S_FLYWHEEL_SETPOINT_VELOCITY = new Constant("S_FLYWHEEL_SETPOINT_VELOCITY", 0.0);
	public static final Constant S_LAYUP_ANGLE_SETPOINT = new Constant("S_LAYUP_ANGLE_SETPOINT", 14);
	public static final Constant S_LONG_SHOT_ANGLE_SETPOINT = new Constant("S_LONG_SHOT_ANGLE_SETPOINT", 39);
}
