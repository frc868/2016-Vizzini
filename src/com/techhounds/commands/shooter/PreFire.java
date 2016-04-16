package com.techhounds.commands.shooter;

import com.techhounds.commands.CreateRumble;
import com.techhounds.commands.SetRumble;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PreFire extends CommandGroup {

	public PreFire(double shooterSpeed) {

		addParallel(new SetCollectorPower(-.4, true));
		addSequential(new WaitForBeanBreak(false), .25);
		//addSequential(new SetCollectorPower());
		//addSequential(new WaitCommand(1));
		//addParallel(new SetCollectorPower(.4, true));
		//addParallel(new WaitForBeanBreak(true));
		//addSequential(new WaitCommand(.01));
		addParallel(new SetCollectorPower(.4, false));
		addSequential(new WaitForBeanBreak(true),.25);
		addSequential(new SetCollectorPower(0, true));
		addSequential(new SetShooterSpeed(shooterSpeed));
		addSequential(new WaitForShooterReady(3, true));
		//addSequential(new CreateRumble());
	}
	
	public PreFire() {
		this(69);
	}
}
