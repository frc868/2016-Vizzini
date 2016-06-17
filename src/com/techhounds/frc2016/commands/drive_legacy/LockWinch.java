package com.techhounds.frc2016.commands.drive_legacy;

import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.ServoSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LockWinch extends Command {

	ServoSubsystem servo;
	private Boolean toLock;
	
	public LockWinch() {
		servo = ServoSubsystem.getWinchLock();
		requires(servo);
	}
	
	public LockWinch(boolean toLock) {
		this();
		this.toLock = toLock;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	servo.set(toLock == null ? !ServoSubsystem.getWinchLock().getIsMax() : toLock);
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
    	end();
    }
}
