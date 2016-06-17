package com.techhounds.frc2016.commands.angler;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.subsystems.Angler;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetStateUp extends Command {
	
	private final Angler angler = HardwareAdaptor.kAnglerSubsystem;

    protected void initialize() {
    	angler.increaseState();
    	if(angler.getState() == 0) {
    		new SetAnglerPosition(HardwareConstants.Angler.DOWN).start();
    	} else if(angler.getState() == 1) {
    		new SetAnglerPosition(HardwareConstants.Angler.COLLECT).start();
    	} else if(angler.getState() == 2) {
    		new SetAnglerPosition(HardwareConstants.Angler.UP).start();
    	}
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }
    
    protected void interrupted() {
    }
}
