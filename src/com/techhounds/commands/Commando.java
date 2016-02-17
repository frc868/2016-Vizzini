package com.techhounds.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Commando extends Command {

private Timer timer;
	
    public Commando() {
    	timer = new Timer();
    }

    protected void initialize() {
    	timer.reset();
    	timer.start();
    }

    int cnt = 0;
    
    protected void execute() {
    	
    	if(timer.get() >= 0.3) {
    		SmartDashboard.putNumber("counted", cnt);
    		cnt++;
    		timer.reset();
    	}
    
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
    }
}
