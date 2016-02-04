package com.techhounds.commands;

import java.util.List;

import com.techhounds.subsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateSmartDashboard extends Command {

    public UpdateSmartDashboard() {
    	setRunWhenDisabled(true);
    }

    protected void initialize() {
    }

    protected void execute() {
    	List<BasicSubsystem> subsystems = BasicSubsystem.getSubsystems();
    	
    	for(BasicSubsystem subsystem : subsystems)
    		subsystem.updateSmartDashboard();
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
