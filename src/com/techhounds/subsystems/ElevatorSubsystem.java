package com.techhounds.subsystems;

public class ElevatorSubsystem{
	
	// TODO: Add SpeedController(s) for the Elevator
	// TODO: Add Ports for the Elevator
	// TODO: Add Implementation (with reasonable constants (use Enums when possible))
	
	private static ElevatorSubsystem instance;
	
	public static ElevatorSubsystem getInstance() {
		if(instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}
	
	public void updateSmartDashboard() {
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
