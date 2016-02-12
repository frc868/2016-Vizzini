package com.techhounds.commands;

import com.techhounds.subsystems.CollectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorCommand extends Command {
	
	private CollectorSubsystem collect;
	private double colPower;

    public CollectorCommand(double power) {
    	collect = CollectorSubsystem.getInstance();
    	requires(collect);
    	colPower = power;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public CollectorCommand(){
    	this(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	collect.setPower(colPower);
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
    	collect.stopPower();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
