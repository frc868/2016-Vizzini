package com.techhounds.frc2016;

public class HardwareConstants {

	public interface OI {
		final int DRIVER_PORT = 0;
		final int OPERATOR_PORT = 1;
	}
	
	public interface Angler {
		final boolean INVERTED = true;
		
		final double UP = 382 + 96;
		final double COLLECT = 643 + 96 - 20;
		final double DOWN = 732 + 96;
		final double AUTON_COLLECT = 670;
	}
	
	public interface Shooter {
		final boolean INVERTED = true;
	}
	
	public interface Drive {
		final boolean LEFT_INVERTED = false;
		final boolean RIGHT_INVERTED = true;
		
		final double MIN_TURN_POWER = .45;
		final double MIN_STRAIGHT_POWER = .35;
		final int LIGHT_THRESHOLD = 280;
		
		final double MAX_VELOCITY = 145;
		final double MAX_ACCELERATION = 72.5;
	}
	
	public interface Collector {
		final boolean INVERTED = false;

		final double inPower = .6, outPower = -1;
	}
	
	public interface Servo {
		
		public final double
			WINCH_DT_TO_ARM_MAX = .5,
			WINCH_DT_TO_ARM_MIN = .2,
			
			WINCH_LOCK_MAX = 1,
			WINCH_LOCK_MIN = 0,
			
			ARM_RELEASE_MAX = 0.9,
			ARM_RELEASE_MIN = 0.05;

		public final boolean
			WIN_DT_TO_ARM_UP = true,
			WINCH_LOCK_UP = false,
			ARM_RELEASE_UP = false;
	}

	public interface Defenses {
		//If a double has DONE after it, please don't change it, as that should be an accurate value for the variable.
		//In inches, objective should be to cross a defense and end with wheels just past it, around 125 in.
		//Standardized, back of robot is 74 in from front of defense.
		
		final double MOAT_SPEED					= .6;//.48;//DONE
		final double MOAT_DISTANCE 				= 165;// + 48; + 45;//DONE
		
		final double LOW_BAR_SPEED 				= .8;//DONE
		final double LOW_BAR_DISTANCE 			= 127.5 + 16 + 24 + 36 + 12;//DONE

		final double RAMPARTS_SPEED 			= .55;//DONE
		final double RAMPARTS_DISTANCE 			= 139.5 + 48;//DONE
		
		final double ROCK_WALL_SPEED 			= .65;//
		final double ROCK_WALL_DISTANCE			= 143.5 + 48 - 24;//
		
		final double ROUGH_TERRAIN_SPEED 		= .5;//SET
		final double ROUGH_TERRAIN_DISTANCE 	= 139.5 + 48;//
		
		final double PORTCULLIS_SPEED_1 		= .5;//Moving to Portcullis
		final double PORTCULLIS_SPEED_2 		= .3;//after lowering collector
		final double PORTCULLIS_SPEED_3 		= .75;//while opening Portcullis and after
		final double PORTCULLIS_DISTANCE_1 		= 60;//Moving to Portcullis
		final double PORTCULLIS_DISTANCE_2 		= 20;//after lowering collector
		final double PORTCULLIS_DISTANCE_3 		= 130;//while opening Portcullis and after
		
		final double CDF_SPEED_1 				= .6;//before lowering CDF
		final double CDF_SPEED_2 				= 1;//after
		final double CDF_DISTANCE_1 			= 46;//before lowering CDF
		final double CDF_DISTANCE_2				= 130;//after
		
		final double TO_DEFENSE_SPEED			= .5;//DONE
		final double DEFENSE_DISTANCE			= 45;//DONE
	}
}
