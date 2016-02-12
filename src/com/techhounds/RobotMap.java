package com.techhounds;

import edu.wpi.first.wpilibj.I2C;

public interface RobotMap {
	interface Collector{
		final int COLLECTOR_MOTOR = -1;
		final double inPower = .5, outPower = -.5;
		final boolean COLLECTOR_IS_INVERTED = false;
		final int COLLECTOR_ANGLER = -1;
	}
	
	interface Shooter{
		final int SHOOTER_MOTOR = -1;
		final boolean SHOOTER_IS_INVERTED = false;
	}
	
	interface DriveTrain {
		final int DRIVE_LEFT_MOTOR = -1;
		final boolean DRIVE_LEFT_IS_INVERTED = false;
		final int DRIVE_RIGHT_MOTOR = -1;
		final boolean DRIVE_RIGHT_IS_INVERTED = false;
	}
	
	interface Gyro {
		final I2C.Port GYRO = I2C.Port.kMXP;
		final I2C.Port GYRO_PRACT = null;//I2C.Port.kMXP;
	}
}
