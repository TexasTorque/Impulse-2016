package org.texastorque.auto;

public enum AutoCommands{
	
	LOWBAR,
	RAMPARTS,
	ROCKWALL,
	MOAT,
	CDF,
	HGSHOT,
	ROTATE;
	
	public static boolean doAction(AutoCommands command){
		switch(command){
			case LOWBAR:
				//TODO: implement drive code for LowBar
				return true;
			case RAMPARTS:
				//TODO: implement drive code for Ramparts
				return true;
			case ROCKWALL:
				//TODO: implement drive code for Rockwall
				return true;
			case MOAT:
				//TODO: implement drive code for Moat
				return true;
			case CDF:
				//TODO: implement drive code for CDF
				return true;
			case HGSHOT:
				//TODO: implement drive code for High Goal Shot
				return true;
			case ROTATE:
				//TODO: implement drive code for About Facing
				return true;
			default:
				break;
		}
		return false;
	}
}

