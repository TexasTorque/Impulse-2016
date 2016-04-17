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
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.14.77.12");
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

		new Thread(() -> {
			while (true) {
				if (dashboard.getBoolean("RPM_READY")) {
					getContentPane().setBackground(Color.white);
				} else {
					try {
						int percent = (int) (255 * dashboard.getNumber("FlywheelVelocity") / dashboard.getNumber("FlywheelVelocitySetpoint"));
						getContentPane().setBackground(new Color(255 - percent, percent, 0));
					} catch (Exception e) {
						getContentPane().setBackground(Color.red);
					}
				}
			}
		}).start();

	}
}
