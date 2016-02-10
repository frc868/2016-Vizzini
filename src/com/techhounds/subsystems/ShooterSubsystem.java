package com.techhounds.subsystems;

public class ShooterSubsystem {

	private static ShooterSubsystem instance;
	
	private ShooterSubsystem() {
	}
	
	public static ShooterSubsystem getInstance() {
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}
}
