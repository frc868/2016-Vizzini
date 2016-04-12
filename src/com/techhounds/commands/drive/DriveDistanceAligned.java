package com.techhounds.commands.drive;

import com.techhounds.RobotMap;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.UltrasonicSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistanceAligned extends Command implements PIDSource, PIDOutput {

	private DriveSubsystem drive;
	private UltrasonicSubsystem ultrasonic;
	
	private double distToMaintain;
	private double distance;
	
	private PIDController pid;
	
	private double lastPower;
	private double targetPower;
	private double minPower;
	private boolean pidOnTarget;
	
	private double currDistance;
	
	private Double timeOut;
	
	public DriveDistanceAligned(double distToMaintain, double distance) {
		this(distToMaintain, distance, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER);
	}
	
	public DriveDistanceAligned(double distToMaintain, double distance, double targetPower) {
		this(distToMaintain, distance, targetPower, RobotMap.DriveTrain.MIN_STRAIGHT_POWER);
	}
	
	public DriveDistanceAligned(double distToMaintain, double distance, double targetPower, double min) {
		this(distToMaintain, distance, targetPower, min, null);
	}

	public DriveDistanceAligned(double distToMaintain, double distance, double targetPower, double min, Double timeOut) {
		drive = DriveSubsystem.getInstance();
		ultrasonic = UltrasonicSubsystem.getInstance();
		requires(drive);
		
		pid = new PIDController(.025, 0, .004, this, this);
		pid.setOutputRange(0, 0.5);
		pid.setAbsoluteTolerance(1);
		
		this.distToMaintain = distToMaintain;
		this.distance = distance;
		this.minPower = min;
		this.timeOut = timeOut;
		this.targetPower = targetPower;
	}

	@Override
	protected void initialize() {
		currDistance = drive.getAvgDistance();
		
		pid.setSetpoint(distToMaintain);
		pid.enable();

	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Ultrasonic Error", pid.getError());

	}

	@Override
	protected boolean isFinished() {
		if(timeOut != null){
			if(timeSinceInitialized() > timeOut){
				return true;
			}
		}
		
		return currDistance + distance > drive.getAvgDistance();
	}

	@Override
	protected void end() {
		pid.disable();
		drive.setPower(0, 0);

	}

	@Override
	protected void interrupted() {
		end();

	}

	@Override
	public void pidWrite(double output) {
		
		drive.setLeftPower(targetPower - output);
		drive.setRightPower(targetPower + output);
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
		return ultrasonic.getDistance();
	}

}
