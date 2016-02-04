package com.techhounds.commands;

import java.util.List;

import com.techhounds.subsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateSmartDashboard extends Command {

	private Timer timer;
	
    public UpdateSmartDashboard() {
    	setRunWhenDisabled(true);
    	timer = new Timer();
    }

    protected void initialize() {
    	timer.reset();
    	timer.start();
    }

    protected void execute() {
    	
    	if(timer.get() >= 0.3) {
	    	List<BasicSubsystem> subsystems = BasicSubsystem.getSubsystems();
	    	
	    	for(BasicSubsystem subsystem : subsystems)
	    		subsystem.updateSmartDashboard();
	    	timer.reset();
	    	SmartDashboard.putNumber("TIME", timer.get());
		}
    }

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
