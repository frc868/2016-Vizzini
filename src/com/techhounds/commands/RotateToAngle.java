package com.techhounds.commands;

import com.techhounds.Robot;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RotateToAngle extends Command implements PIDSource, PIDOutput{

	private DriveSubsystem drive;
	private GyroSubsystem gyro;
	private PIDController pid;
	private final double gyro_p;
	private final double gyro_i;
	private final double gyro_d;
	private final double percentTolerance = 0.1;
	
	public RotateToAngle() {
		requires(Robot.drive);
		drive = Robot.drive;
		gyro = Robot.gyro;
		gyro_p = 0.0001;
		gyro_i = 0;
		gyro_d = 0;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.getNumber("Gyro-Setpoint", 0);
		gyro.resetGyro();
		pid = new PIDController(gyro_p, gyro_i, gyro_d, this, this);
		pid.setPercentTolerance(percentTolerance);
		pid.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("Rotation Error", pid.getError());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pid.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		pid.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	@Override
	public double pidGet() {
		return gyro.getRotation();
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}
	
	@Override
	public void pidWrite(double output) {
		drive.setPower(output, output);
	}
}
