package com.techhounds.frc2016.commands.angler;

import com.techhounds.frc2016.subsystems.Angler;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetAnglerPower extends Command {
	
	private Angler angler;
	private double power;

    public SetAnglerPower(double power) {
    	angler = Angler.getInstance();
    	requires(angler);
    	this.power = power;
    }
    
    public SetAnglerPower(){
    	this(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angler.setPower(power);
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
