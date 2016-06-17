package com.techhounds.frc2016.commands.drive_legacy;
import com.techhounds.frc2016.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class StopDrive extends Command {

	private Drive drive;
	
	public StopDrive() {
		requires(drive = Drive.getInstance());
	}
	@Override
	protected void initialize() {
		drive.setPower(0, 0);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
