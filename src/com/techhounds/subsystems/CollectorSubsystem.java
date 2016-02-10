package com.techhounds.subsystems; 

public class CollectorSubsystem {

	private static CollectorSubsystem instance;
	
	private CollectorSubsystem() {
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null)
			instance = new CollectorSubsystem();
		return instance;
	}
}