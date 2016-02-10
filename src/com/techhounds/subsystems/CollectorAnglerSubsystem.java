package com.techhounds.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CollectorAnglerSubsystem extends Subsystem {
	
	private static CollectorAnglerSubsystem instance;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static CollectorAnglerSubsystem getInstance(){
		if(instance == null){
			instance = new CollectorAnglerSubsystem();
		}
		return instance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

