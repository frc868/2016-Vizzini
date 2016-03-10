package com.techhounds.commands;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.servos.*;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.subsystems.AnglerSubsystem;
import com.techhounds.subsystems.DriveSubsystem;

/**
 *
 */
public class MatchSetup extends CommandGroup {
    
    public  MatchSetup() {
    	DriveSubsystem.isForward = true;
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	AnglerSubsystem.getInstance().defaultState();
    	addParallel(new SetCollectorPower());
    	addParallel(new SetShooterPower());
    	//addParallel(new SetScissorsOne(RobotMap.Servo.SCISSOR_ONE_IS_UP_DEFAULT));
    	//addParallel(new SetScissorsTwo(RobotMap.Servo.SCISSOR_TWO_IS_UP_DEFAULT));
    	addParallel(new SetWinchEnable(RobotMap.Servo.WINCH_ENABLE_IS_UP_DEFAULT));
    	//addParallel(new SetWinchLock(RobotMap.Servo.WINCH_LOCK_IS_UP_DEFAULT));
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
