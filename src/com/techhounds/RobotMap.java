package com.techhounds;

public interface RobotMap {
	
	interface OI_Constants {
		final int DRIVER_PORT = 0;
		final int OPERATOR_PORT = 1;
	}
	
	interface Angler {
		final boolean IS_INVERTED = true;
	}
	
	interface Collector{
		final int COLLECTOR_MOTOR = 17;
		final double inPower = .6, outPower = -1;
		final boolean COLLECTOR_IS_INVERTED = false;
		final int COLLECTOR_ANGLER = 23;
		final int BEAM_BREAK_SENSOR = 5;
		final double ANGLER_FORWARD_LIMIT = 0;
		final double ANGLER_REVERSE_LIMIT = 1;
		final int COLLECTOR_PDP = 11;
		
		final double COLLECTOR_UP = 382;
		final double COLLECTING = 643;
		final double COLLECTOR_DOWN = 732;
	}
	
	interface Shooter{
		final int SHOOTER_MOTOR = 13;
		// Not sure if there will be a second shooter motor
		final int SHOOTER_MOTOR_B = 21;
		final boolean SHOOTER_IS_INVERTED = false;
		final int SHOOTER_SPEED_DIO = 4;
	}
	
	interface DriveTrain {
		final int DRIVE_LEFT_MOTOR = 1;
		final boolean DRIVE_LEFT_IS_INVERTED = false;
		final int DRIVE_RIGHT_MOTOR = 0;
		final boolean DRIVE_RIGHT_IS_INVERTED = true;
		final int ENC_RIGHT_A = 2;
		final int ENC_LEFT_A = 0;
		final int ENC_RIGHT_B = 3;
		final int ENC_LEFT_B = 1;
		
		final int DRIVE_LEFT_PDP_1 = 0;
		final int DRIVE_LEFT_PDP_2 = 1;
		final int DRIVE_LEFT_PDP_3 = 2;
		final int DRIVE_RIGHT_PDP_1 = 13;
		final int DRIVE_RIGHT_PDP_2 = 14;
		final int DRIVE_RIGHT_PDP_3 = 15;
		
		final double MIN_TURN_POWER = .35;
		final double MIN_STRAIGHT_POWER = .15;
	}
	
	interface Servo {
		final int WINCH_ENABLE = 2;
		final int WINCH_LOCK = -1;
		final int SCISSOR_ONE = -1;
		final int SCISSOR_TWO = -1;
		final double WINCH_ENABLE_MAX = .4;
		final double WINCH_ENABLE_MIN = .2;
		final double WINCH_LOCK_MAX = 1;
		final double WINCH_LOCK_MIN = 0;
		final double SCISSOR_ONE_MAX = 1;
		final double SCISSOR_ONE_MIN = 0;
		final double SCISSOR_TWO_MAX = 1;
		final double SCISSOR_TWO_MIN = 0;
		final boolean WINCH_ENABLE_IS_UP_DEFAULT = true;
		final boolean WINCH_LOCK_IS_UP_DEFAULT = false;
		final boolean SCISSOR_ONE_IS_UP_DEFAULT = false;
		final boolean SCISSOR_TWO_IS_UP_DEFAULT = false;
		
	}
	
	interface Defenses {
		//If a double has DONE after it, please don't change it, as that should be an accurate value for the variable.
		//In inches, objective should be to cross a defense and end with wheels just past it, around 125 in.
		//Standardized, back of robot is 74 in from front of defense.
		
		final double MOAT_SPEED					= .48;//DONE
		final double MOAT_DISTANCE 				= 148.5;//DONE
		
		final double LOW_BAR_SPEED 				= .4;//DONE
		final double LOW_BAR_DISTANCE 			= 127.5;//DONE

		final double RAMPARTS_SPEED 			= .5;//DONE
		final double RAMPARTS_DISTANCE 			= 139.5;//DONE
		
		final double ROCK_WALL_SPEED 			= .5;//
		final double ROCK_WALL_DISTANCE			= 143.5;//
		
		final double ROUGH_TERRAIN_SPEED 		= .5;//SET
		final double ROUGH_TERRAIN_DISTANCE 	= 139.5;//
		
		final double PORTCULLIS_SPEED_1 		= .4;//Moving to Portcullis
		final double PORTCULLIS_SPEED_2 		= .3;//after lowering collector
		final double PORTCULLIS_SPEED_3 		= .5;//while opening Portcullis and after
		final double PORTCULLIS_DISTANCE_1 		= 60;//Moving to Portcullis
		final double PORTCULLIS_DISTANCE_2 		= 20;//after lowering collector
		final double PORTCULLIS_DISTANCE_3 		= 60;//while opening Portcullis and after
		
		final double CDF_SPEED_1 				= .4;//before lowering CDF
		final double CDF_SPEED_2 				= .5;//after
		final double CDF_DISTANCE_1 			= 60;//before lowering CDF
		final double CDF_DISTANCE_2				= 60;//after
		final double CDF_WAIT_1					= .2;//after lowering collector
		final double CDF_WAIT_2					= .1;//before raising collector
		
		final double TO_DEFENSE_SPEED			= .5;//DONE
		final double DEFENSE_DISTANCE			= 45;//DONE
	}
	
	interface LED {
		// Two "option bits" to set LED mode on arduine connected to DIO ports
		final static int DIO_MODE_0 = 8;
		final static int DIO_MODE_1 = 9;
	}
}
