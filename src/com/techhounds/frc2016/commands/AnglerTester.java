package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.subsystems.AnglerSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AnglerTester extends Command {
	
	private AnglerSubsystem angler;
	private static String positionKey = "Angler_Position";
	private double position = .5;
    public AnglerTester() {
    	angler = AnglerSubsystem.getInstance();
    	requires(angler);
    	SmartDashboard.putNumber(positionKey, position);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	position = SmartDashboard.getNumber(positionKey, position);
    	angler.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    	end();
    }
}
