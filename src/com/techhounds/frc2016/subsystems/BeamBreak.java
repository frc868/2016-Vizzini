package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BeamBreakSubsystem extends HoundSubsystem {
	
	private DigitalInput sensor = HardwareAdaptor.kDIO_BeamBreak;
	private static BeamBreakSubsystem instance;
	
	private BeamBreakSubsystem(){
	}
	
	public boolean ballPresent(){
		return !sensor.get();
	}
	
	public void updatePeriodic(){
		SmartDashboard.putBoolean("Ball Collected", ballPresent());
	}
	
    public static BeamBreakSubsystem getInstance(){
    	return instance == null ? new BeamBreakSubsystem() : instance;
    }
    
    public void initDefaultCommand() {
    	
    }
}

