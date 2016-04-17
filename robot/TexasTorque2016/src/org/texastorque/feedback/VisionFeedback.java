package org.texastorque.feedback;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class VisionFeedback {

	private static VisionFeedback instance;
	private static boolean networking = false;

	private Thread networkThread;

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
				jetson.setSoTimeout(1);
				jetsonBuffer = new byte[256];
				jetsonPacket = new DatagramPacket(jetsonBuffer, jetsonBuffer.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		networkThread = new Thread(() -> {
			while (true) {
				if (networking) {
					run();
				}
			}
		});
	}

	public void run() {
		try {
			jetson.receive(jetsonPacket);
			jetsonValues = new String(jetsonBuffer, 0, jetsonPacket.getLength()).split(",");

			turn = Double.parseDouble(jetsonValues[0]);
			tilt = Double.parseDouble(jetsonValues[1]);
			distance = Double.parseDouble(jetsonValues[2]);
			jetsonHeartbeat = Integer.parseInt(jetsonValues[3]);
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

	public void setNetworking(boolean _networking) {
		networking = _networking;
		if (networking && networkThread.getState() == Thread.State.NEW) {
			networkThread.start();
		}
	}

	// never called, needs to be called at some point
	public void stopNetwork() {
		jetson.disconnect();
		jetson.close();
	}

	// singleton
	public static VisionFeedback getInstance() {
		return instance == null ? instance = new VisionFeedback() : instance;
	}
}
