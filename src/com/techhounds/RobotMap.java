package com.techhounds;

import edu.wpi.first.wpilibj.I2C;

public class RobotMap {
	public static class Collector{
		public static final int COLLECTOR_MOTOR = -1;
		public static final double inPower = .5, outPower = -.5;
		public static final boolean COLLECTOR_IS_INVERTED = false;
		public static final int COLLECTOR_ANGLER = -1;
	}
	
	public static class Shooter{
		public static final int SHOOTER_MOTOR = -1;
		public static final boolean SHOOTER_IS_INVERTED = false;
	}
	
	public static class DriveTrain {
		public static final int DRIVE_LEFT_MOTOR = -1;
		public static final boolean DRIVE_LEFT_IS_INVERTED = false;
		public static final int DRIVE_RIGHT_MOTOR = -1;
		public static final boolean DRIVE_RIGHT_IS_INVERTED = false;
	}
	
	public static class Gyro {
		public static final I2C.Port GYRO = I2C.Port.kMXP;
		public static final I2C.Port GYRO_PRACT = null;//I2C.Port.kMXP;
	}
}
