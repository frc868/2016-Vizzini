package com.techhounds.frc2016.commands.collector;

import com.techhounds.frc2016.RobotMap;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetCollector extends CommandGroup {

	public SetCollector(double power) {
		
		if(power > 0) {
			addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
		} else if(power < 0){
			addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING + 5));
		} else {
			
		}
		
		addParallel(new SetCollectorPower(power));
	}
}
