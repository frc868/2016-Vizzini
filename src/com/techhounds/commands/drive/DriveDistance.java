package com.techhounds.commands.drive;

import com.techhounds.RobotMap;
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
	private double lastPower;
	private double minPower;
	public DriveDistance(double dist) {
		this(dist, 1);
	}
	
	public DriveDistance(){
		this(30, .4);
	}
	
	public DriveDistance(double dist, double max, double min) {
		drive = DriveSubsystem.getInstance();
		requires(drive);
		pid = new PIDController(.02,  0, .005, this, this);
		pid.setOutputRange(-max, max);
		pid.setAbsoluteTolerance(1);
		minPower = min;
		this.targetDist = dist;
		SmartDashboard.putData("Drive distance pid", pid);
		SmartDashboard.putNumber("Power to Drive", 0.3);
		SmartDashboard.putNumber("Min Power To Drive", min);
	}
	public DriveDistance(double dist, double max){
		this(dist, max, RobotMap.DriveTrain.MIN_STRAIGHT_POWER);
	}
	
	@Override
	protected void initialize() {
		targetDist = SmartDashboard.getNumber("Distance To Drive");
		double maxPower = SmartDashboard.getNumber("Power to Drive", 0.3);
		minPower = SmartDashboard.getNumber("Min Power To Drive", .2);
		pid.setOutputRange(-maxPower, maxPower);
		double curDist = drive.countsToDist(drive.getAvgDistance());
		lastPower = 0;
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
		drive.setPower(0,0);
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pidWrite(double output) {
		double difference = output - lastPower;
		if(difference > .1){
			output = lastPower + .1;
		}else if(difference < -.1){
			output = lastPower - .1;
		}
		
		if(output < minPower || output > minPower)
			output = output > 0 ? minPower : -minPower;
		lastPower = output;
		drive.setLeftPower(output);
		drive.setRightPower(output);
		
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