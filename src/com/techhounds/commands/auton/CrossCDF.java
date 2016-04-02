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
    
    public  CrossCDF() {
    	
    	addSequential(new SaveCurrentAngle());//saves angle
    	
    	addSequential(new DriveDistance(-RobotMap.Defenses.CDF_DISTANCE_1, -RobotMap.Defenses.CDF_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN), 2);//lowers collector onto defense
    	
    	addSequential(new WaitCommand(.25));//waits until defense is lowered
    	
    	addParallel(new DriveDistance(-RobotMap.Defenses.CDF_DISTANCE_2 - 6, -RobotMap.Defenses.CDF_SPEED_2, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives over defense and waits a moment before...
    	addSequential(new AnglerWithDelay(.5), 2);//...raising collector to prevent damage to it
    	addSequential(new WaitCommand(1.5));
    	addSequential(new RotateUsingGyro(180, 2, 0));
    }
}
