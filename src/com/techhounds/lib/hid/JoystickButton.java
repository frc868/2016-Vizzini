package com.techhounds.lib.hid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Implement our own Joystick Button class based on our custom Button class
 * @author Atif Niyaz
 */
public class JoystickButton extends Button {

	protected GenericHID joystick;
	protected int buttonNumber;
	protected boolean off;
	
	protected JoystickButton(GenericHID joystick) {
		this.joystick = joystick;
		this.off = false;
	}

	public JoystickButton(GenericHID joystick, int buttonNumber) {
		this.joystick = joystick;
		this.buttonNumber = buttonNumber;
		this.off = false;
	}
	
	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}
	
	public void setOff(boolean off) {
		this.off = off;
	}
	
	@Override
	public boolean get() {
		if(off)
			return false;
		
		return joystick.getRawButton(buttonNumber);
	}
}

