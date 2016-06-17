package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 */
public class FlashlightSubsystem extends HoundSubsystem {
   
	private Relay flashlight = HardwareAdaptor.kFlashlight;
	private static FlashlightSubsystem instance;
	
	private FlashlightSubsystem(){
		flashlight.setDirection(Relay.Direction.kForward);
	}
	
	public static FlashlightSubsystem getInstance()  {
		return instance == null ? instance = new FlashlightSubsystem() : instance;
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

