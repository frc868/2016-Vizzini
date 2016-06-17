package com.techhounds.frc2016.subsystems.controllers;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem.Controller;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.lib.trajectory.Trajectory;
import com.techhounds.lib.trajectory.TrajectoryFollower;
import com.techhounds.lib.util.DriveSignal;
import com.techhounds.lib.util.HoundMath;

public class DriveTrajectoryController implements Controller {

	private static double
		Kp = 0.0,
		Ki = 0.0,
		Kd = 0.0,
		Kv = 1 / 110.0,
		Ka = 0.0,
		Kturn = 0.0;
	
	private double initAngle;
	private boolean firstCall = true;
	private boolean isEnabled;
	
	private DriveSubsystem drive = HardwareAdaptor.kDriveSubsystem;
	private GyroSubsystem gyro = HardwareAdaptor.kGyroSubsystem;
	
	private TrajectoryFollower leftFollower;
	private TrajectoryFollower rightFollower;
	
	public void setTrajectory(Trajectory left, Trajectory right) {
		leftFollower = new TrajectoryFollower(left, Kp, Ki, Kd, Kv, Ka, Kturn);
		rightFollower = new TrajectoryFollower(right, Kp, Ki, Kd, Kv, Ka, Kturn);
	}
	
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void enable() {
		drive.resetEncoders();
		isEnabled = true;
	}
	
	public void disable() {
		firstCall = true;
		isEnabled = false;
	}
	
	public boolean onTarget(double tolerance) {
		return leftFollower.isFinished();
	}

	@Override
	public DriveSignal update() {
		
		if(onTarget(0) || leftFollower == null || rightFollower == null) {
			return DriveSignal.STOP;
		}
		
		if(firstCall) {
			initAngle = gyro.getRotation();
			firstCall = false;
		}
		
		double left = leftFollower.calculateOutput(drive.getLeftDistance());
		double right = rightFollower.calculateOutput(drive.getRightDistance());
		
		double turn = leftFollower.calculateHeading(gyro.getRotation() - initAngle);
		turn = 0;
		
		return new DriveSignal(
				HoundMath.checkRange(left - turn) , 
				HoundMath.checkRange(right + turn));
	}
}
