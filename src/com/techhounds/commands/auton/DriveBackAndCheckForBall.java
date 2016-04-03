package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.collector.WaitForBeanBreak;
import com.techhounds.commands.drive.DriveDistance;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveBackAndCheckForBall extends CommandGroup {
	
	private Command drive, ballCheck;
	
	public DriveBackAndCheckForBall() {
		addParallel(drive = new DriveDistance(-85, -RobotMap.Defenses.LOW_BAR_SPEED, -RobotMap.Defenses.LOW_BAR_SPEED + .1, 3));
		addSequential(ballCheck = new WaitForBeanBreak(true));
	}
	public DriveBackAndCheckForBall(double stopDistance){
		addParallel(drive = new DriveDistance(stopDistance, -.6, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));
		addSequential(ballCheck = new WaitForBeanBreak(true));
	}

	@Override
	protected boolean isFinished() {
		if(ballCheck != null && drive != null) {
			if(!ballCheck.isRunning()) {
				return true;
			} else if(!drive.isRunning()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public void end() {
		drive.cancel();
		ballCheck.cancel();
	}
}
