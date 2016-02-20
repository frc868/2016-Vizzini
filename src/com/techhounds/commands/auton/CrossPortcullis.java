package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {
	
    public  CrossPortcullis() {
    	
    	//This command assumes the robot is immediately before the Portcullis and is in default positions
    	
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_MIN_HEIGHT));//Lowers Collector to ground where it can go under the Portcullis
    	addSequential(new WaitCommand(1));//Waits this amount of time after Collector begins moving before initiating the next command
    	
    	addParallel(new DriveDistance(1, .3));//Then drives forward this amount to have the Collector be exactly under the Portcullis
    	
    	addSequential(new WaitCommand(3));//Waits until it is in the correct position, possible to remove this later?
    	
    	addParallel(new DriveDistance(3, .3));//Drives forward through the Portcullis
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_HEIGHT));//At the same time raises the Collector up to open the Portcullis
    	
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
