package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

	// auto
	public static final Constant DRIVE_FORWARD_AUTO_DISTANCE = new Constant("A_DriveForwardDistance", 0.0);

	// vision
	public static final Constant V_CAMERA_FOV = new Constant("V_CAMERA_FOV", 67.0);
	public static final Constant V_WIDTH = new Constant("V_CAMERA_W", 800.0);
	public static final Constant V_HEIGHT = new Constant("V_CAMERA_H", 600.0);

	// drivebase
	public static final Constant D_MAX_VELOCITY = new Constant("D_MAX_VELOCITY", 0.0);
	public static final Constant D_MAX_ACCELERATION = new Constant("D_MAX_ACCELERATION", 0.0);

	public static final Constant D_RIGHT_PV_P = new Constant("D_RIGHT_PV_P", 0.0);
	public static final Constant D_RIGHT_PV_V = new Constant("D_RIGHT_PV_V", 0.0);
	public static final Constant D_RIGHT_PV_ffP = new Constant("D_RIGHT_PV_ffP", 0.0);
	public static final Constant D_RIGHT_PV_ffV = new Constant("D_RIGHT_PV_ffV", 0.0);

	public static final Constant D_LEFT_PV_P = new Constant("D_LEFT_PV_P", 0.0);
	public static final Constant D_LEFT_PV_V = new Constant("D_LEFT_PV_V", 0.0);
	public static final Constant D_LEFT_PV_ffP = new Constant("D_LEFT_PV_ffP", 0.0);
	public static final Constant D_LEFT_PV_ffV = new Constant("D_LEFT_PV_ffV", 0.0);

	public static final Constant TUNED_VOLTAGE = new Constant("TUNED_VOLTAGE", 12.7);
}
