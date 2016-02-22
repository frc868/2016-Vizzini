package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.gyro.RotateToPreviousAngle;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.commands.gyro.SaveCurrentAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossCDF extends CommandGroup {
    
    public  CrossCDF(boolean isFacingForward) {
    	
    	//this command assumes the robot is facing forward in front of the defense
    	double direction = 1;
    	addSequential(new SaveCurrentAngle());//saves angle
    	
    	if(isFacingForward){
    		addSequential(new RotateUsingGyro(180));//turns around
    		direction = -1;
    	}
    	
    	addSequential(new DriveDistance(direction * 90, .5));//drives up on to ramp to its position
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN));//lowers collector onto defense
    	
    	addSequential(new WaitCommand(.2));//waits until defense is lowered
    	
    	addParallel(new DriveDistance(direction * 55, .7));//drives over defense and waits a moment before...
    	addSequential(new WaitCommand(.1));
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));//...raising collector to prevent damage to it
    	
    	addSequential(new RotateToPreviousAngle());
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
    
    public CrossCDF(){
    	this(true);
    }
}
