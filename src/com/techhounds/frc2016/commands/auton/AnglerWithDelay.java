package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.RobotMap;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AnglerWithDelay extends CommandGroup {

	public AnglerWithDelay(double time) {
		addSequential(new WaitCommand(time));
		addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
	}
}
