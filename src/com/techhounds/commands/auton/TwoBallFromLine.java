package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.drive.DriveDistanceStraight;
import com.techhounds.commands.drive.DriveOneSideDistance;
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
    	
		CommandGroup adjBall = new CommandGroup();
		adjBall.addSequential(new WaitCommand(0.5));
		adjBall.addParallel(new SetCollectorPower(-.4, true));
		adjBall.addSequential(new WaitForBeanBreak(false), .25);
		adjBall.addParallel(new SetCollectorPower(.4, false));
		adjBall.addSequential(new WaitForBeanBreak(true),.25);
		adjBall.addSequential(new SetShooterSpeed(72.5));
		
		CommandGroup adjBall2 = new CommandGroup();
		adjBall2.addParallel(new SetCollectorPower(-.4, true));
		adjBall2.addSequential(new WaitForBeanBreak(false), .25);
		adjBall2.addParallel(new SetCollectorPower(.4, false));
		adjBall2.addSequential(new WaitForBeanBreak(true),.25);

    	// GET BALL FROM MIDLINE
    	addParallel(new SaveCurrentAngle());
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addParallel(new SetCollectorPower(0.8));
    	addSequential(new WaitForBeanBreak(true), .75);
    	addParallel(new SetCollectorPower(0));
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	
    	// GET POSITION TO DRIVE LOW BAR
    	//addSequential(new RotateUsingGyro(-40, 1, true));
    	addSequential(new DriveOneSideDistance(45, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, false));
    	addSequential(new WaitCommand(.05));
    	
    	addParallel(adjBall2);
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addSequential(new DriveOneSideDistance(45, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, true));
    	if(true)
    		return;
    	/* Proposed New Low Bar
    	addSequential(new RotateUsingGyro(-20));
    	addSequential(new WaitCommand(.1);
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addSequential(new DriveDistance(50));
    	addSequential(new RotateToLastAngle());
    	*/
    	
    	// DRIVE LOW BAR
    	addSequential(new WaitCommand(.05));
    	// Take Out addParallel(new SaveCurrentAngle());

    	addParallel(new SetShooterSpeed(74));
    	//addParallel(new SetShooterSpeeD(73);
    	addSequential(new DriveDistanceStraight(95, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
    	
    	
    	
    	//addSequential(new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE - 60 - 48, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
    	
    	//
    	//Potential drive to edge of defense
    	//addParallel(new SetShooterSpeed(71));
    	//addSequential(new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE - 120, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));

    	rotateAlignShoot();
    	
		// ROTATE BACK TO INITIAL ANGLE
		addSequential(new RotateToLastAngle());
		
		// DRIVE BACK AND CHECK FOR BALL`
		addParallel(new SetCollectorPower(0.8));
		addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING + 35));
		// addParallel(new SetAnglerPosition(RobotMap.Collector.AUTON_COLLECTING));
		//addSequential(new DriveDistanceStraight(-RobotMap.Defenses.LOW_BAR_DISTANCE + 20, 
		//		-RobotMap.Defenses.LOW_BAR_SPEED + .1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
		//addSequential(new DriveBackAndCheckForBall(-RobotMap.Defenses.LOW_BAR_DISTANCE + 20 + 30 + 24));
		addSequential(new DriveBackAndCheckForBall(-95));
    	
		if(true)
        	return;
		
    	// STOP COLLCETOR
		addSequential(new WaitCommand(.1));
		addSequential(new SetCollectorPower(0, true));
		
		// DRIVE BACK
		addParallel(adjBall);
		addSequential(new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE - 60 - 48 + 24, 0.85, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
		
		rotateAlignShoot();
    }
    
    public void rotateAlignShoot() {
    	
    	// SET SHOOTER AND ROTATE 60
    	addSequential(new WaitCommand(.025));
    	
    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
    	addSequential(new RotateUsingGyro(43));
    	// ALL GOOD ^^^
    	
    	// ALIGN USING VISION
    	addSequential(new RotateUsingVisionContinuous(1));
    	
    	// SHOOT
    	addSequential(new SetCollectorPower(1, true));
		addSequential(new WaitForBeanBreak(false), .75);
		addParallel(new SetCollectorPower(0, true));
		addParallel(new SetShooterPower());
		addSequential(new WaitCommand(.125)); // 3.45 sec total max
	
    }
}
