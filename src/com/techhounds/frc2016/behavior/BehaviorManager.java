package com.techhounds.frc2016.behavior;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.Systems;
import com.techhounds.frc2016.behavior.routine.FireRoutine;
import com.techhounds.frc2016.behavior.routine.PreFireRoutine;
import com.techhounds.frc2016.behavior.routine.Routine;
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
	
	private Routine m_routine;
	
	public void setRoutine(Routine routine) {
		m_routine = routine;
	}
	
	public void update(Commands commands) {
		
		Commands.Angler anglerCommand = commands.m_angler;
		Commands.Collector collectCommand = commands.m_collector;
		Commands.Shooter shooterCommand = commands.m_shooter;
		Commands.Flashlight flashlightCommand = commands.m_flashlight;
		
		if(m_routine != null && m_routine.isFinished()) {
			setRoutine(null);
		}
		
		if(m_routine != null && !m_routine.uses(Systems.COLLECTOR)) {
			
			if(collectCommand == Commands.Collector.IN) {
				collector.setPower(beamBreak.ballPresent() ? 0 : HardwareConstants.Collector.inPower);
			} else if(collectCommand == Commands.Collector.OUT) {
				collector.setPower(HardwareConstants.Collector.outPower);
			} else if(collectCommand == Commands.Collector.FIRE) {
				setRoutine(new FireRoutine());
			} else {
				collector.setPower(0.0);
			}
			
		}
		
		if(m_routine != null && !m_routine.uses(Systems.ANGLER)) {
			
			if(anglerCommand != Commands.Angler.DO_NOTHING) {
				if(anglerCommand == Commands.Angler.UP) {
					angler.increaseState();
				} else if(anglerCommand == Commands.Angler.DOWN) {
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
		}
		
		if(m_routine != null && !m_routine.uses(Systems.SHOOTER)) {
			
			if(shooterCommand != Commands.Shooter.DO_NOTHING) {
				if(shooterCommand == Commands.Shooter.START) {
					setRoutine(new PreFireRoutine());
				} else if(shooterCommand == Commands.Shooter.STOP) {
					shooter.setPower(0.0);
				}
			}
		}
		
		if(m_routine != null)
			m_routine.update();
		
		if(flashlightCommand != Commands.Flashlight.DO_NOTHING) {
			if(flashlightCommand == Commands.Flashlight.TOGGLE) 
				flashlight.toggle();
		}
	}
}
