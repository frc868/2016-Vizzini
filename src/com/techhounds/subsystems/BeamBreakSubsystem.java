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
	/*read only subsystem, does not need to be required
	 * 
	 */
	private DigitalInput sensor;
	private static BeamBreakSubsystem instance;
	
	private BeamBreakSubsystem(){
		sensor = new DigitalInput(RobotMap.Collector.BEAM_BREAK_SENSOR);
		LiveWindow.addSensor("beam_break", "sensor", sensor);
	}
	public boolean ballPresent(){
		return !sensor.get();
	}
	public void updateSmartDashboard(){
		SmartDashboard.putBoolean("Beam Break Sensor", ballPresent());
	}
    public static BeamBreakSubsystem getInstance(){
    	if(instance == null){
    		instance = new BeamBreakSubsystem();
    	}
    	return instance;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

