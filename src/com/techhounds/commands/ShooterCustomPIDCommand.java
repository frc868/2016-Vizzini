package com.techhounds.commands;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterCustomPIDCommand extends Command {
	
	private ShooterSubsystem shoot;
	private double setPow;
	private double curPow = 0;
	private double newPow = 0;
	private double difPow = 0;
	
    public ShooterCustomPIDCommand(double set) {
    	shoot = ShooterSubsystem.getInstance();
    	requires(shoot);
    	setPow = set;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	difPow = setPow-curPow;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shoot.stopPower();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
