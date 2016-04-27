package com.techhounds.commands.drive;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveUntilTiltPatternWithDistance extends CommandGroup {

	private Command tiltCommand, driveCommand;
	
	public DriveUntilTiltPatternWithDistance(double distance, double maxPower, double minPower, double timeOut) {
		
		if(distance > 0) {
			addParallel(tiltCommand = new WaitForTiltPattern(WaitForTiltPattern.DEFENSE_CROSS_FORWARD, 
					WaitForTiltPattern.DEFENSE_CROSS_TIMEOUTS, 5));
		} else {
			addParallel(tiltCommand = new WaitForTiltPattern(WaitForTiltPattern.DEFENSE_CROSS_BACKWARD, 
					WaitForTiltPattern.DEFENSE_CROSS_TIMEOUTS, 5));
		}
		
		addParallel(driveCommand = new DriveDistance(distance, maxPower, minPower, timeOut));
	}
	
	public DriveUntilTiltPatternWithDistance(double distance, double maxPower, double minPower, double timeOut, WaitForTiltPattern.Motion [] motion, Double [] timeout) {
		addParallel(tiltCommand = new WaitForTiltPattern(motion, timeout, 5));
		addParallel(driveCommand = new DriveDistanceStraight(distance, maxPower, minPower, timeOut));
	}
	
	public DriveUntilTiltPatternWithDistance(double distance, double maxPower, double minPower, double timeOut, WaitForTiltPattern.Motion [] motion, Double [] timeout, double angleThreshold) {
		addParallel(tiltCommand = new WaitForTiltPattern(motion, timeout, angleThreshold));
		addParallel(driveCommand = new DriveDistanceStraight(distance, maxPower, minPower, timeOut));
	}
	
	@Override
	public boolean isFinished() {
		if(!tiltCommand.isRunning() || !driveCommand.isRunning()) {
			return true;
		} else {
			return super.isFinished();
		}
	}
}