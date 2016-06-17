package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.HardwareConstants;
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
    	addSequential(new SetAnglerPosition(HardwareConstants.Angler.UP));
    	addSequential(new DriveDistance(-HardwareConstants.Defenses.CDF_DISTANCE_1, -HardwareConstants.Defenses.CDF_SPEED_1, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
    	addSequential(new DriveDistance(6, HardwareConstants.Defenses.CDF_SPEED_1, HardwareConstants.Drive.MIN_STRAIGHT_POWER, 1));
    	addSequential(new SetAnglerPosition(HardwareConstants.Angler.DOWN), .5);//lowers collector onto defense
    	
    	addParallel(new DriveDistance(-HardwareConstants.Defenses.CDF_DISTANCE_2 - 6, -HardwareConstants.Defenses.CDF_SPEED_2, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 3));//drives over defense and waits a moment before...
    	addSequential(new AnglerWithDelay(.5), 2);//...raising collector to prevent damage to it
    	addSequential(new WaitCommand(0.25));
    	addSequential(new RotateUsingGyro(180, 2, 0));
    }
}
