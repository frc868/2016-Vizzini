package com.techhounds.lib.util;

import java.util.Vector;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class HoundSubsystem {

	private static Vector<HoundSubsystem> subsystems = new Vector<>();
	
	public HoundSubsystem() {
		subsystems.add(this);
	}
	
	public static void updateSubsystemsPeriodic() {
		for(HoundSubsystem sub : subsystems)
			sub.updatePeriodic();
	}
	
	public abstract void updatePeriodic();
	
	public static class DashboardUpdate implements Updateable {

		@Override
		public void update() {
			updateSubsystemsPeriodic();
		}
	}
}
