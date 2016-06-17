package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.commands._experimental.DriveTrajectory;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonTest extends CommandGroup {

	public AutonTest() {
		addSequential(new DriveTrajectory(0));
		addSequential(new WaitCommand(3));
		addSequential(new DriveTrajectory(1));
		addSequential(new WaitCommand(3));
		addSequential(new DriveTrajectory(2));
	}
}
