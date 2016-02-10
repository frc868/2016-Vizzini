package com.techhounds.subsystems;

public class ShooterSubsystem {

	private static ShooterSubsystem instance;
	
	private SpinnerSubsystem subsystemSpinner;
	
	private ShooterSubsystem() {
		subsystemSpinner = new SpinnerSubsystem() {

			// TODO: Add Custom Implementation
		};
	}
	
	public static ShooterSubsystem getInstance() {
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}
	
	public SpinnerSubsystem getSpinner() {
		return subsystemSpinner;
	}
}
