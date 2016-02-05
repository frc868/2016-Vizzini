package com.techhounds.subsystems;

import com.techhounds.lib.subsystems.BasicSubsystem;

public class GyroSubsystem extends BasicSubsystem {

	private static GyroSubsystem instance;
	
	public static GyroSubsystem getInstance() {
		if(instance == null)
			instance = new GyroSubsystem();
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
