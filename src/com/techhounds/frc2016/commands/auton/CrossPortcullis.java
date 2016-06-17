package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.RobotMap;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistance;
import com.techhounds.frc2016.commands.gyro.RotateToPreviousAngle;
import com.techhounds.frc2016.commands.gyro.RotateUsingGyro;
import com.techhounds.frc2016.commands.gyro.SaveCurrentAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {
	
    public  CrossPortcullis() {
    	
    	//this command assumes the robot is facing forward in front of the defense
    	addSequential(new SaveCurrentAngle());
    	
    	addParallel(new DriveDistance(-RobotMap.Defenses.PORTCULLIS_DISTANCE_1, -RobotMap.Defenses.PORTCULLIS_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN, 3.0));//Lowers collector to position on ground
    	addSequential(new WaitCommand(.75));
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));//drives through portcullis while raising collector
    	addSequential(new DriveDistance(-RobotMap.Defenses.PORTCULLIS_DISTANCE_3, -RobotMap.Defenses.PORTCULLIS_SPEED_3, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER));
    	addSequential(new RotateUsingGyro(180), 2.5);
    	
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
