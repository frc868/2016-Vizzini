package com.techhounds.commands.angler;

import com.techhounds.subsystems.AnglerSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetAnglerPosition extends Command {
	
	private AnglerSubsystem angle;
	private Double position;
	

    public SetAnglerPosition(Double position) {
    	angle = AnglerSubsystem.getInstance();
    	requires(angle);
    	this.position = position;
    }
    
    public SetAnglerPosition(){
    	this(null);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(position == null)
    		angle.setPosition(angle.getPosition());
    	else
    		angle.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!angle.onTarget()){
    		//new SetAnglerPosition(position).start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	angle.stopPower();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
