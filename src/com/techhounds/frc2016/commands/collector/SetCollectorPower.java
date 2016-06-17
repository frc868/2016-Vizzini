package com.techhounds.frc2016.commands.collector;

import com.techhounds.frc2016.subsystems.BeamBreakSubsystem;
import com.techhounds.frc2016.subsystems.CollectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetCollectorPower extends Command {
	
	private CollectorSubsystem collect;
	private BeamBreakSubsystem beam;
	private boolean isShooting;
	private boolean timeOut = false;
	
	private double power;
    public SetCollectorPower(double power) {
    	this(power, false);
    }
    
    public SetCollectorPower(){
    	this(0);
    }
    public SetCollectorPower(double power, double timeOut){
    	
    }
    public SetCollectorPower(double power, boolean isShooting){
     	collect = CollectorSubsystem.getInstance();
    	beam = BeamBreakSubsystem.getInstance();
    	
    	requires(collect);
    	this.power = -power;
    	this.isShooting = isShooting;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	collect.setPower(beam.ballPresent() && power < 0 && !isShooting ? 0 : power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isShooting;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!isShooting)
    	collect.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
