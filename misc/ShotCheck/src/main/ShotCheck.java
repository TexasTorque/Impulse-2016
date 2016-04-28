package main;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class ShotCheck extends JFrame {

	private static final long serialVersionUID = 1L;

	private static ITable dashboard;

	public static void main(String[] args) {
		NetworkTable.setIPAddress("10.14.77.2");
		NetworkTable.setClientMode();
		dashboard = NetworkTable.getTable("SmartDashboard");
		JDialog dialog = new JOptionPane("Opening...", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, new Object[] {}).createDialog("");
		new Thread(() -> {
			double time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time < 500) {
			}
			dialog.dispose();
		}).start();
		dialog.setVisible(true);
		new ShotCheck();
	}

	public ShotCheck() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		double vel;
		double setpoint;
		while (true) {
			try {
				vel = dashboard.getNumber("FlywheelVelocity", 0.0);
				setpoint = dashboard.getNumber("FlywheelSetpointVelocity", 0.0);

				if (setpoint == 0) {
					getContentPane().setBackground(Color.red);
				} else if (vel < setpoint && vel > setpoint / 2.0) {
					getContentPane().setBackground(Color.orange);
				} else if (vel > setpoint) {
					getContentPane().setBackground(Color.green);
				} else {
					getContentPane().setBackground(Color.red);
				}
			} catch (Exception e) {
				e.printStackTrace();
				getContentPane().setBackground(Color.red);
			}
		}
	}
}
