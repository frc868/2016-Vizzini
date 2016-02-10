package com.techhounds.subsystems;

public class VisionSubsystem{

	private static VisionSubsystem instance;
	
	public static VisionSubsystem getInstance() {
		if(instance == null)
			instance = new VisionSubsystem();
		return instance;
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
