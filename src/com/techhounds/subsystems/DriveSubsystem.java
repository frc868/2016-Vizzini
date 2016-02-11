package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem implements PIDOutput, PIDSource{

	// TODO: Add Encoders
	// TODO: Add Implementation for Set Drive Power (maybe Voltage?)
	// TODO: Add Drive Distance PID Controller
	// TODO: Add Gyro Turn PID Controller
	
	private Spark left;
	private Spark right;
	private boolean rotating = false;
	private boolean straight = false;
	
	private static DriveSubsystem instance;
	
	private DriveSubsystem(Spark l, boolean l_i, Spark r, boolean r_i) {
		left = l;
		left.setInverted(l_i);
		right = r;
		right.setInverted(r_i);
	}
	
	public static DriveSubsystem getInstance() {
		if(instance == null) {
			Spark l = new Spark(RobotMap.DriveTrain.DRIVE_LEFT_MOTOR);
			Spark r = new Spark(RobotMap.DriveTrain.DRIVE_RIGHT_MOTOR);
			instance = new DriveSubsystem(l, RobotMap.DriveTrain.DRIVE_LEFT_IS_INVERTED, r,RobotMap.DriveTrain.DRIVE_RIGHT_IS_INVERTED);
		}
		return instance;
	}
	
	public void loadStraightPIDValues(double p, double i, double d, double f) {
		
	}
	
	public void setLeftSpeed(double speed) {
		left.set(speed);
	}
	
	public void setRightSpeed(double speed) {
		right.set(speed);
	}
	
	public void setBothSpeed(double output) {
		left.set(output);
		right.set(output);
	}
	
	public double getLeftSpeed() {
		return left.get();
	}
	
	public double getRightSpeed() {
		return right.get();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Driver Left Speed", getLeftSpeed());
		SmartDashboard.putNumber("Driver Right Speed", getRightSpeed());
	}

	protected void initDefaultCommand() {
		// TODO: Add Default command for DriveWithGamepad()
	}

	@Override
	public void pidWrite(double output) {
		setBothSpeed(output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getLeftSpeed();
	}

}
