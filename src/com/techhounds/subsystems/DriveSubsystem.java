package com.techhounds.subsystems;

public class DriveSubsystem{

	// TODO: Add SpeedControllers for Left and Right Drive
	// TODO: Add Ports for Each Side
	// TODO: Add Encoders
	// TODO: Add Implementation for Set Drive Power (maybe Voltage?)
	// TODO: Add Drive Distance PID Controller
	// TODO: Add Gyro Turn PID Controller
	
	private static DriveSubsystem instance;
	
	public static DriveSubsystem getInstance() {
		if(instance == null)
			instance = new DriveSubsystem();
		return instance;
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO: Add Default command for DriveWithGamepad()
	}

}
