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
	
	public CrossDefense(){
		this(defaultCrossingSpeed);
	}
	
	public CrossDefense(double crossingSpeed){
		 addSequential(new SaveCurrentAngle());
		 addSequential(new DriveDistance(100, crossingSpeed));
		 addSequential(new RotateToPreviousAngle());
    }
}
