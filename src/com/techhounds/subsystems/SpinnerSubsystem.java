package com.techhounds.subsystems;

import com.techhounds.lib.subsystems.BasicSubsystem;

public abstract class SpinnerSubsystem extends BasicSubsystem {

	// TODO: Add SpeedController
	// TODO: Constructor needs to be able to pass, PORT_NUMBER and ENCODERS (null if nonexistent)
	// TODO: Add PID Controller to set to a Speed
	// TODO: Add Generic Implementation
	
	SpinnerSubsystem() {
		super();
	}
	
	SpinnerSubsystem(String name) {
		super(name);
	}
	
	@Override
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	@Override
	protected void initDefaultCommand() {
		
	}

}
