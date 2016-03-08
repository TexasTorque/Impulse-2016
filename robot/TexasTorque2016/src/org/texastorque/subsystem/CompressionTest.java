package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CompressionTest extends Subsystem {

	private static CompressionTest instance;

	private boolean compressionTesting;
	
	private double maxCompressionRate;

	// sensor values
	private double compressionValue;
	private boolean ballIn;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		compressionValue = feedback.getCompressionValue();
		ballIn = feedback.isCompressionTestReady();

		compressionTesting = input.getCompressionTesting();
		
		if (compressionTesting) {
			if (compressionValue > maxCompressionRate) {
				maxCompressionRate = compressionValue;
			}
		} else {
			maxCompressionRate = 0.0;
		}
		
		output();
	}

	@Override
	protected void output() {
		output.setCompressionTesting(compressionTesting);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putBoolean("CompressionTesting", compressionTesting);
		
		SmartDashboard.putNumber("CompressionValue", compressionValue);
		SmartDashboard.putNumber("MaxCompressionRate", maxCompressionRate);
		SmartDashboard.putBoolean("CompressionBallIn", ballIn);
	}

	// singleton
	public static CompressionTest getInstance() {
		return instance == null ? instance = new CompressionTest() : instance;
	}

}
