package com.techhounds.frc2016.commands.gyro;

import com.techhounds.frc2016.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SaveCurrentTilt extends Command {
	
	private GyroSubsystem gyro;

    public SaveCurrentTilt() {
    	gyro = GyroSubsystem.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.storeCurrentTilt();
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
