package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.gyro.RotateToLastAngle;
import com.techhounds.commands.gyro.RotateToPreviousAngle;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.commands.gyro.SaveCurrentAngle;
import com.techhounds.commands.shooter.AlignUsingVision;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.SetShooterSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TwoBallFromLine extends CommandGroup {
    
    public  TwoBallFromLine() {
    	
    	// GET BALL FROM MIDLINE
    	addParallel(new SaveCurrentAngle());
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addParallel(new SetCollectorPower(RobotMap.Collector.inPower));
    	addSequential(new WaitForBeanBreak(true));
    	addParallel(new SetCollectorPower(0));
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	
    	// GET POSITION TO DRIVE LOW BAR
    	addSequential(new RotateUsingGyro(-40));
    	addSequential(new WaitCommand(.1));
    	addSequential(new DriveDistance(45));
    	addSequential(new WaitCommand(.1));
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addSequential(new RotateToLastAngle(-2));

    	// DRIVE LOW BAR
    	addSequential(new WaitCommand(.1));
    	addSequential(new DriveDistance(RobotMap.Defenses.LOW_BAR_DISTANCE - 24, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5));
    	// ^^ ALL GOOD
    	
    	rotateAlignShoot();
    	
		// ROTATE BACK TO INITIAL ANGLE
		addSequential(new RotateToLastAngle(5));
		addSequential(new WaitCommand(.1));
		
		// DRIVE BACK AND CHECK FOR BALL
		addParallel(new SetCollectorPower(RobotMap.Collector.inPower));
		addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
		addSequential(new DriveBackAndCheckForBall(-RobotMap.Defenses.LOW_BAR_DISTANCE + 18));
		
		// STOP COLLCETOR
		addSequential(new WaitCommand(.1));
		addParallel(new SetCollectorPower(0, true));
		
		// DRIVE BACK
		addSequential(new DriveDistance(RobotMap.Defenses.LOW_BAR_DISTANCE, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5));
		
		rotateAlignShoot();
    }
    
    public void rotateAlignShoot() {
    	
    	// SET SHOOTER AND ROTATE 60
    	addSequential(new SaveCurrentAngle());
    	addSequential(new WaitCommand(.3));
    	
    	addParallel(new SetShooterSpeed(69));
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	addSequential(new RotateUsingGyro(65));
    	// ALL GOOD ^^^
    	
    	// ALIGN USING VISION
    	addSequential(new WaitCommand(.1));
    	addSequential(new RotateUsingVisionContinuous(2));
    	
    	// SHOOT
    	addSequential(new SetCollectorPower(1, true));
		addSequential(new WaitForBeanBreak(false), .75);
		addParallel(new SetCollectorPower(0, true));
		addParallel(new SetShooterPower());
		addSequential(new WaitCommand(.3));
	
    }
}
