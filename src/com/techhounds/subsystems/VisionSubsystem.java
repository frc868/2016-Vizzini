package com.techhounds.subsystems;

import com.techhounds.lib.subsystems.BasicSubsystem;

public class VisionSubsystem extends BasicSubsystem {

	private static VisionSubsystem instance;
	
	public static VisionSubsystem getInstance() {
		if(instance == null)
			instance = new VisionSubsystem();
		return instance;
	}
	
	@Override
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
