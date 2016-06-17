package com.techhounds.frc2016;

public class HardwareMap {

	public interface Motors {
		public final int 
			COLLECTOR = 17,
			ANGLER = 23,
			SHOOTER = 13,
			
			DRIVE_LEFT = 1,
			DRIVE_RIGHT = 0,
			
			FLASHLIGHT = 0;
	}
	
	public interface Servos {
		public final int 
			WINCH_DT_TO_ARM = 3,
			WINCH_LOCK = 2,
			ARM_RELEASE = 5;
	}
	
	public interface DIO {
		public final int 
			SHOOTER = 4,
			BEAM_BREAK = 5,
			
			DRIVE_ENC_RIGHT_A = 2,
			DRIVE_ENC_LEFT_A = 0,
			DRIVE_ENC_RIGHT_B = 3,
			DRIVE_ENC_LEFT_B = 1,
			DRIVE_LIGHT = 0;
	}
	
	public interface LED {
		public final int 
			DIO_MODE_0 = 10,
			DIO_MODE_1 = 11,
			DIO_COLLECTOR = 13,
			DIO_DIRECTION = 17;
	}
	
	public interface PDP {
		public final int
			COLLECTOR = 11,
			DRIVE_LEFT_1 = 0,
			DRIVE_LEFT_2 = 1,
			DRIVE_LEFT_3 = 2,
			DRIVE_RIGHT_1 = 13,
			DRIVE_RIGHT_2 = 14,
			DRIVE_RIGHT_3 = 15;
	
	}
}
