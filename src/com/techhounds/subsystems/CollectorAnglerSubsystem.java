package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CollectorAnglerSubsystem extends Subsystem {
	
	private static CollectorAnglerSubsystem instance;
	
	private CANTalon angler;
    
	public CollectorAnglerSubsystem(CANTalon angler) {
		this.angler = angler;
		this.angler.enableForwardSoftLimit(true);
		this.angler.enableReverseSoftLimit(true);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPower(double power) {
		power = Math.min(power, 1);
		power = Math.max(power, -1);
	}
	
	public static CollectorAnglerSubsystem getInstance(){
		if(instance == null){
			CANTalon x = new CANTalon(RobotMap.COLLECTOR_ANGLER);
			instance = new CollectorAnglerSubsystem(x);
		}
		return instance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

