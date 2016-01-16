package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

	// auto
	public static final Constant DRIVE_FORWARD_AUTO_DISTANCE = new Constant("A_DriveForwardDistance", 0.0);
	
	//vision
	public static final Constant CAMERA_MAX_PITCH_FOV_ANGLE = new Constant("V_CamMaxYFOVAngle", 0.0);
	public static final Constant CAMERA_MIN_PITCH_FOV_ANGLE = new Constant("V_CamMinYFOVAngle", 0.0);
	public static final Constant CAMERA_MAX_YAW_FOV_ANGLE = new Constant("V_CamMaxXFOVAngle", 0.0);
	public static final Constant CAMERA_MIN_YAW_FOV_ANGLE = new Constant("V_CamMinXFOVAngle", 0.0);
}
