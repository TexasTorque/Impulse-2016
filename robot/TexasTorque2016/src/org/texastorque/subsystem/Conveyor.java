package org.texastorque.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conveyor extends Subsystem {

	private static Conveyor instance;

	private double conveyorSpeed;

	@Override
	public void init() {
	}

	@Override
	public void run() {
		if (input.isConveyorIntaking() || feedback.visionShotReady()) {
			conveyorSpeed = 1.0;
		} else if (input.isConveyorOuttaking()) {
			conveyorSpeed = -1.0;
		} else {
			conveyorSpeed = 0.0;
		}

		output();
	}

	@Override
	protected void output() {
		output.setConveyorSpeed(conveyorSpeed);
	}

	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("ConveyorSpeed", conveyorSpeed);
	}

	// singleton
	public static Conveyor getInstance() {
		return instance == null ? instance = new Conveyor() : instance;
	}
}