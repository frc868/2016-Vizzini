package com.techhounds.commands.collector;

import com.techhounds.subsystems.BeamBreakSubsystem;
import com.techhounds.subsystems.CollectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetCollectorPower extends Command {
	
	private CollectorSubsystem collect;
	private BeamBreakSubsystem beam;
	private boolean isShooting;
	
	private double power;

    public SetCollectorPower(double power) {
    	this(power, false);
    }
    
    public SetCollectorPower(){
    	this(0);
    }
    public SetCollectorPower(double power, boolean isShooting){
     	collect = CollectorSubsystem.getInstance();
    	beam = BeamBreakSubsystem.getInstance();
    	
    	requires(collect);
    	this.power = power;
    	this.isShooting = isShooting;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(beam.ballPresent() && power > 0 && !isShooting){
    		collect.setPower(0);
    	}else{
    		collect.setPower(power);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	collect.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
