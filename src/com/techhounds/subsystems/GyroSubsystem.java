package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;
import com.techhounds.gyro.GyroItg3200;
import com.techhounds.gyro.RotationTracker;
import com.techhounds.gyro.RotationTrackerInverted;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroSubsystem extends Subsystem {

	private static GyroSubsystem instance;
	
	private GyroItg3200 gyro;
	private RotationTracker tilt, lean, rotation;
	
	public boolean gyroEnabled;
	
	private GyroSubsystem() {

		if (Robot.isFinal()){
			if(gyroEnabled = RobotMap.Gyro.GYRO != null) { 
	
				gyro = new GyroItg3200(RobotMap.Gyro.GYRO, false);
				
				tilt = getTiltTracker();
				lean = getLeanTracker();
				rotation = getRotationTracker();		
			}
		}else{
			if(gyroEnabled = RobotMap.Gyro.GYRO_PRACT != null) {
				
				gyro = new GyroItg3200(RobotMap.Gyro.GYRO_PRACT, false);
				
				tilt = getTiltTracker();
				lean = getLeanTracker();
				rotation = getRotationTracker();		
			}
		}
	}
	
	public static GyroSubsystem getInstance() {
		if(instance == null)
			instance = new GyroSubsystem();
		return instance;
	}
	
	public void updateSmartDashboard() {
//		SmartDashboard.putNumber("Tilt", getTilt());
//		SmartDashboard.putNumber("Lean", getLean());
		SmartDashboard.putNumber("Rotation", getRotation());
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public double getTilt() {
		return gyroEnabled ? tilt.getAngle() : 0.0;
	}
	
	public double getLean() {
		return gyroEnabled ? lean.getAngle() : 0.0;
	}
	
	public double getRotation() {
		return gyroEnabled ? rotation.getAngle() : 0.0;
	}
	
	public RotationTracker getTiltTracker() {
		return gyroEnabled ? gyro.getRotationZ() : null;
	}
	
	public RotationTracker getLeanTracker() {
		return gyroEnabled ? gyro.getRotationY() : null;
	}
	
	public RotationTracker getRotationTracker() {
		return gyroEnabled ? new RotationTrackerInverted(gyro.getRotationX()) : null;
	}
	
	public void resetGyro() {
		if (gyroEnabled)
			gyro.reset();
	}
}
