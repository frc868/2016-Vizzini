package com.techhounds.commands;

import com.techhounds.OI;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.subsystems.*;

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
    	
    	if(timer.get() >= 1/200.0) {
        	
    		ShooterSubsystem.getInstance().updateSmartDashboard();
    		//CollectorSubsystem.getInstance().updateSmartDashboard();
    		AnglerSubsystem.getInstance().updateSmartDashboard();
    		DriveSubsystem.getInstance().updateSmartDashboard();
    		BeamBreakSubsystem.getInstance().updateSmartDashboard();
    		//AutonChooser.getInstance().updateSmartDashboard();
    		OI.getInstance().updateDashboard();
    		GyroSubsystem.getInstance().updateSmartDashboard();
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
