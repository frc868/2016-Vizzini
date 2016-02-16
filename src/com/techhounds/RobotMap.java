package com.techhounds;

import edu.wpi.first.wpilibj.I2C;

public interface RobotMap {
	interface Collector{
		final int COLLECTOR_MOTOR = 17;
		final double inPower = .5, outPower = -.5;
		final boolean COLLECTOR_IS_INVERTED = false;
		final int COLLECTOR_ANGLER = 23;
		final int BEAM_BREAK_SENSOR = 4;
	}
	
	interface Shooter{
		final int SHOOTER_MOTOR = 13;
		// Not sure if there will be a second shooter motor
		final int SHOOTER_MOTOR_B = 21;
		final boolean SHOOTER_IS_INVERTED = false;
		final int SHOOTER_SPEED_DIO = 5;
	}
	
	interface DriveTrain {
		final int DRIVE_LEFT_MOTOR = 1;
		final boolean DRIVE_LEFT_IS_INVERTED = false;
		final int DRIVE_RIGHT_MOTOR = 0;
		final boolean DRIVE_RIGHT_IS_INVERTED = false;
		final int ENC_RIGHT_A = 2;
		final int ENC_LEFT_A = 0;
		final int ENC_RIGHT_B = 3;
		final int ENC_LEFT_B = 1;
		
	}
	
	interface Gyro {
		final I2C.Port GYRO = I2C.Port.kMXP;
		final I2C.Port GYRO_PRACT = null;//I2C.Port.kMXP;
	}
}
