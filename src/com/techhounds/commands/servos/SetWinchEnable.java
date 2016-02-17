package com.techhounds.commands.servos;

import com.techhounds.RobotMap;
import com.techhounds.subsystems.ServoSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetWinchEnable extends Command {
	
	private ServoSubsystem servo;
	private double max = RobotMap.Servo.WINCH_ENABLE_MAX;
	private double min = RobotMap.Servo.WINCH_ENABLE_MIN;

    public SetWinchEnable() {
    	servo = ServoSubsystem.getWinchEnable();
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
