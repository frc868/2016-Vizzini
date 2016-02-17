
package com.techhounds;

import com.techhounds.commands.UpdateSmartDashboard;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.auton.UpdateValidAuton;
import com.techhounds.subsystems.BeamBreakSubsystem;
import com.techhounds.subsystems.CollectorAnglerSubsystem;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;
import com.techhounds.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static boolean oneControllerMode = true;
	private static boolean finalRobot = false;
	
	private Command autonCommand;
	
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
    	BeamBreakSubsystem.getInstance();
    	AutonChooser.getInstance();
    	
    	// TODO: Initialize Subsystems and OI
    	// TODO: Start Smart Dashboard
    	
    	initSubsystems();
    	OI.getInstance();
    	new UpdateSmartDashboard().start();
    	
    	System.out.println("*** TECHHOUNDS IS READY TO ROBOT ***");
    	SmartDashboard.putNumber("HEY", 3);
    	(new UpdateValidAuton()).start();
    }
	
    /**
     * Runs once the instant the robot is disabled
     */
    
    
    public void disabledInit(){
    	if(autonCommand != null)
    		autonCommand.cancel();
    }
	
    /**
     * Runs when the robot is disabled
     */
	public void disabledPeriodic() {
		BeamBreakSubsystem.getInstance().updateSmartDashboard();
		Scheduler.getInstance().run();
	}

	/**
	 * Runs once the instant the robot is in autonomous mode
	 */
	public void autonomousInit() {
		// TODO: Get Selected Auton Command and Run It!

    	System.out.println("*** TECHHOUNDS IS READY TO AUTON ***");
		(autonCommand = AutonChooser.getInstance().createAutonCommand()).start();
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
    	if(autonCommand != null)
    		autonCommand.cancel();

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
    	CollectorSubsystem.getInstance();
    	CollectorAnglerSubsystem.getInstance();
    	DriveSubsystem.getInstance();
    	GyroSubsystem.getInstance();
    	ShooterSubsystem.getInstance();
    	VisionSubsystem.getInstance();
    }
}
