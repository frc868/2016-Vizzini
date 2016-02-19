package com.techhounds.commands.servos;

import com.techhounds.subsystems.ServoSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class SetScissorsOne extends Command {
	
	private ServoSubsystem servo;
	
	private boolean setMax;

    public SetScissorsOne() {
    	servo = ServoSubsystem.getScissorOne();
    	requires(servo);
    	LiveWindow.addActuator("Scissor_One", "Servo", servo.getServo());
    	setMax = !servo.getIsMax();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public SetScissorsOne(boolean setMax){
    	servo = ServoSubsystem.getScissorOne();
    	requires(servo);
    	LiveWindow.addActuator("Scissor_One", "Servo", servo.getServo());
    	this.setMax = setMax;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	servo.set(setMax);
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
