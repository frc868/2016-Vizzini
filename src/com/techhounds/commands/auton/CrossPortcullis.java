package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {
	
    public  CrossPortcullis() {
    	
    	//This command assumes that the robot has it's first rear wheels on ramp and is in default collector position
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_MIN_HEIGHT));//Lowers collector to position below Portcullis
    	
    	addSequential(new DriveDistance(.5, .4));//drives forward exactly to where collector is in position below Portcullis
    	
    	addParallel(new DriveDistance(3, .4));//drives forward through defense
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_HEIGHT));//simultaneously raises collector up, opening the Portcullis
    	
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
