package com.techhounds.hid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Implement our own Joystick Button class based on our custom Button class
 * @author Atif Niyaz
 */
public class JoystickButton extends Button {

	protected GenericHID joystick;
	protected int buttonNumber;
	
	protected JoystickButton(GenericHID joystick) {
		this.joystick = joystick;
	}

	public JoystickButton(GenericHID joystick, int buttonNumber) {
		this.joystick = joystick;
		this.buttonNumber = buttonNumber;
	}
	
	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}
	
	@Override
	public boolean get() {
		return joystick.getRawButton(buttonNumber);
	}
}

