package org.texastorque.feedback;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class VisionFeedback {

	private static VisionFeedback instance;
	private static double WIDTH = Constants.V_WIDTH.getDouble() / 2.0;
	private static double HEIGHT = Constants.V_HEIGHT.getDouble() / 2.0;
	private static double FOV = Constants.V_CAMERA_FOV.getDouble() / 2.0;

	private ITable visionTable;
	private double goalCenterX;
	private double goalCenterY;
	private double turn;
	private double tilt;

	public VisionFeedback() {
		visionTable = NetworkTable.getTable("GRIP").getSubTable("visionReport");
	}

	public void update() {
		double[] _goalCenterX = visionTable.getNumberArray("centerX", new double[] { -1.0 });
		double[] _goalCenterY = visionTable.getNumberArray("centerY", new double[] { -1.0 });
		if (_goalCenterX[0] == -1.0 || _goalCenterY[0] == -1.0) {
			goalCenterX = visionTable.getNumber("centerX", WIDTH);
			goalCenterY = visionTable.getNumber("centerY", HEIGHT);
		} else {
			goalCenterX = TorqueMathUtil.arrayClosest(_goalCenterX, WIDTH);
			goalCenterY = TorqueMathUtil.arrayClosest(_goalCenterY, HEIGHT);
		}

		turn = ((goalCenterX / WIDTH) - 1) * FOV;
		tilt = ((goalCenterY / HEIGHT) - 1) * FOV;
	}

	public double getTurn() {
		return turn;
	}

	public double getTilt() {
		return tilt;
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}