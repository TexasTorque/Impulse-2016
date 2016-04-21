package org.texastorque.feedback;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import org.texastorque.constants.Constants;

public class VisionFeedback {

	private static VisionFeedback instance;

	private Thread networkThread;
	private boolean halt;

	private DatagramSocket jetson;
	private DatagramPacket jetsonPacket;
	private byte[] jetsonBuffer;
	private String[] jetsonValues;

	private double turn;
	private double tilt;
	private double distance;
	private int jetsonHeartbeat;

	public VisionFeedback() {
		if (jetson == null) {
			try {
				jetson = new DatagramSocket(5805);
				jetson.setSoTimeout(100);
				jetsonBuffer = new byte[1028];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		networkThread = new Thread(() -> {
			while (true) {
				run();
				if (halt) {
					break;
				}
			}
		});
		networkThread.start();
	}

	public void run() {
		try {
			jetsonPacket = new DatagramPacket(jetsonBuffer, jetsonBuffer.length);
			jetson.receive(jetsonPacket);
			jetsonValues = new String(jetsonBuffer, 0, jetsonPacket.getLength()).split(",");

			turn = Double.parseDouble(jetsonValues[0]);
			tilt = Double.parseDouble(jetsonValues[1]);
			distance = Double.parseDouble(jetsonValues[2]);
			jetsonHeartbeat = (int) Double.parseDouble(jetsonValues[3]);

			if (distance < 0) {// tower not found
				turn = 0.0;
				tilt = Constants.S_DOWN_SETPOINT.getDouble();
			}
		} catch (Exception e) {
			if (!(e instanceof SocketTimeoutException)) {
				e.printStackTrace();
			}
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

	public int getJetsonHeartbeat() {
		return jetsonHeartbeat;
	}

	// never called, needs to be called at some point
	public void stopNetwork() {
		halt = true;
		try {
			networkThread.join(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		jetson.disconnect();
		jetson.close();
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}
