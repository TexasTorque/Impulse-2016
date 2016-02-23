package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * -One Boolean 'testing'
 * if testing,
 * 	enable compression testing solenoid
 * 	
 * @author 2015 ProLaptop
 *
 */
public class CompressionTest extends Subsystem{

	private boolean compressionTesting;
	private double compressionValue;
	
	@Override
	public void init() {
	}

	@Override
	public void run() {
		compressionValue = feedback.getCompressionValue();
		compressionTesting = input.getCompressionTesting();
	}

	@Override
	protected void output() {
		output.setCompressionTesting(compressionTesting);
	}	

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("CompressionTesting", compressionTesting);
		SmartDashboard.putNumber("CompressionValue", compressionValue);
	}

}
