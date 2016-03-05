package org.texastorque.feedback;

import static java.lang.Math.atan;
import static java.lang.Math.toDegrees;

import org.texastorque.constants.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

public class VisionFeedback {

	private static VisionFeedback instance;
	private static double CAM_WIDTH;
	private static double CAM_HEIGHT;
	private static double CAM_FOV;

	private static double G;
	private static double H;// height of the twoer
	private static double V;// velocity of the shooter
	private static double Vsq;

	private ITable visionTable;
	private double goalCenterX;
	private double goalCenterY;
	private double turn;
	private double tilt;

	private int visionState;

	public VisionFeedback() {
		visionTable = NetworkTable.getTable("GRIP").getSubTable("visionReport");
		visionState = 0;
	}

	public void calc() {
		// calc turn
		try {
			goalCenterX = visionTable.getNumberArray("centerX", new double[] { -1.0 })[0];
			goalCenterY = visionTable.getNumberArray("centerY", new double[] { -1.0 })[0];
		} catch (Exception e) {
			goalCenterX = visionTable.getNumber("centerX", -1.0);
			goalCenterY = visionTable.getNumber("centerY", -1.0);
		}

		if (goalCenterX == -1.0 || goalCenterY == -1.0) {
			turn = 0.0;
			visionState = 3;// failed - could not find goal
		} else {
			visionState = 1;// loading - waiting for setpoints to be met
		}

		turn = ((goalCenterX / CAM_WIDTH) - 1) * CAM_FOV;

		SmartDashboard.putNumber("ReqTurn", turn);

		// calc tilt
		double distance = 0.0;// get from the Lidar sensor
		double _tilt0 = 0.0;
		double _tilt1 = 0.0;
		try {
			_tilt0 = toDegrees(atan((Vsq + Math.sqrt(Math.pow(Vsq, 2) - G * (G * distance * distance + 2 * H * Vsq)))
					/ (G * distance)));
		} catch (Exception e) {
			_tilt0 = 0.0;
		}
		try {
			_tilt1 = toDegrees(atan((Vsq - Math.sqrt(Math.pow(Vsq, 2) - G * (G * distance * distance + 2 * H * Vsq)))
					/ (G * distance)));
		} catch (Exception e) {
			_tilt1 = 0.0;
		}
		if (_tilt0 == 0.0 && _tilt1 == 0.0) {
			visionState = 3;// failed - could not fit a shooting angle
			tilt = 0.0;
		} else if (_tilt0 == 0.0) {
			tilt = _tilt1;
		} else if (_tilt1 == 0.0) {
			tilt = _tilt0;
		}

		if (tilt > 35 || tilt < -3) {
			visionState = 3;
			tilt = 0.0;
		}

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

	public int getVisionState() {
		return visionState;
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}

	public static void setState(int state) {
		getInstance().visionState = state;
	}

	public static void init() {
		G = Constants.V_G.getDouble();
		H = Constants.V_H.getDouble();
		V = Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble();

		CAM_WIDTH = Constants.V_CAMERA_WIDTH.getDouble() / 2.0;
		CAM_HEIGHT = Constants.V_CAMERA_HEIGHT.getDouble();
		CAM_FOV = Constants.V_CAMERA_FOV.getDouble() / 2.0;
		Vsq = V * V;
	}
}
