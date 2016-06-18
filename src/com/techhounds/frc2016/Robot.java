
package com.techhounds.frc2016;

import com.techhounds.frc2016.behavior.BehaviorManager;
import com.techhounds.frc2016.behavior.Commands;
import com.techhounds.frc2016.behavior.Commands.Angler;
import com.techhounds.frc2016.behavior.Commands.Collector;
import com.techhounds.frc2016.behavior.Commands.Shooter;
import com.techhounds.lib.util.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public Updater m_controllers = new Updater(200.0);	   // CANTalon
	public Updater m_slowControllers = new Updater(100.0); // Sparks
	public Updater m_logger = new Updater(50.0);
	
	public BehaviorManager m_behaviorManager = new BehaviorManager();
	
	public Commands m_matchSetup = new Commands();
	
	private static final String GAME_STATE = "GameState";
	public static boolean oneControllerMode = false;
	public static GameState robotStatus;
	
	public enum GameState {
		AUTONOMOUS, TELEOP, DISABLED, ROBOT_INIT, TEST
	}

	public static boolean isFinal() {
		return true;
	}

	// Runs once when Robot is on
	public void robotInit() {

		SmartDashboard.putString(GAME_STATE, "robotInit");
		robotStatus = GameState.ROBOT_INIT;
		
		m_matchSetup.m_angler = Angler.UP;
		m_matchSetup.m_collector = Collector.STOP;
		m_matchSetup.m_shooter = Shooter.STOP;
		
		// Setup our Updaters / Controllers
		m_controllers.addUpdateable(HardwareAdaptor.kShooterSubsystem::update);
		m_controllers.addUpdateable(HardwareAdaptor.kCollectorSubsystem::update);

		m_slowControllers.addUpdateable(HardwareAdaptor.kDriveSubsystem::update);
		
		m_logger.addUpdateable(HardwareAdaptor.kDashboardUpdater::update);
		m_logger.addUpdateable(HardwareAdaptor.kLEDSubsystem::update);
		m_logger.start();
		
		// Match Setup
		m_behaviorManager.update(m_matchSetup);
		
		System.out.println("*** TECHHOUNDS IS READY TO ROBOT ***");
	}

	// Runs once when Disabled
	public void disabledInit() {

		SmartDashboard.putString(GAME_STATE, "disabled");
		robotStatus = GameState.DISABLED;
		
		// Disable Our Updaters / Controllers
		setControllers(false);
		//new SetFlashlight(false).start();


		System.out.println("*** TECHHOUNDS IS DISABLED ***");
	}

	// Runs once when in Autonomous
	public void autonomousInit() {
		
		SmartDashboard.putString(GAME_STATE, "auton");
		robotStatus = GameState.AUTONOMOUS;

		// Enable Our Updaters / Controllers
		setControllers(true);

		System.out.println("*** TECHHOUNDS IS AUTON ***");

	}

	// Runs once when in Teleop
	public void teleopInit() {
		
		SmartDashboard.putString(GAME_STATE, "teleop");
		robotStatus = GameState.TELEOP;

		// Enable Our Updaters / Controllers
		setControllers(true);
		
		// Match Setup
		m_behaviorManager.update(m_matchSetup);

		System.out.println("*** TECHHOUNDS IS TELEOP ***");
	}
	
	public void setControllers(boolean state) {
		if(state) {
			m_controllers.start();
			m_slowControllers.start();
		} else {
			m_controllers.stop();
			m_slowControllers.stop();
		}
	}

	public void teleopPeriodic() {
		// Update Driver / Operator Input
		m_behaviorManager.update(HardwareAdaptor.kOperatorInterface.getCommands());
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
