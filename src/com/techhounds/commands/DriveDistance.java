package com.techhounds.commands;

import com.techhounds.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command implements PIDSource, PIDOutput {

	private DriveSubsystem drive;
	private double targetDist;
	private PIDController pid;

	public DriveDistance(double dist) {
		this(dist, 1);
	}
	
	public DriveDistance(){
		this(30);
	}
	
	public DriveDistance(double dist, double max) {
		drive = DriveSubsystem.getInstance();
		requires(drive);
		pid = new PIDController(.5,  0, 0, this, this);
		pid.setOutputRange(-max, max);
		this.targetDist = dist;
		SmartDashboard.putData("Drive distance pid", pid);
	}
	
	@Override
	protected void initialize() {
		targetDist = SmartDashboard.getNumber("Distance To Drive");
		double curDist = drive.countsToDist(drive.getAvgDistance());
		pid.setSetpoint(targetDist + curDist);
		pid.enable();
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Drive Error", pid.getError());
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return pid.onTarget();
	}

	@Override
	protected void end() {
		pid.disable();
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pidWrite(double output) {
		drive.setLeftPower(.4);
		drive.setRightPower(.4);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return drive.countsToDist(drive.getAvgDistance());
	}

}
