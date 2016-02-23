package com.techhounds.commands.shooter;

import com.techhounds.commands.collector.SetCollectorPower;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Fire extends CommandGroup {

	public Fire() {
		addSequential(new SetCollectorPower(.6, true));
		addSequential(new WaitCommand(.5));
		addSequential(new SetCollectorPower(0));
		addSequential(new SetShooterPower());
		
	}
}
