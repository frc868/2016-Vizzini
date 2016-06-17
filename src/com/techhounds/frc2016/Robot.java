
package com.techhounds.frc2016;

import com.techhounds.frc2016.commands.MatchSetup;
import com.techhounds.frc2016.commands.SetFlashlight;
import com.techhounds.frc2016.commands.UpdateSmartDashboard;
import com.techhounds.frc2016.commands.auton.RetrieveAuton;
import com.techhounds.frc2016.commands.servos.SetWinchLock;
import com.techhounds.lib.util.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public Updater controllers = new Updater(200.0);
	public Updater slowControllers = new Updater(100.0);
	
	private static final String GAME_STATE = "GameState";

	public static boolean oneControllerMode = false;

	public static boolean isFinal() {
		return true;
	}

	// Runs once when Robot is on
	public void robotInit() {

		SmartDashboard.putString(GAME_STATE, "robotInit");

		new UpdateSmartDashboard().start();
		new MatchSetup().start();
		new SetWinchLock(false).start();

		controllers.addUpdateable(HardwareAdaptor.kShooterSubsystem);

		slowControllers.addUpdateable(HardwareAdaptor.kDriveSubsystem);

		System.out.println("*** TECHHOUNDS IS READY TO ROBOT ***");
	}

	// Runs once when Disabled
	public void disabledInit() {

		controllers.stop();
		slowControllers.stop();

		new SetFlashlight(false).start();

		SmartDashboard.putString(GAME_STATE, "disabled");

		System.out.println("*** TECHHOUNDS IS DISABLED ***");
	}

	// Runs once when in Autonomous
	public void autonomousInit() {
		SmartDashboard.putString(GAME_STATE, "auton");

		controllers.start();
		slowControllers.start();

		new RetrieveAuton().start();

		System.out.println("*** TECHHOUNDS IS AUTON ***");

	}

	// Runs once when in Teleop
	public void teleopInit() {
		SmartDashboard.putString(GAME_STATE, "teleop");

		controllers.start();
		slowControllers.start();

		new MatchSetup().start();

		System.out.println("*** TECHHOUNDS IS TELEOP ***");
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
