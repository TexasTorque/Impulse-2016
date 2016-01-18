package org.texastorque.feedback;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import org.texastorque.feedback.vision.VisionManager;

public class Feedback {

	private static Feedback instance;

	private VisionManager visionManager;
	private double G = -386.088583;// gravity in/s^2: constant
	private double Lrobot = 0.0;// length of the robot from back to front:
								// constant
	private double Hrobot = 0.0;// height of the robot from bottom to top:
								// constant
	private double H = 85.0;// tower height: constant
	private double Hmod = 0.0;// tower height from
	private double V = 0.0;// constant shooter velocity?: constant?

	// sensors

	// values
	private double distanceAhead;

	public Feedback() {
		visionManager = VisionManager.getInstance();
	}

	public void update() {
		// distanceAhead = sensor.get
		visionManager.update();
	}

	public double getRequiredTilt() {
		// return (calcReqTilt(H) + visionManager.getNecessaryPitch())/ 2;
		// return calcReqTilt(Hmod);
		return calcReqTilt(H);
	}

	public double getDistanceAhead() {
		return distanceAhead;
	}

	public void pushToDashboard() {
		visionManager.pushToDashboard();
	}

	// private helpers
	private double calcReqTilt(double dh) {
		double v0 = 0.0;
		double v1 = 0.0;
		try {
			v0 = atan((pow(V, 2) + sqrt(pow(V, 4) - G * (G * pow(distanceAhead, 2) + 2 * dh * pow(V, 2))))
					/ (distanceAhead * G));
			v1 = atan((pow(V, 2) - sqrt(pow(V, 4) - G * (G * pow(distanceAhead, 2) + 2 * dh * pow(V, 2))))
					/ (distanceAhead * G));
		} catch (Exception e) {
		}

		if (v0 != 0.0 && v1 != 0.0) {
			if (abs(v0) < abs(v1)) {
				calcModH(0);
				return v0;
			} else {
				calcModH(1);
				return v1;
			}
		}

		if (v0 == 0.0) {
			calcModH(1);
			return v1;
		} else if (v1 == 0.0) {
			calcModH(0);
			return v0;
		}
		return 0.0;
	}

	private void calcModH(int choice) {
		Hmod = pow(Hrobot * G * distanceAhead / Lrobot + (choice == 0 ? -pow(V, 2) : pow(V, 2)), 2) - pow(V, 4)
				+ G * pow(distanceAhead, 2);
	}

	// singleton
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
