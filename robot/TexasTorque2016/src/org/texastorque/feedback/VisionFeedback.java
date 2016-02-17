package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class VisionFeedback {

	private static VisionFeedback instance;
	private static double WIDTH = Constants.V_CAMERA_WIDTH.getDouble() / 2.0;
	private static double HEIGHT = Constants.V_CAMERA_HEIGHT.getDouble() / 2.0;
	private static double FOV = Constants.V_CAMERA_FOV.getDouble() / 2.0;

	private static double G = Constants.V_G.getDouble();
	private static double H = Constants.V_H.getDouble();
	private static double V = Constants.S_FLYWHEEL_SETPOINT_VELOCITY.getDouble();

	private ITable visionTable;
	private double goalCenterX;
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
		if (_goalCenterX[0] == -1.0) {
			goalCenterX = visionTable.getNumber("centerX", -1.0);
		} else {
			goalCenterX = TorqueMathUtil.arrayClosest(_goalCenterX, WIDTH);
		}

		if (goalCenterX == -1.0) {
			visionState = 3;
		} else {
			visionState = 1;
		}

		turn = ((goalCenterX / WIDTH) - 1) * FOV;

		// calc tilt
		double _tilt0 = 0.0;
		double _tilt1 = 0.0;
		try {
			_tilt0 = Math.toDegrees(
					Math.atan((V * V + Math.sqrt(Math.pow(V, 4) - G * (G * 0.0 * 0.0 + 2 * H * V * V))) / (G * 0.0)));

		} catch (Exception e) {
			_tilt0 = 0.0;
		}
		try {
			_tilt1 = Math.toDegrees(
					Math.atan((V * V - Math.sqrt(Math.pow(V, 4) - G * (G * 0.0 * 0.0 + 2 * H * V * V))) / (G * 0.0)));
		} catch (Exception e) {
			_tilt1 = 0.0;
		}
		if (_tilt0 == 0.0 && _tilt1 == 0.0) {
			visionState = 3;
			tilt = 0.0;
		} else if (_tilt0 == 0.0) {
			tilt = _tilt1;
		} else if (_tilt1 == 0.0) {
			tilt = _tilt0;
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
