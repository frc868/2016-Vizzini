package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BeamBreakSubsystem extends Subsystem {

	private static BeamBreakSubsystem instance;
	private DigitalInput sensor;
	
	private BeamBreakSubsystem(){
		sensor = new DigitalInput(RobotMap.Collector.BEAM_BREAK_DIO);
		LiveWindow.addSensor("beam_break", "sensor", sensor);
	}
	
	public boolean ballPresent(){
		return !sensor.get();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putBoolean("Ball Collected", ballPresent());
	}
	
    public static BeamBreakSubsystem getInstance(){
    	return instance == null ? instance = new BeamBreakSubsystem() : instance;
    	
    }
    
    public void initDefaultCommand() {
    	
    }
}

