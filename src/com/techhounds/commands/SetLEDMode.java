package com.techhounds.commands;

import com.techhounds.subsystems.LEDSubsystem;
import com.techhounds.subsystems.LEDSubsystem.mode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLEDMode extends Command {
	
	private LEDSubsystem led;
	private mode state;

    public SetLEDMode(mode mode) {
        led = LEDSubsystem.getInstance();
        requires(led);
        state = mode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state){
    		case FORWARDUP: 
    			led.set(false, true);
    			break;
    		
    		case REVERSEUP:  
    			led.set(false, false);
    			break;
    			
    		case FORWARDDOWN:
    			led.set(true, true);
    			break;
    			
    		case REVERSEDOWN:
    			led.set(true, false);
    			break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
