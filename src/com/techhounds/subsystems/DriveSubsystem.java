package com.techhounds.subsystems;

import edu.wpi.first.wpilibj.Spark;

public class DriveSubsystem{

	// TODO: Add SpeedControllers for Left and Right Drive
	// TODO: Add Ports for Each Side
	// TODO: Add Encoders
	// TODO: Add Implementation for Set Drive Power (maybe Voltage?)
	// TODO: Add Drive Distance PID Controller
	// TODO: Add Gyro Turn PID Controller
	
	private Spark left;
	private Spark right;
	
	private static DriveSubsystem instance;
	
	private DriveSubsystem(Spark l, Spark r) {
		left = l;
		right = r;
	}
	
	public static DriveSubsystem getInstance() {
		if(instance == null) {
			Spark l = new Spark(-1);
			Spark r = new Spark(-1);
			instance = new DriveSubsystem(l, r);
		}
		return instance;
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO: Add Default command for DriveWithGamepad()
	}

}
