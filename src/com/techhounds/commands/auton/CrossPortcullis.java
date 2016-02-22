package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.gyro.RotateUsingGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {
	
    public  CrossPortcullis() {
    	
    	//this command assumes the robot is facing forward in front of the defense
    	
    	addSequential(new RotateUsingGyro(180));//turns around
    	
    	addSequential(new DriveDistance(-1, .5));//drives up on to ramp to its position
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN));//Lowers collector to position on ground
    	
    	addSequential(new DriveDistance(-1, .3));//drives to position collector under portcullis
    	
    	addParallel(new DriveDistance(-3, .5));//drives through portcullis while raising collector
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	
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
