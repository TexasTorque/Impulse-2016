package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

	// auto
	public static final Constant A_DRIVE_FORWARD_AUTO_DISTANCE = new Constant("A_DRIVE_FORWARD_DISTANCE", 0.0);
	public static final Constant A_TURN_AUTO_ANGLE = new Constant("A_TURN_ANGLE", 0.0);
	public static final Constant A_TILT_AUTO_ANGLE = new Constant("A_TILT_AUTO_ANGLE", 0.0);

	public static final Constant A_LBS_DRIVE_DISTANCE = new Constant("A_LBS_DRIVE_DISTANCE", 0.0);

	// vision
	public static final Constant V_CAMERA_FOV = new Constant("V_CAMERA_FOV", 67.0);
	public static final Constant V_CAMERA_WIDTH = new Constant("V_CAMERA_WIDTH", 640.0);
	public static final Constant V_CAMERA_HEIGHT = new Constant("V_CAMERA_HEIGHT", 480.0);
	public static final Constant V_CAMERA_MOUNT_ANGLE = new Constant("V_CAMERA_MOUNT_ANGLE", 50.0);
	public static final Constant V_ROBOT_CAMERA_DISTANCE = new Constant("V_ROBOT_CAMERA_DISTANCE", 0.0);
	public static final Constant V_TOWER_HEIGHT = new Constant("V_TOWER_HEIGHT", 0.0);
	public static final Constant V_ROBOT_HEIGHT = new Constant("V_ROBOT_HEIGHT", 0.0);
	public static final Constant V_G = new Constant("V_G", 0.0);

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

	public static final Constant TUNED_VOLTAGE = new Constant("TUNED_VOLTAGE", 12.5);

	// shooter
	public static final Constant S_TILT_P = new Constant("S_TILT_P", 0.0);
	public static final Constant S_TILT_I = new Constant("S_TILT_I", 0.0);
	public static final Constant S_TILT_D = new Constant("S_TILT_D", 0.0);

	public static final Constant S_TILT_SET_ANGLE = new Constant("S_TILT_SET_ANGLE", 0.0);
	
	public static final Constant S_VISION_FLYWHEEL = new Constant("S_VISION_FLYWHEEL", 0.0);
	public static final Constant S_BATTER_FLYWHEEL = new Constant("S_BATTER_FLYWHEEL", 0.0);
	public static final Constant S_LAYUP_FLYWHEEL = new Constant("S_LAYUP_FLYWHEEL", 0.0);
	public static final Constant S_LONG_FLYWHEEL = new Constant("S_LONG_FLYWHEEL", 0.0);

	public static final Constant S_LONG_SHOT_SETPOINT = new Constant("S_LONG_SHOT_SETPOINT", 32);
	public static final Constant S_LAYUP_ANGLE_SETPOINT = new Constant("S_LAYUP_ANGLE_SETPOINT", 23);
	public static final Constant S_BATTER_SHOT_SETPOINT = new Constant("S_BATTER_SHOT_SETPOINT", 14);
	public static final Constant S_DOWN_SETPOINT = new Constant("S_DOWN_SETPOINT", -11.0);// -7

	// a mechanism
	public static final Constant ARM_P = new Constant("ARM_P", 0.0);
	public static final Constant ARM_I = new Constant("ARM_I", 0.0);
	public static final Constant ARM_D = new Constant("ARM_D", 0.0);

	public static final Constant ARM_UP_SETPOINT = new Constant("ARM_UP_SETPOINT", 0.0);
	public static final Constant ARM_DOWN_SETPOINT = new Constant("ARM_DOWN_SETPOINT", 0.0);
}
