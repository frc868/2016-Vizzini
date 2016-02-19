package com.techhounds;

import edu.wpi.first.wpilibj.I2C;

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
		final double inPower = .5, outPower = -.5;
		final boolean COLLECTOR_IS_INVERTED = false;
		final int COLLECTOR_ANGLER = 23;
		final int BEAM_BREAK_SENSOR = 5;
		final double ANGLER_FORWARD_LIMIT = 0;
		final double ANGLER_REVERSE_LIMIT = 1;
		final int COLLECTOR_PDP = 11;
		
		final double COLLECTOR_HEIGHT = 416;
		final double DEFENSE_PASS_HEIGHT = 200;
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
		final boolean DRIVE_LEFT_IS_INVERTED = true;
		final int DRIVE_RIGHT_MOTOR = 0;
		final boolean DRIVE_RIGHT_IS_INVERTED = false;
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
	}
	
	interface Servo {
		final int WINCH_ENABLE = 2;
		final int WINCH_LOCK = -1;
		final int SCISSOR_ONE = -1;
		final int SCISSOR_TWO = -1;
		final double WINCH_ENABLE_MAX = .54;
		final double WINCH_ENABLE_MIN = .35;
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
	
	interface Gyro {
		final I2C.Port GYRO = I2C.Port.kMXP;
		final I2C.Port GYRO_PRACT = null;//I2C.Port.kMXP;
	}
}
