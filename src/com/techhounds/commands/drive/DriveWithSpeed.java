package com.techhounds.commands.drive;

import com.techhounds.OI;
import com.techhounds.Robot;
import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithSpeed extends Command {
	DriveSubsystem drive;
	double speed;

	private PIDController leftPID;
	private PIDController rightPID;

	public DriveWithSpeed(double speed) {
		this.speed = speed;

		rightPID = new PIDController(0,0,0, new PID(false), new PID(false));
		leftPID = new PIDController(0,0,0, new PID(true), new PID(true));

		drive = DriveSubsystem.getInstance();
		requires(drive);
	}


	// Called just before this Command runs the first time
	protected void initialize() {
		rightPID.enable();
		leftPID.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		rightPID.disable();
		leftPID.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	private class PID implements PIDSource, PIDOutput {

		private boolean isLeft;

		public PID(boolean isLeft) {
			this.isLeft = isLeft;
		}

		@Override
		public void pidWrite(double output) {
			if(isLeft)
				drive.setLeftPower(output);
			else
				drive.setRightPower(output);
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kRate;
		}

		@Override
		public double pidGet() {
			return (isLeft ? OI.getInstance().getLeftBackward() * Robot.powToSpeedConst : OI.getInstance().getRightBackward()) * Robot.powToSpeedConst;
		}
	}
}
