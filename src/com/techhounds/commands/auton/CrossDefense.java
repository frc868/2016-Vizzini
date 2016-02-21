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
    		addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	}else{
    		addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	}
    	addSequential(new DriveDistance(4, crossingSpeed));//Need to work on this using gyros to allow for accuracy in distance
    	
        
    }
}
