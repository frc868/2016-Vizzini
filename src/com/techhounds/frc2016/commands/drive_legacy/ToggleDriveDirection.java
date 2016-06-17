package com.techhounds.frc2016.commands.drive_legacy;

import com.techhounds.frc2016.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleDriveDirection extends Command {

    public ToggleDriveDirection() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Drive.isForward){
    		Drive.isForward = false;
    	}else{
    		Drive.isForward = true;
    	}
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
