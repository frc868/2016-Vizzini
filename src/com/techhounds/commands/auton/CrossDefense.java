package com.techhounds.commands.auton;

import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.gyro.RotateToPreviousAngle;
import com.techhounds.commands.gyro.SaveCurrentAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossDefense extends CommandGroup {
	
	//This command assumes the robot is in front of the defense, and only needs to drive forward over the obstacle
	
	private static double defaultCrossingSpeed = .3;
	private static double defaultDistance = 139.5;
	
	public CrossDefense(){
		this(defaultDistance, defaultCrossingSpeed);
	}
	
	public CrossDefense(double crossingSpeed, boolean isDifferentCrossingSpeed){
		this(defaultDistance, crossingSpeed);
	}
	
	public CrossDefense(double distance){
		this(distance, defaultCrossingSpeed);
	}
	
	public CrossDefense(double distance, double crossingSpeed){
		 addSequential(new SaveCurrentAngle());
		 addSequential(new DriveDistance(distance, crossingSpeed));
		 addSequential(new RotateToPreviousAngle());
    }
}
