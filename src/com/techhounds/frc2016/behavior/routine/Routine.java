package com.techhounds.frc2016.behavior.routine;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.Systems;
import com.techhounds.frc2016.subsystems.Angler;
import com.techhounds.frc2016.subsystems.BeamBreak;
import com.techhounds.frc2016.subsystems.Collector;
import com.techhounds.frc2016.subsystems.Drive;
import com.techhounds.frc2016.subsystems.Flashlight;
import com.techhounds.frc2016.subsystems.Gyro;
import com.techhounds.frc2016.subsystems.LED;
import com.techhounds.frc2016.subsystems.Servos;
import com.techhounds.frc2016.subsystems.Shooter;
import com.techhounds.frc2016.subsystems.Vision;

public abstract class Routine {

	static final Angler angler = 			HardwareAdaptor.kAnglerSubsystem;
	static final BeamBreak beamBreak = 		HardwareAdaptor.kBeamBreakSubsystem;
	static final Collector collector = 		HardwareAdaptor.kCollectorSubsystem;
	static final Drive drive = 				HardwareAdaptor.kDriveSubsystem;
	static final Gyro gyro = 				HardwareAdaptor.kGyroSubsystem;
	static final Flashlight flashlight = 	HardwareAdaptor.kFlashlightSubsystem;
	static final LED leds = 				HardwareAdaptor.kLEDSubsystem;
	static final Servos winch_enb = 		HardwareAdaptor.kServo_WINCH_ENB;
	static final Servos winch_lock =	 	HardwareAdaptor.kServo_WINCH_LOC;
	static final Servos release = 			HardwareAdaptor.kServo_RELEASE;
	static final Shooter shooter = 			HardwareAdaptor.kShooterSubsystem;
	static final Vision vision = 			HardwareAdaptor.kVisionSubsystem;
	
	public abstract void update();
	public abstract boolean isFinished();
	public abstract boolean uses(Systems system);
}
