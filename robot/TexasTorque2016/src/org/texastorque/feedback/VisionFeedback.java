package org.texastorque.feedback;

import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.texastorque.constants.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class VisionFeedback {

	private static VisionFeedback instance;

	// jetson network processing
	private boolean networkInit;
	private boolean useNetwork = false;

	private DatagramSocket jetson;
	private DatagramPacket jetsonPacket;
	private byte[] jetsonBuffer;
	private String[] jetsonValues;

	private double jetsonTilt;
	private double jetsonTurn;
	private double jetsonDistance;
	private int jetsonHeartbeat;

	// off board processing
	private double CAM_WIDTH;
	private double CAM_HEIGHT;
	private double CAM_FOV;
	private double H_DIFF;
	private double X_CAM_ROBOT;
	private double THETA_PLUS;
	private double Vsq;
	private double G;
	private double k1;

	private ITable visionTable;
	private double goalCenterX;
	private double goalCenterY;
	private double turn;
	private double tilt;
	private double _tilt1;
	private boolean t1valid;
	private double _tilt2;
	private boolean t2valid;

	private double distance;

	public VisionFeedback() {
		visionTable = NetworkTable.getTable("GRIP").getSubTable("visionReport");
	}

	private double calcTilt() {
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

		t1valid = _tilt1 != 0.0 && _tilt1 > Constants.S_DOWN_SETPOINT.getDouble() && _tilt1 < 37;
		t2valid = _tilt2 != 0.0 && _tilt2 > Constants.S_DOWN_SETPOINT.getDouble() && _tilt2 < 37;

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
		return Constants.S_DOWN_SETPOINT.getDouble();
	}

	public void calc() {
		if (useNetwork) {
			try {
				jetson.receive(jetsonPacket);
				jetsonValues = new String(jetsonBuffer, 0, jetsonPacket.getLength()).split(",");
				jetsonTurn = Double.parseDouble(jetsonValues[0]);
				jetsonTilt = Double.parseDouble(jetsonValues[1]);
				jetsonDistance = Double.parseDouble(jetsonValues[2]);
				jetsonHeartbeat = Integer.parseInt(jetsonValues[3]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

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
			tilt = Constants.S_DOWN_SETPOINT.getDouble();
			return;
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

	public double getTilt1() {
		return _tilt1;
	}

	public double getTilt2() {
		return _tilt2;
	}

	public int getJetsonHeartbeat() {
		return jetsonHeartbeat;
	}

	public void visionInit() {
		if (!networkInit) {
			networkInit = false;

			try {
				jetsonBuffer = new byte[2048];
				jetsonPacket = new DatagramPacket(jetsonBuffer, jetsonBuffer.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		CAM_WIDTH = Constants.V_CAMERA_WIDTH.getDouble();
		CAM_HEIGHT = Constants.V_CAMERA_HEIGHT.getDouble();
		CAM_FOV = Constants.V_CAMERA_FOV.getDouble();

		H_DIFF = Constants.V_TOWER_HEIGHT.getDouble() - Constants.V_ROBOT_HEIGHT.getDouble();
		X_CAM_ROBOT = Constants.V_ROBOT_CAMERA_DISTANCE.getDouble();
		THETA_PLUS = Constants.V_CAMERA_MOUNT_ANGLE.getDouble() - CAM_FOV / 2.0;

		G = Constants.V_G.getDouble();
		Vsq = 7.5;
		Vsq *= Vsq;

		k1 = 2 * H_DIFF * Vsq;
	}

	public void stop() {
		try {
			jetson.disconnect();
			jetson.close();
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				e.printStackTrace();
			}
		}
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
	
	public static void init() {
		getInstance().visionInit();
	}
}
