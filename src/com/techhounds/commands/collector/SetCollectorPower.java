package com.techhounds.commands.collector;

import com.techhounds.subsystems.CollectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetCollectorPower extends Command {
	
	private CollectorSubsystem collect;
	private double power;

    public SetCollectorPower(double power) {
    	collect = CollectorSubsystem.getInstance();
    	requires(collect);
    	this.power = power;
    }
    
    public SetCollectorPower(){
    	this(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	collect.setPower(power);
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
