
package com.techhounds;

import com.techhounds.commands.MatchSetup;
import com.techhounds.commands.SetFlashlight;
import com.techhounds.commands.CameraCommand;
import com.techhounds.commands.UpdateSmartDashboard;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.auton.RetrieveAuton;
import com.techhounds.commands.drive.LockWinch;
import com.techhounds.commands.servos.SetWinchLock;
import com.techhounds.subsystems.AnglerSubsystem;
import com.techhounds.subsystems.BeamBreakSubsystem;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.FlashlightSubsystem;
import com.techhounds.subsystems.GyroSubsystem;
import com.techhounds.subsystems.LEDSubsystem;
import com.techhounds.subsystems.ServoSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;
import com.techhounds.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private static final String GAME_STATE = "GameState";
	public static boolean competing = true;
	public static double powToSpeedConst = 20;
	
	public static boolean oneControllerMode = false;
	public static boolean manualOverride = false;
	
	
	public static boolean isFinal(){
		return true;
	}
	
	public static double rangeCheck(double power){
		return rangeCheck(power, -1, 1);
	}
	
	public static double rangeCheck(double power, double min, double max) {
		return Math.max(Math.min(power, max), min);
	}
	/**
	 * Run once the instant the robot starts
	 */
    public void robotInit() {
    	// TODO: Initialize Subsystems and OI
    	// TODO: Start Smart Dashboard
    	SmartDashboard.putString(GAME_STATE, "robotInit");
    	
    	initSubsystems();
    	OI.getInstance();
    	AutonChooser.getInstance();
    	new UpdateSmartDashboard().start();
    	new MatchSetup().start();
    	System.out.println("*** TECHHOUNDS IS READY TO ROBOT ***");
    	SmartDashboard.putNumber("HEY", 2);
    	new SetWinchLock(false).start();
    }
	
    /**
     * Runs once the instant the robot is disabled
     */
    public void disabledInit(){
    	DriveSubsystem.getInstance().closeWriter();
    	new SetFlashlight(false).start();
       	SmartDashboard.putString(GAME_STATE, "disabled");   	
    	//new USBCameraCommand(true).start();
    }
	
    /**
     * Runs when the robot is disabled
     */
	public void disabledPeriodic() {
		
		Scheduler.getInstance().run();
	}

	/**
	 * Runs once the instant the robot is in autonomous mode
	 */
	public void autonomousInit() {
    	SmartDashboard.putString(GAME_STATE, "auton");
		// TODO: Get Selected Auton Command and Run It!
		new RetrieveAuton().start();
    	System.out.println("*** TECHHOUNDS IS READY TO AUTON ***");
    }

	/**
	 * Runs while the robot is in autonomous mode
	 */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Runs once the instant that the robot is in teleop mode
     */
    public void teleopInit() {
    	SmartDashboard.putString(GAME_STATE, "teleop");
    	// TODO: Cancel Auton Command
    	new MatchSetup().start();
    	//new SetFlashlight(true).start();
    	//new CameraCommand(true).start();
    	System.out.println("*** TECHHOUNDS IS READY TO TELEOP ***");
    }

    /**
	 * Runs while the robot is in teleop mode
	 */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void initSubsystems() {
    	AnglerSubsystem.getInstance();
    	BeamBreakSubsystem.getInstance();
    	CollectorSubsystem.getInstance();
    	DriveSubsystem.getInstance();
    	GyroSubsystem.getInstance();
    	//ServoSubsystem.getScissorOne();
    	//ServoSubsystem.getScissorTwo();
    	ServoSubsystem.getWinchEnable();
    	ServoSubsystem.getScissorOne();
    	ServoSubsystem.getWinchLock();
    	ShooterSubsystem.getInstance();
    	VisionSubsystem.getInstance();
    	LEDSubsystem.getInstance();
    	FlashlightSubsystem.getInstance();
    }
}
