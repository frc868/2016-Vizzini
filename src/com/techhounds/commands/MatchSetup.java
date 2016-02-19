package com.techhounds.commands;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.servos.*;
import com.techhounds.commands.shooter.SetShooterPower;

/**
 *
 */
public class MatchSetup extends CommandGroup {
    
    public  MatchSetup() {
    	addParallel(new SetAnglerPosition(RobotMap.Collector.ANGLER_FORWARD_LIMIT));
    	addParallel(new SetCollectorPower());
    	addParallel(new SetShooterPower());
    	addParallel(new SetScissorsOne(false));
    	addParallel(new SetScissorsTwo(false));
    	addParallel(new SetWinchEnable(false));
    	addParallel(new SetWinchLock(false));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
