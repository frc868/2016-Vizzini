package com.techhounds.commands.shooter;

import com.techhounds.commands.SetRumble;
import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetShooterSpeed extends Command {

	private double speed;
	private ShooterSubsystem shooter;
	private int cnt;
	
    public SetShooterSpeed(double speed) {
    	this.speed = speed;
    	shooter = ShooterSubsystem.getInstance();
    	requires(shooter);
   
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.setSpeed(speed);
    	cnt = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    private Long currTime;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(shooter.onTarget())
    		cnt++;
    	else
    		cnt = 0;
    	
        return cnt >= 3;
        /*
    	if(shooter.onTarget()) {
    		if(currTime == null)
    			currTime = System.currentTimeMillis();
    		if(System.currentTimeMillis() - currTime > 1000) {
    			SmartDashboard.putNumber("Time To Get To Speed", timeSinceInitialized());
    			return true;
    		}
    	} else {
    		currTime = null;
    	}
    	
		return false;*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Locks pid power, because PID is disabled in setPower
    	//shooter.setPower(shooter.getPower());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
