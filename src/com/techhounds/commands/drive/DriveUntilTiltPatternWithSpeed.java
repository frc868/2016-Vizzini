package com.techhounds.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveUntilTiltPatternWithSpeed extends CommandGroup {

	private Command tiltCommand;
	
	public DriveUntilTiltPatternWithSpeed(double speed) {
		
		if(speed > 0) {
			addParallel(tiltCommand = new WaitForTiltPattern(WaitForTiltPattern.DEFENSE_CROSS_FORWARD, 
					WaitForTiltPattern.DEFENSE_CROSS_TIMEOUTS, 5));
		} else {
			addParallel(tiltCommand = new WaitForTiltPattern(WaitForTiltPattern.DEFENSE_CROSS_BACKWARD, 
					WaitForTiltPattern.DEFENSE_CROSS_TIMEOUTS, 5));
		}
		
		addParallel(new DriveWithSpeed(speed));
	}
	
	public DriveUntilTiltPatternWithSpeed(double speed, WaitForTiltPattern.Motion [] motion, Double [] timeout) {
		addParallel(tiltCommand = new WaitForTiltPattern(motion, timeout, 5));
		addParallel(new DriveWithSpeed(speed));
	}
	
	public DriveUntilTiltPatternWithSpeed(double speed, WaitForTiltPattern.Motion [] motion, Double [] timeout, double angleThreshold) {
		addParallel(tiltCommand = new WaitForTiltPattern(motion, timeout, angleThreshold));
		addParallel(new DriveWithSpeed(speed));
	}
	
	@Override
	public boolean isFinished() {
		if(!tiltCommand.isRunning()) {
			return true;
		} else {
			return super.isFinished();
		}
	}
}