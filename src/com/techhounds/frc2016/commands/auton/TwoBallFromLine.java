package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.ProfileGenerator;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.commands._experimental.DriveTrajectory;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;
import com.techhounds.frc2016.commands.collector.SetCollectorPower;
import com.techhounds.frc2016.commands.collector.WaitForBeanBreak;
import com.techhounds.frc2016.commands.drive_legacy.DriveDistanceStraight;
import com.techhounds.frc2016.commands.gyro.RotateToLastAngle;
import com.techhounds.frc2016.commands.gyro.RotateUsingGyro;
import com.techhounds.frc2016.commands.gyro.SaveCurrentAngle;
import com.techhounds.frc2016.commands.shooter.SetShooterPower;
import com.techhounds.frc2016.commands.shooter.SetShooterSpeed;

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
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.COLLECT));
    	addParallel(new SetCollectorPower(0.8));
    	addSequential(new WaitForBeanBreak(true), .95);
    	addParallel(new SetCollectorPower(0));
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.UP));
    	
    	addParallel(new DriveTrajectory(1, true));
    	addParallel(adjBall2, 0.75);
    	addSequential(new SetAnglerPosition(HardwareConstants.Angler.COLLECT), 0.75);
    	/* Proposed New Low Bar
    	addSequential(new RotateUsingGyro(-20));
    	addSequential(new WaitCommand(.1);
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.COLLECT));
    	addSequential(new DriveDistance(50));
    	addSequential(new RotateToLastAngle());
    	*/
    	
    	// DRIVE LOW BAR
    	addSequential(new WaitCommand(.05));
    	// Take Out addParallel(new SaveCurrentAngle());

    	addParallel(new SetShooterSpeed(74));
    	//addParallel(new SetShooterSpeeD(73);
    	addSequential(new DriveDistanceStraight(95, 1, HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));
    	
    	
    	//addSequential(new DriveDistanceStraight(HardwareConstants.Defenses.LOW_BAR_DISTANCE - 60 - 48, 1, HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));
    	
    	//
    	//Potential drive to edge of defense
    	//addParallel(new SetShooterSpeed(71));
    	//addSequential(new DriveDistanceStraight(HardwareConstants.Defenses.LOW_BAR_DISTANCE - 120, HardwareConstants.Defenses.LOW_BAR_SPEED, HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));

    	rotateAlignShoot();
    	
		// ROTATE BACK TO INITIAL ANGLE
		addSequential(new RotateToLastAngle());
		
		// DRIVE BACK AND CHECK FOR BALL`
		addParallel(new SetCollectorPower(0.8));
		addParallel(new SetAnglerPosition(HardwareConstants.Angler.COLLECT + 10));
		// addParallel(new SetAnglerPosition(HardwareConstants.Collector.AUTON_COLLECTING));
		//addSequential(new DriveDistanceStraight(-HardwareConstants.Defenses.LOW_BAR_DISTANCE + 20, 
		//		-HardwareConstants.Defenses.LOW_BAR_SPEED + .1, -HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));
		//addSequential(new DriveBackAndCheckForBall(-HardwareConstants.Defenses.LOW_BAR_DISTANCE + 20 + 30 + 24));
		addSequential(new DriveBackAndCheckForBall(-95-24));
    	
		if(true)
        	return;
		
    	// STOP COLLCETOR
		addSequential(new WaitCommand(.1));
		addSequential(new SetCollectorPower(0, true));
		
		// DRIVE BACK
		addParallel(adjBall);
		addSequential(new DriveDistanceStraight(HardwareConstants.Defenses.LOW_BAR_DISTANCE - 60 - 48 + 24, 0.85, HardwareConstants.Drive.MIN_STRAIGHT_POWER, 5.0, true));
		
		rotateAlignShoot();
    }
    
    public void rotateAlignShoot() {
    	
    	// SET SHOOTER AND ROTATE 60
    	addSequential(new WaitCommand(.025));
    	
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.UP));
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
