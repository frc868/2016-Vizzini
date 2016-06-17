package com.techhounds.frc2016.commands.drive;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.controllers.DriveStraightController;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {

	private DriveStraightController m_controller = new DriveStraightController();
	private DriveSubsystem drive;
	
	private double distance;
	private static double DRIVE_STRAIGHT_TOL = 2.5;
	
	public DriveStraight(double distance) {
		this.distance = distance;
		requires(drive = HardwareAdaptor.kDriveSubsystem);
	}
	
	public DriveStraight(double distance, double maxPower) {
		this(distance);
		m_controller.getController()
			.setOutputRange(-maxPower, maxPower);
	}
	
	@Override
	protected void initialize() {
		m_controller.getController()
			.setSetpoint(drive.getAvgDistance() + distance);
		m_controller.enable();
		drive.setController(m_controller);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return m_controller.onTarget(DRIVE_STRAIGHT_TOL);
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
