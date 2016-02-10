package com.techhounds.subsystems;

public abstract class SpinnerSubsystem{

	// TODO: Add SpeedController
	// TODO: Constructor needs to be able to pass, PORT_NUMBER and ENCODERS (null if nonexistent)
	// TODO: Add PID Controller to set to a Speed
	// TODO: Add Generic Implementation
	
	SpinnerSubsystem() {
		super();
	}
	
	SpinnerSubsystem(String name) {
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		
	}

}
