package com.techhounds.frc2016.commands.servos;

import com.techhounds.frc2016.subsystems.Servos;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetWinchLock extends Command {
	
	private Servos winchLock;
	
	private boolean setMax;
	
    public SetWinchLock() {
    	this(!Servos.getWinchLock().getIsMax());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public SetWinchLock(boolean setMax) {
    	this.setRunWhenDisabled(true);
    	winchLock = Servos.getWinchLock();
    	requires(winchLock);
    	this.setMax = setMax;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	winchLock.set(setMax);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    	end();
    }
}