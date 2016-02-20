package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.angler.SetAnglerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossDefense extends CommandGroup {
	
	//This command assumes the robot is in front of the defense, and only needs to drive forward over the obstacle
	
	private static double defaultCrossingSpeed = .3;
	//Collector position will be set to the up position by default
	
	public CrossDefense(){
		this(defaultCrossingSpeed);
	}
	
	public CrossDefense(double crossingSpeed){
		this(crossingSpeed, false);
	}
    
    public  CrossDefense(double crossingSpeed, boolean anglerShouldBeDown) {
    	if(anglerShouldBeDown){
    		addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_HEIGHT));
    	}else{
    		addSequential(new SetAnglerPosition(RobotMap.Collector.DEFENSE_PASS_HEIGHT));
    	}
    	addSequential(new DriveDistance(4, crossingSpeed));//Need to work on this using gyros to allow for accuracy in distance
    	
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
