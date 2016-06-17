package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.commands.SetLEDMode;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 */
public class LED extends HoundSubsystem {

	private DigitalOutput 
		led_1 = HardwareAdaptor.kLED_1, 
		led_2 = HardwareAdaptor.kLED_2, 
		collect = HardwareAdaptor.kLED_COLLECTOR;
	
	public enum LEDMode {
		FORWARDUP, REVERSEUP, FORWARDDOWN, REVERSEDOWN
	}
	
	public static LED instance;
	
	private LED(){
	}
	
	public void set(boolean state, boolean state2){
		led_1.set(state);
		led_2.set(state2);
	}
	
	public void setCollect(boolean state) {
		collect.set(state);
	}
	
	public void initDefaultCommand() {
    	setDefaultCommand(new SetLEDMode());
    }
    
    public static LED getInstance() {
    	return instance == null ? instance = new LED() : instance;
    }

	@Override
	public void updatePeriodic() {
		
	}
}

