package com.techhounds.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Debug extends Command {

    private String label;
	private String value;

	public Debug(String label, String value) {
    	this.label = label;
    	this.value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString(label, value);
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
    }
}
