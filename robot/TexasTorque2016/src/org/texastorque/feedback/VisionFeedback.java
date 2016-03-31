package org.texastorque.feedback;

import org.texastorque.constants.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

import static java.lang.Math.*;

public class VisionFeedback {

	private static VisionFeedback instance;

	private static double CAM_WIDTH;
	private static double CAM_HEIGHT;
	private static double CAM_FOV;
	private static double H_DIFF;
	private static double X_CAM_ROBOT;
	private static double THETA_PLUS;
	private static double Vsq;
	private static double G;

	private static double k1;

	private ITable visionTable;
	private double goalCenterX;
	private double goalCenterY;
	private double turn;
	private double tilt;

	private double distance;

	private int visionState;

	public VisionFeedback() {
		visionTable = NetworkTable.getTable("GRIP").getSubTable("visionReport");
		visionState = 0;
	}

	private double calcTilt() {
		double _tilt1;
		boolean t1valid;
		double _tilt2;
		boolean t2valid;

		try {
			_tilt1 = atan((Vsq - sqrt(Vsq * Vsq - G * (G * distance * distance + k1))) / (G * distance));
			_tilt1 = 90 - toDegrees(_tilt1);
		} catch (Exception e) {
			_tilt1 = 0.0;
		}

		try {
			_tilt2 = atan((Vsq + sqrt(Vsq * Vsq - G * (G * distance * distance + k1))) / (G * distance));
			_tilt2 = 90 - toDegrees(_tilt2);
		} catch (Exception e) {
			_tilt2 = 0.0;
		}

		SmartDashboard.putNumber("Tilt1", _tilt1);
		SmartDashboard.putNumber("Tilt2", _tilt2);

		t1valid = _tilt1 != 0.0 && _tilt1 > -7 && _tilt1 < 37;
		t2valid = _tilt2 != 0.0 && _tilt2 > -7 && _tilt2 < 37;

		if (t1valid && t2valid) {
			if (_tilt1 > _tilt2) {
				return _tilt1;
			}
			return _tilt2;
		} else if (t1valid) {
			return _tilt1;
		} else if (t2valid) {
			return _tilt2;
		}

		// neither tilt is possible
		visionState = 3;
		return -6.0;
	}

	public void calc() {
		// grab values
		try {
			goalCenterX = visionTable.getNumberArray("centerX", new double[] { -1.0 })[0];
			goalCenterY = visionTable.getNumberArray("centerY", new double[] { -1.0 })[0];
		} catch (Exception e) {
			goalCenterX = visionTable.getNumber("centerX", -1.0);
			goalCenterY = visionTable.getNumber("centerY", -1.0);
		}

		if (goalCenterX == -1.0 || goalCenterY == -1.0) {
			turn = 0.0;
			tilt = -6.0;
			visionState = 3;// failed - could not find goal
			return;
		} else {
			visionState = 1;// loading - waiting for setpoints to be met
		}

		// calculated turn angle for real turn
		turn = ((2 * goalCenterX / CAM_WIDTH) - 1) * CAM_FOV;

		// linear tilt angle for distance
		tilt = (1 - (goalCenterY / CAM_HEIGHT)) * CAM_FOV;
		tilt += THETA_PLUS;

		distance = H_DIFF / tan(toRadians(tilt));

		// real tilt
		tilt = calcTilt();

		// real turn
		turn = toDegrees(atan(tan(toRadians(turn)) - (X_CAM_ROBOT / (distance * cos(toRadians(turn))))));

		if (visionState != 3 && Feedback.getInstance().visionShotReady()) {
			visionState = 2;// success
		}
	}

	public double getTurn() {
		return turn;
	}

	public double getTilt() {
		return tilt;
	}

	public double getDistance() {
		return distance;
	}

	public int getVisionState() {
		return visionState;
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}

	public static void init() {
		CAM_WIDTH = Constants.V_CAMERA_WIDTH.getDouble();
		CAM_HEIGHT = Constants.V_CAMERA_HEIGHT.getDouble();
		CAM_FOV = Constants.V_CAMERA_FOV.getDouble();

		H_DIFF = Constants.V_TOWER_HEIGHT.getDouble() - Constants.V_ROBOT_HEIGHT.getDouble();
		X_CAM_ROBOT = Constants.V_ROBOT_CAMERA_DISTANCE.getDouble();
		THETA_PLUS = Constants.V_CAMERA_MOUNT_ANGLE.getDouble() - CAM_FOV / 2.0;

		G = Constants.V_G.getDouble();
		// Vsq = 0.24765 * 0.10472 *
		// Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble();
		Vsq = 10;
		Vsq *= Vsq;

		k1 = 2 * H_DIFF * Vsq;
	}
}
