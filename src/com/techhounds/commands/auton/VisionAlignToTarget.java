package com.techhounds.commands.auton;

import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionAlignToTarget extends Command implements PIDSource, PIDOutput {

	private DriveSubsystem drive;
	private double offAngle = 0;
	private PIDController pid;
	private NetworkTable netTable;
	
    public VisionAlignToTarget() {
        drive = DriveSubsystem.getInstance();
        requires(drive);
        
        pid = new PIDController(0, 0, 0, this, this);
        pid.setOutputRange(-1, 1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	NetworkTable.setServerMode();
    	NetworkTable.initialize();
    	netTable = NetworkTable.getTable("SmartDashboard");
    	
    	if(netTable != null) {
    		offAngle = netTable.getNumber("OffCenterDegreesX", 0);
    	}
    	
    	pid.setSetpoint(offAngle);
    	pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pid.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	public void pidWrite(double output) {
		drive.setLeftPower(-output);
		drive.setRightPower(output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		return offAngle;
	}
	
	/*
	 * if angle is positive,
	 * we need to rotate to the left (counterclockwise)
	 * thus left power should be negative, right power should be positive
	 */
}
