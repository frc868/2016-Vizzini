package com.techhounds.subsystems;

public class CollectorSubsystem {

	private static CollectorSubsystem instance;
	
	private SpinnerSubsystem subsystemSpinner;
	
	private CollectorSubsystem() {
		subsystemSpinner = new SpinnerSubsystem() {

			// TODO: Add Custom Implementation
		};
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null)
			instance = new CollectorSubsystem();
		return instance;
	}
	
	public SpinnerSubsystem getSpinner() {
		return subsystemSpinner;
	}
}