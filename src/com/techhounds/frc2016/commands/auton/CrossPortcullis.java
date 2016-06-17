package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistance;
import com.techhounds.frc2016.commands.gyro.RotateUsingGyro;
import com.techhounds.frc2016.commands.gyro.SaveCurrentAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {
	
    public  CrossPortcullis() {
   
    	addSequential(new SaveCurrentAngle());
    	
    	addParallel(new DriveDistance(-HardwareConstants.Defenses.PORTCULLIS_DISTANCE_1, -HardwareConstants.Defenses.PORTCULLIS_SPEED_1, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
    	addSequential(new SetAnglerPosition(HardwareConstants.Angler.DOWN, 3.0));//Lowers collector to position on ground
    	addSequential(new WaitCommand(.75));
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.UP));//drives through portcullis while raising collector
    	addSequential(new DriveDistance(-HardwareConstants.Defenses.PORTCULLIS_DISTANCE_3, -HardwareConstants.Defenses.PORTCULLIS_SPEED_3, -HardwareConstants.Drive.MIN_STRAIGHT_POWER));
    	addSequential(new RotateUsingGyro(180), 2.5);
   
    }
}
