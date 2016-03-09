package com.techhounds.commands.collector;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetCollector extends CommandGroup {

	public SetCollector(double power) {
		
		if(power > 0) {
			addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
		} else if(power < 0){
			addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING + 5));
		} else {
			
		}
		
		addSequential(new SetCollectorPower(power));
	}
}