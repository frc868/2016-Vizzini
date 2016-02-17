package com.techhounds.lib.hid;

import java.util.HashMap;

import com.techhounds.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControllerMap {
	
	private HashMap<Integer, JoystickButton> buttons;
	
	private Joystick joystick;
	private Type type;
	
	private int [] buttonPorts;
	
	private double DEADZONE = 0.05;
	
	public interface Key {
		
		// XBOX AND LOGITECH KEYS && UNIVERSAL AXIS KEYS
		int A = 0, B = 1, X = 2, Y = 3, RB = 4, RT = 5, LB = 6,
			LT = 7, START = 8, BACK = 9;
		
		// PLAYSTATION KEYS (START = 8 but already defined)
		int CROSS = 0, CIRCLE = 1, SQUARE = 2, TRIANGLE = 3, R1 = 4, R2 = 5, L1 = 6,
	    		L2 = 7, SELECT = 9, OPTIONS = 8, SHARE = 9;
	}
	
	public interface Direction {
		
		int LEFT_HORIZONTAL = 10,
	  			RIGHT_HORIZONTAL = 11, LEFT_VERTICAL = 12, RIGHT_VERTICAL = 13;
	}
	
	protected static final int[] logitech =	{ 2, 3, 1, 4, 6,  8, 5,  7, 10, 9, 0, 2, 1, 3};	
    protected static final int[] xbox360 = 	{ 1, 2, 3, 4, 6, -1, 5, -1,  8, 7, 0, 4, 1, 5};
    protected static final int[] ps4 =		{ 2, 3, 1, 4, 6,  8, 5,  7, 10, 9, 0, 2, 1, 5};
    protected static final int[] xboxOne = 	{ 1, 2, 3, 4, 6,  3, 5,  2,  8, 7, 0, 4, 1, 5};
    		
	public ControllerMap(Joystick joystick) {
		this(joystick, Type.LOGITECH);
	}
	
	public ControllerMap(Joystick joystick, Type type) {
		this.joystick = joystick;
		buttons = new HashMap<>();
		setControllerType(type);
	}
	
	public void setControllerType(Type type) {
		this.type = type;
		
		switch(type){
			case PS4:
				buttonPorts = ps4; 
				break;
			case LOGITECH:
				buttonPorts = logitech;
				break;
			case XBOX_ONE:
				buttonPorts = xboxOne;
				break;
			case XBOX_360:
				buttonPorts = xbox360;
				break;
			default:
				buttonPorts = logitech;
				break;
		}
		
		// Iterate through all possible Joystick buttons to replace type
		for(int buttonID = 0; buttonID < 10; buttonID++) {
			JoystickButton button = buttons.get(buttonID);
			if(button != null) {
				button.setOff(true);
				if((type == Type.XBOX_ONE || type == Type.XBOX_360) && (buttonID == Key.LT  || buttonID == Key.RT))
					buttons.replace(buttonID, new TriggerButton(joystick, buttonID));
				else
					buttons.replace(buttonID, new JoystickButton(joystick, buttonPorts[buttonID]));

				SmartDashboard.putNumber("Button Selected " + buttonID, buttonPorts[buttonID]);
			}
		}
	}
	public double getRightPower(){
		return Robot.rangeCheck(getAxis(Direction.LEFT_VERTICAL) + getAxis(Direction.RIGHT_HORIZONTAL));
	}
	public double getLeftPower(){
		return Robot.rangeCheck(getAxis(Direction.LEFT_VERTICAL) - getAxis(Direction.RIGHT_HORIZONTAL));
	}
	public Type getType() {
		return type;
	}
	
	public double getAxis(int direction) {
		return checkDeadZone(joystick.getRawAxis(buttonPorts[direction]));
	}
	
	public Button getButton(Integer buttonID) {
		if(buttons.get(buttonID) == null) {
			if(DPadButton.isDPADButton(buttonID))
				buttons.put(buttonID, new DPadButton(joystick, buttonID));
			else if((type == Type.XBOX_ONE || type == Type.XBOX_360) && 
					(buttonID == Key.LT  || buttonID == Key.RT))
				buttons.put(buttonID, new TriggerButton(joystick, buttonID));
			else
				buttons.put(buttonID, new JoystickButton(joystick, buttonPorts[buttonID]));
		}
		
		if(!DPadButton.isDPADButton(buttonID))
			SmartDashboard.putNumber("Button Selected " + buttonID, buttonPorts[buttonID]);
		
		return buttons.get(buttonID);
	}
	
	private double checkDeadZone(double value) {
		return Math.abs(value) < DEADZONE ? 0 : value;
	}
	
	public enum Type {
		PS4, LOGITECH, XBOX_ONE, XBOX_360
	}
	
}


