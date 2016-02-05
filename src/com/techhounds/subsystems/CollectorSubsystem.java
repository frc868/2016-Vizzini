package com.techhounds.subsystems;

public class CollectorSubsystem {

	private static CollectorSubsystem instance;
	
	private AnglerSubsystem subsystemAngler;
	private SpinnerSubsystem subsystemSpinner;
	
	private CollectorSubsystem() {
		subsystemAngler = new AnglerSubsystem() {

			// TODO: Add Custom Implementation
		};
		
		subsystemSpinner = new SpinnerSubsystem() {

			// TODO: Add Custom Implementation
		};
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null)
			instance = new CollectorSubsystem();
		return instance;
	}
	
	public AnglerSubsystem getAngler() {
		return subsystemAngler;
	}
	
	public SpinnerSubsystem getSpinner() {
		return subsystemSpinner;
	}
}