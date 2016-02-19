package com.techhounds.commands.servos;

import com.techhounds.subsystems.ServoSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class SetWinchEnable extends Command {
	
	private ServoSubsystem winchEnable;
	
	private boolean setMax;

    public SetWinchEnable() {
    	winchEnable = ServoSubsystem.getWinchEnable();
    	requires(winchEnable);
    	LiveWindow.addActuator("Winch_Enable", "Servo", winchEnable.getServo());
    	setMax = !winchEnable.getIsMax();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public SetWinchEnable(boolean setMax) {
    	winchEnable = ServoSubsystem.getWinchEnable();
    	requires(winchEnable);
    	LiveWindow.addActuator("Winch_Enable", "Servo", winchEnable.getServo());
    	this.setMax = setMax;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	winchEnable.set(setMax);
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
