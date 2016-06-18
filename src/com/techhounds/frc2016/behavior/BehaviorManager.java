package com.techhounds.frc2016.behavior;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.OperatorInterface;
import com.techhounds.frc2016.OperatorInterface.Commands;
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

public class BehaviorManager {
	
	public static final Angler angler = 		HardwareAdaptor.kAnglerSubsystem;
	public static final BeamBreak beamBreak = 	HardwareAdaptor.kBeamBreakSubsystem;
	public static final Collector collector = 	HardwareAdaptor.kCollectorSubsystem;
	public static final Drive drive = 			HardwareAdaptor.kDriveSubsystem;
	public static final Gyro gyro = 			HardwareAdaptor.kGyroSubsystem;
	public static final Flashlight flashlight = HardwareAdaptor.kFlashlightSubsystem;
	public static final LED leds = 				HardwareAdaptor.kLEDSubsystem;
	public static final Servos winch_enb = 		HardwareAdaptor.kServo_WINCH_ENB;
	public static final Servos winch_lock = 	HardwareAdaptor.kServo_WINCH_LOC;
	public static final Servos release = 		HardwareAdaptor.kServo_RELEASE;
	public static final Shooter shooter = 		HardwareAdaptor.kShooterSubsystem;
	public static final Vision vision = 		HardwareAdaptor.kVisionSubsystem;
	
	public void update(Commands[] commands) {
		
		Commands collectCommand = commands[0];
		Commands anglerCommand = commands[1];
		Commands shooterCommand = commands[2];
		Commands flashlightCommand = commands[3];
		Commands servoRelease = commands[4];
		Commands servoWinchDT = commands[5];
		Commands servoLock = commands[6];
		
		if(collectCommand == Commands.COLLECT_IN) {
			collector.setPower(beamBreak.ballPresent() ? 0 : HardwareConstants.Collector.inPower);
		} else if(collectCommand == Commands.COLLECT_OUT) {
			collector.setPower(HardwareConstants.Collector.outPower);
		} else if(collectCommand == Commands.COLLECT_FIRE) {
			collector.setPower(HardwareConstants.Collector.shooterPower);
		} else {
			collector.setPower(0.0);
		}
		
		if(anglerCommand != Commands.ANGLER_DO_NOTHING) {
			if(anglerCommand == Commands.ANGLER_TO_UP) {
				angler.increaseState();
			} else if(anglerCommand == Commands.ANGLER_TO_DOWN) {
				angler.decreaseState();
			}
			
			if(angler.getState() == 0) {
	    		angler.setPosition(HardwareConstants.Angler.DOWN);
	    	} else if(angler.getState() == 1) {
	    		angler.setPosition(HardwareConstants.Angler.COLLECT);
	    	} else if(angler.getState() == 2) {
	    		angler.setPosition(HardwareConstants.Angler.UP);
	    	}
		}
		
		if(shooterCommand != Commands.SHOOTER_DO_NOTHING) {
			if(shooterCommand == Commands.SHOOTER_START) {
				shooter.setSpeed(70.0);
			} else if(shooterCommand == Commands.SHOOTER_STOP) {
				shooter.setPower(0.0);
			}
		}
		
		if(flashlightCommand != Commands.FLASHLIGHT_DO_NOTHING) {
			if(flashlightCommand == Commands.FLASHLIGHT_TOGGLE) 
				flashlight.toggle();
		}
	}
}
