package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 */
public class Flashlight extends HoundSubsystem {
   
	private Relay flashlight = HardwareAdaptor.kFlashlight;
	private static Flashlight instance;
	
	private Flashlight(){
		flashlight.setDirection(Relay.Direction.kForward);
	}
	
	public static Flashlight getInstance()  {
		return instance == null ? instance = new Flashlight() : instance;
	}
	
	public void on(){
		flashlight.set(Relay.Value.kOn);
	}
	public void off(){
		flashlight.set(Relay.Value.kOff);
	}
	
	public void toggle(){
		flashlight.set(flashlight.get() == Relay.Value.kOn ? Relay.Value.kOff : Relay.Value.kOn);
	}

	public void updatePeriodic() {
		
	}
	
    public void initDefaultCommand() {
    	
    }
}

