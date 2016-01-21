package org.texastorque;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDPLogger{

	PowerDistributionPanel pdp;
	
	public PDPLogger(){
		pdp = new PowerDistributionPanel();
	}
	
	public String readOut(){
		String output = "";
		for(int x = 0; x <= 15; x++){
			output += "Channel" + x + "Current" + getChannelCurrent(x)+"\t\t";
			if(x%2 == 0)
				output += "\n";
		}
		output += "\n\nCurrentPDPTemperature: " + getTemperature();
		output += "\nCurrentPDPTotalEnergy: " + getTotalEnergy();
		output += "\nCurrentPDPTotalCurrent: " + getTotalCurrent();
		output += "\nCurrentPDPTotalPower: " + getTotalPower();
		output += "\nCurrentPDPVoltage: " + getVoltage();
		return output;
	}
	
	public double getChannelCurrent(int channel){
		return pdp.getCurrent(channel);
	}
	
	public double getTemperature(){
		return pdp.getTemperature();
	}
	
	public double getTotalEnergy(){
		return pdp.getTotalEnergy();
	}
	
	public double getTotalCurrent(){
		return pdp.getTotalCurrent();
	}
	
	public double getTotalPower(){
		return pdp.getTotalPower();
	}
	
	public double getVoltage(){
		return pdp.getVoltage();
	}
	
	public void resetPDPEnergy(){
		pdp.resetTotalEnergy();
	}
	
}
