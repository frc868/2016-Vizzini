package com.techhounds.frc2016.commands.drive;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.Drive;
import com.techhounds.frc2016.subsystems.controllers.DriveStraightController;
import com.techhounds.frc2016.subsystems.controllers.TurnInPlaceController;

import edu.wpi.first.wpilibj.command.Command;

public class TurnInPlace extends Command {

	private TurnInPlaceController m_controller = new TurnInPlaceController();
	private Drive drive;
	
	private double degrees;
	private static double GYRO_TOL = 1.0;
	
	public TurnInPlace(double degrees) {
		this.degrees = degrees;
		requires(drive = HardwareAdaptor.kDriveSubsystem);
	}
	
	public TurnInPlace(double degrees, double maxPower) {
		this(degrees);
		m_controller.getController()
			.setOutputRange(-maxPower, maxPower);
	}
	
	@Override
	protected void initialize() {
		m_controller.getController()
			.setSetpoint(HardwareAdaptor.kGyroSubsystem.getRotation() + degrees);
		m_controller.enable();
		drive.setController(m_controller);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return m_controller.onTarget(GYRO_TOL);
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
