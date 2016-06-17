package com.techhounds.frc2016.commands.drive;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.Drive;
import com.techhounds.frc2016.subsystems.controllers.DriveStraightController;
import com.techhounds.frc2016.subsystems.controllers.DriveTrajectoryController;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrajectory extends Command {

	private DriveTrajectoryController m_controller = new DriveTrajectoryController();
	private Drive drive;
	
	public DriveTrajectory() {
		requires(drive = HardwareAdaptor.kDriveSubsystem);
	}
	
	@Override
	protected void initialize() {
		m_controller.enable();
		drive.setController(m_controller);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return m_controller.onTarget(0);
	}

	@Override
	protected void end() {
		m_controller.disable();
		drive.setController(null);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
