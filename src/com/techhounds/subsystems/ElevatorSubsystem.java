package com.techhounds.subsystems;

import com.techhounds.lib.subsystems.BasicSubsystem;

public class ElevatorSubsystem extends BasicSubsystem {
	
	// TODO: Add SpeedController(s) for the Elevator
	// TODO: Add Ports for the Elevator
	// TODO: Add Implementation (with reasonable constants (use Enums when possible))
	
	private static ElevatorSubsystem instance;
	
	public static ElevatorSubsystem getInstance() {
		if(instance == null)
			instance = new ElevatorSubsystem();
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
