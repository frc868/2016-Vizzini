package com.techhounds.commands;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Basic command, still on subsystem level, this is likely just a placeholder for now.
 */
public class ShooterCommand extends Command {
	
	private ShooterSubsystem shooter;
	private double power = 0;

    public ShooterCommand() {
    	shooter = ShooterSubsystem.getInstance();
    	requires(shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public ShooterCommand(double power){
    	shooter = ShooterSubsystem.getInstance();
    	requires(shooter);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.setPower(power);
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
    	shooter.stopPower();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
