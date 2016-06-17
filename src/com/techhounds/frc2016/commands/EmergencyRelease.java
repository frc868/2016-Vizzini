package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.commands.angler.SetStateUp;
import com.techhounds.frc2016.commands.collector.SetCollectorPower;
import com.techhounds.frc2016.commands.shooter.SetShooterPower;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class EmergencyRelease extends CommandGroup {
    
	private Command stateUp, shooter, collector;
	
    public  EmergencyRelease() {
    	addSequential(new SetStateUp());
        addParallel(new SetShooterPower(-1));
        addSequential(new SetCollectorPower(-1, true));
        
    }
    
    @Override
    public void end() {
    	//new SetShooterPower(0).start();
    	//new SetCollectorPower(0).start();
    }
}
