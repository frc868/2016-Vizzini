package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class LEDSubsystem extends Subsystem {

	private DigitalOutput led, led2;
	public enum mode {FORWARDUP, REVERSEUP, FORWARDDOWN, REVERSEDOWN};
	
	public static LEDSubsystem instance;
	
	private LEDSubsystem(){
		led = new DigitalOutput(RobotMap.LED.DIO_MODE_0);
		led2= new DigitalOutput(RobotMap.LED.DIO_MODE_1);
		LiveWindow.addActuator("LED", "Mode Bit 0", led);
		LiveWindow.addActuator("LED", "Mode Bit 1", led2);
	}
	
	public void set(boolean state, boolean state2){
		led.set(state);
		led2.set(state2);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static LEDSubsystem getInstance(){
    	if(instance==null) {
    		instance = new LEDSubsystem();
    	}
    	return instance;
    }
}

