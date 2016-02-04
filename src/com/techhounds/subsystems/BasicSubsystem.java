package com.techhounds.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class BasicSubsystem extends Subsystem {
    
	private static List<BasicSubsystem> subsystems = new ArrayList<>();
	
	public BasicSubsystem() {
		super();
		subsystems.add(this);
	}
	
	public BasicSubsystem(String name) {
		super(name);
		subsystems.add(this);
	}
	
	public static List<BasicSubsystem> getSubsystems() {
		return subsystems;
	}
	
	public abstract void updateSmartDashboard();
}

