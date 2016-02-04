
package com.techhounds;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	
	/**
	 * Run once the instant the robot starts
	 */
    public void robotInit() {
    	// TODO: Initialize Subsystems and OI
    	// TODO: Start Smart Dashboard
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
}
