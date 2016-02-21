package com.techhounds.commands.auton;

import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionRotateToTarget extends Command {

	private double maxAngle = 0.5;
	private double lastFrame = 0;
	DriveSubsystem drive;
	RotateUsingGyro rotateCommand;
	private boolean done = false;
	
    public VisionRotateToTarget() {
    	drive = DriveSubsystem.getInstance();
    	requires(drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastFrame = SmartDashboard.getNumber("FrameCount", -1);
    	rotateCommand = null;
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double frame = SmartDashboard.getNumber("FrameCount", -1);
    	
    	if(rotateCommand != null) {
    		if(!rotateCommand.isRunning()){
    			done = true;
    			rotateCommand = null;
    		}
    		
    	}else if(frame != lastFrame){
    		double anglesOffCenter = -SmartDashboard.getNumber("OffCenterDegreesX");
    		lastFrame = frame;
    		rotateCommand = new RotateUsingGyro(anglesOffCenter);
    		rotateCommand.start();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
