package com.techhounds.commands;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeShooterSpeedCommand extends Command {
	
	private ShooterSubsystem launch;
	private double speed;

    public ChangeShooterSpeedCommand(double change) {
    	launch = ShooterSubsystem.getInstance();
    	requires(launch);
    	speed = launch.getPower() + change;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	launch.setPower(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
