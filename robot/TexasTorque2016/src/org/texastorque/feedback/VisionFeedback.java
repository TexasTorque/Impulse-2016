package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

import static java.lang.Math.*;

public class VisionFeedback {

	private static VisionFeedback instance;
	private static double WIDTH = Constants.V_CAMERA_WIDTH.getDouble() / 2.0;
	private static double HEIGHT = Constants.V_CAMERA_HEIGHT.getDouble() / 2.0;
	private static double FOV = Constants.V_CAMERA_FOV.getDouble() / 2.0;

	private static double G = Constants.V_G.getDouble();
	private static double H = Constants.V_H.getDouble();
	private static double V = Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble();
	private static double Vsq = V * V;

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
		double[] _goalCenterX = visionTable.getNumberArray("centerX", new double[] { -1.0 });
		double[] _goalCenterY = visionTable.getNumberArray("centerY", new double[] { -1.0 });
		if (_goalCenterX[0] == -1.0 || _goalCenterY[0] == -1.0) {
			goalCenterX = visionTable.getNumber("centerX", -1.0);
			goalCenterY = visionTable.getNumber("centerY", -1.0);
		} else {
			goalCenterX = TorqueMathUtil.arrayClosest(_goalCenterX, WIDTH);
			goalCenterY = TorqueMathUtil.arrayClosest(_goalCenterY, HEIGHT);
		}

		if (goalCenterX == -1.0) {
			visionState = 3;// failed - could not find goal
		} else {
			visionState = 1;// loading - waiting for setpoints to be met
		}

		turn = ((goalCenterX / WIDTH) - 1) * FOV;

		// calc tilt
		double distance = HEIGHT / tan(FOV * goalCenterY / HEIGHT);
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
}
