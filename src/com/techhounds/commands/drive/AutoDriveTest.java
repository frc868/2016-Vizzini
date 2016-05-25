package com.techhounds.commands.drive;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDriveTest extends CommandGroup {

	public AutoDriveTest() {

		addSequential(new DrivePath("0,0","22.6,22.9508","36,0","36,70",1.1,0.5));
		addSequential(new DriveDistanceStraight(45, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, 0.0));
		
	}
}
