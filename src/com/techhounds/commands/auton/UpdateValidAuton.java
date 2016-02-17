package com.techhounds.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class UpdateValidAuton extends Command {

	private Timer timer;
	private AutonChooser chooser;
	
    public UpdateValidAuton() {
    	setRunWhenDisabled(true);
    	chooser = AutonChooser.getInstance();
    	timer = new Timer();
    }

    protected void initialize() {
    	timer.reset();
    	timer.start();
    }

    protected void execute() {
    	
    	if(timer.get() >= 0.3) {
    		SmartDashboard.putBoolean("Valid Auton", chooser.isValid());
	    	timer.reset();
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
