package com.techhounds.hid;

import com.techhounds.hid.ControllerMap.Key;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

public class TriggerButton extends JoystickButton {
	
	private Hand hand;

	public TriggerButton(Joystick joystick, int buttonID) {
		super(joystick);
		
		if(buttonID == Key.LT)
			hand = Hand.kLeft;
		else if(buttonID == Key.RT)
			hand = Hand.kRight;
		else
			hand = null;
	}
	
	@Override
	public boolean get() {
		if(hand == Hand.kLeft)
			return joystick.getRawAxis(2) > .6;
		else if(hand == Hand.kRight)
			return joystick.getRawAxis(3) > .6;
		else
			return false;
	}
}
