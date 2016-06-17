package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.RobotMap;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistance;
import com.techhounds.frc2016.commands.gyro.RotateToPreviousAngle;
import com.techhounds.frc2016.commands.gyro.RotateUsingGyro;
import com.techhounds.frc2016.commands.gyro.SaveCurrentAngle;
import com.techhounds.frc2016.commands.shooter.SetShooterSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossCDF extends CommandGroup {
    
    public  CrossCDF() {
    	
    	addSequential(new SaveCurrentAngle());//saves angle
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	addSequential(new DriveDistance(-RobotMap.Defenses.CDF_DISTANCE_1, -RobotMap.Defenses.CDF_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
    	addSequential(new DriveDistance(6, RobotMap.Defenses.CDF_SPEED_1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 1));
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN), .5);//lowers collector onto defense
    	
    	addParallel(new DriveDistance(-RobotMap.Defenses.CDF_DISTANCE_2 - 6, -RobotMap.Defenses.CDF_SPEED_2, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives over defense and waits a moment before...
    	addSequential(new AnglerWithDelay(.5), 2);//...raising collector to prevent damage to it
    	addSequential(new WaitCommand(0.25));
    	addSequential(new RotateUsingGyro(180, 2, 0));
    }
}