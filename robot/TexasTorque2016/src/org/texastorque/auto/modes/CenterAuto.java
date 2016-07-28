package org.texastorque.auto.modes;

import org.texastorque.auto.AutoMode;

public class CenterAuto extends AutoMode{

	@Override
	protected void run() {
		vision();
	}
}
