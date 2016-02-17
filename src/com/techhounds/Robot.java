
package com.techhounds;

import com.techhounds.commands.UpdateSmartDashboard;
import com.techhounds.subsystems.CollectorAnglerSubsystem;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;
import com.techhounds.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static double powToSpeedConst = 20;
	
	public static boolean oneControllerMode = true;
	private static boolean finalRobot = false;
	public static GyroSubsystem gyro;
	public static DriveSubsystem drive;
	public static CollectorSubsystem collector;
	public static CollectorAnglerSubsystem collectorAngler;
	public static ShooterSubsystem shooter;
	public static VisionSubsystem vision;
	
	public static boolean isFinal(){
		return finalRobot;
	}
	
	public static double rangeCheck(double power){
		return Math.max(Math.min(power, 1), -1);
	}
	
	/**
	 * Run once the instant the robot starts
	 */
    public void robotInit() {
    	// TODO: Initialize Subsystems and OI
    	// TODO: Start Smart Dashboard
    	
    	initSubsystems();
    	OI.getInstance();
    	new UpdateSmartDashboard().start();
    	
    	System.out.println("*** TECHHOUNDS IS READY TO ROBOT ***");
    	SmartDashboard.putNumber("HEY", 2);
    }
	
    /**
     * Runs once the instant the robot is disabled
     */
    public void disabledInit(){
    	
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
		// TODO: Get Selected Auton Command and Run It!

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
    	// TODO: Cancel Auton Command

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
    	collector = CollectorSubsystem.getInstance();
    	collectorAngler = CollectorAnglerSubsystem.getInstance();
    	drive = DriveSubsystem.getInstance();
    	gyro = GyroSubsystem.getInstance();
    	shooter = ShooterSubsystem.getInstance();
    	vision = VisionSubsystem.getInstance();
    }
}
