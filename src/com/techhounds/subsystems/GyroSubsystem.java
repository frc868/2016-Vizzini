package com.techhounds.subsystems;

public class GyroSubsystem{

	private static GyroSubsystem instance;
	
	public static GyroSubsystem getInstance() {
		if(instance == null)
			instance = new GyroSubsystem();
		return instance;
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
