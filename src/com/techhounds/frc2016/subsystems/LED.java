package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.lib.util.HoundSubsystem;
import com.techhounds.lib.util.Updateable;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 */
public class LED extends HoundSubsystem implements Updateable {

	private DigitalOutput 
		led_1 = HardwareAdaptor.kLED_1, 
		led_2 = HardwareAdaptor.kLED_2, 
		collect = HardwareAdaptor.kLED_COLLECTOR;
	
	private Shooter shooter = HardwareAdaptor.kShooterSubsystem;
	private BeamBreak beamBreak = HardwareAdaptor.kBeamBreakSubsystem;
	
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
		
    }
    
    public static LED getInstance() {
    	return instance == null ? instance = new LED() : instance;
    }

	@Override
	public void updatePeriodic() {
		
	}

	@Override
	public void update() {
		
		setCollect(beamBreak.ballPresent());
		
		if(shooter.isEnabled()) {
    		if(shooter.getSpeed() < 10 && shooter.getSetPoint() > 10) {
    			set(false, true);
    		} else {
    			if(shooter.onTarget()) {
    				set(true, true);
    			} else {
    				set(true, false);
    			}
    		}
    	} else {
    		set(false, false);
    	}
	}
}

