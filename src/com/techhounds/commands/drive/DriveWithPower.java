package com.techhounds.commands.drive;

import com.techhounds.OI;
import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithPower extends Command {
	
	DriveSubsystem drive;

    public DriveWithPower() {
    	drive = DriveSubsystem.getInstance();
    	requires(drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(DriveSubsystem.isForward){
    		drive.setPower(OI.getInstance().getRightBackward(), OI.getInstance().getLeftBackward());
    	}else{
    		drive.setPower(OI.getInstance().getRightForward(), OI.getInstance().getLeftForward());
        	
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
