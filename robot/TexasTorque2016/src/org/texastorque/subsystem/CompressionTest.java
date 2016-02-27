package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CompressionTest extends Subsystem {

	private static CompressionTest instance;

	private boolean compressionTesting;

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
		SmartDashboard.putBoolean("CompressionBallIn", ballIn);
	}

	// singleton
	public static CompressionTest getInstance() {
		return instance == null ? instance = new CompressionTest() : instance;
	}

}
