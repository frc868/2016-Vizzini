package com.techhounds.commands.gyro;

import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class RotateUsingGyro extends PIDCommand {
	
	private static double p, i, d;
	private DriveSubsystem drive;
	
    public RotateUsingGyro() {
    	super(p, i, d);
    	drive = DriveSubsystem.getInstance();
    	requires(drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
}
