package org.texastorque;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PDPLogger {

	private static PowerDistributionPanel pdp = new PowerDistributionPanel();

	public static void pushToDashboard() {
		for (int x = 0; x <= 15; x++) {
			SmartDashboard.putNumber("Channel" + x + "Current", getChannelCurrent(x));
		}
		SmartDashboard.putNumber("CurrentPDPTemperature", getTemperature());
		SmartDashboard.putNumber("CurrentPDPTotalEnergy", getTotalEnergy());
		SmartDashboard.putNumber("CurrentPDPTotalCurrent", getTotalCurrent());
		SmartDashboard.putNumber("CurrentPDPTotalPower", getTotalPower());
		SmartDashboard.putNumber("CurrentPDPVoltage", getVoltage());
	}

	private static double getChannelCurrent(int channel) {
		return pdp.getCurrent(channel);
	}

	private static double getTemperature() {
		return pdp.getTemperature();
	}

	private static double getTotalEnergy() {
		return pdp.getTotalEnergy();
	}

	private static double getTotalCurrent() {
		return pdp.getTotalCurrent();
	}

	private static double getTotalPower() {
		return pdp.getTotalPower();
	}

	private static double getVoltage() {
		return pdp.getVoltage();
	}

	private static void resetPDPEnergy() {
		pdp.resetTotalEnergy();
	}

}
