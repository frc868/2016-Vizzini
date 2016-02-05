package com.techhounds.subsystems;

public class ShooterSubsystem {

	private static ShooterSubsystem instance;
	
	private AnglerSubsystem subsystemAngler;
	private SpinnerSubsystem subsystemSpinner;
	
	private ShooterSubsystem() {
		subsystemAngler = new AnglerSubsystem() {

			// TODO: Add Custom Implementation
		};
		
		subsystemSpinner = new SpinnerSubsystem() {

			// TODO: Add Custom Implementation
		};
	}
	
	public static ShooterSubsystem getInstance() {
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}
	
	public AnglerSubsystem getAngler() {
		return subsystemAngler;
	}
	
	public SpinnerSubsystem getSpinner() {
		return subsystemSpinner;
	}
}
