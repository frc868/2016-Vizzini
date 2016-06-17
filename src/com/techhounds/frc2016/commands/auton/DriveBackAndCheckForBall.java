package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.commands.collector.WaitForBeanBreak;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistance;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistanceStraight;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveBackAndCheckForBall extends CommandGroup {
	
	private Command drive, ballCheck;
	
	public DriveBackAndCheckForBall() {
		addParallel(drive = new DriveDistance(-85, -HardwareConstants.Defenses.LOW_BAR_SPEED, -HardwareConstants.Defenses.LOW_BAR_SPEED + .1, 3));
		addSequential(ballCheck = new WaitForBeanBreak(true));
	}
	public DriveBackAndCheckForBall(double stopDistance){
		addParallel(drive = new DriveDistanceStraight(stopDistance, -HardwareConstants.Defenses.LOW_BAR_SPEED + .1, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true, 0.25));
		addSequential(ballCheck = new WaitForBeanBreak(true));
	}
	public DriveBackAndCheckForBall(double stopDistance, double maxSpeed){
		addParallel(drive = new DriveDistanceStraight(stopDistance, maxSpeed, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));
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
	}
}
