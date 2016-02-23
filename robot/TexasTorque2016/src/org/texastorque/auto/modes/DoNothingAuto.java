package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class DoNothingAuto extends AutoMode {
	
	@Override
	protected void run() {
	}
	
	@Override
	public String getName() {
		return "DoNothingAuto";
	}
}
