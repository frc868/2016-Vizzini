package com.techhounds.frc2016.behavior;

public class Commands {
	
	public enum Collector {
		IN, OUT, FIRE, STOP
	}
	
	public enum Shooter {
		START, STOP, DO_NOTHING
	}
	
	public enum Angler {
		UP, COLLECTING, DOWN, DO_NOTHING
	}

	public enum Flashlight {
		TOGGLE, DO_NOTHING
	}
	
	public Collector m_collector;
	public Shooter m_shooter;
	public Angler m_angler;
	public Flashlight m_flashlight;
	
}
