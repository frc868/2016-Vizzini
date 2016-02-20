package com.techhounds.commands.shooter;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetShooterSpeed extends Command {

	private double speed;
	private ShooterSubsystem shooter;
	
    public SetShooterSpeed() {
    	shooter = ShooterSubsystem.getInstance();
    	requires(shooter);
   
    }

    // Called just before this Command runs the first time
    protected void initialize() {
     	this.speed = SmartDashboard.getNumber("Shooter Set Speed");
    	shooter.setSpeed(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shooter.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Locks pid power, because PID is disabled in setPower
    	shooter.setPower(shooter.getPower());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
