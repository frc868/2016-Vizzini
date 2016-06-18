package com.techhounds.frc2016;

import java.util.ArrayList;

import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.ControllerMap.Direction;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.util.HoundMath;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

	private static ControllerMap driver = new ControllerMap(new Joystick(HardwareConstants.OI.DRIVER_PORT), ControllerMap.Type.XBOX_ONE);
	private static ControllerMap operator = new ControllerMap(new Joystick(HardwareConstants.OI.OPERATOR_PORT), ControllerMap.Type.XBOX_ONE);
	
	public enum Commands {
		COLLECT_IN,
		COLLECT_OUT,
		COLLECT_FIRE,
		COLLECT_STOP,
		
		ANGLER_TO_UP,
		ANGLER_TO_DOWN,
		ANGLER_DO_NOTHING,
		
		SHOOTER_START,
		SHOOTER_STOP,
		SHOOTER_DO_NOTHING,
		
		FLASHLIGHT_TOGGLE,
		FLASHLIGHT_DO_NOTHING,
	}
	
	private static int
		COLLECTOR_IN = ControllerMap.Key.RT,
		COLLECTOR_OUT = ControllerMap.Key.RB,
		COLLECTOR_FIRE = ControllerMap.Key.B,
		
		ANGLER_UP = ControllerMap.Key.Y,
		ANGLER_DOWN = ControllerMap.Key.A,
		
		SHOOTER_START = ControllerMap.Key.X,
		SHOOTER_STOP = DPadButton.Direction.LEFT,
		
		TOGGLE_FLASHLIGHT = DPadButton.Direction.DOWN;
	
	private Integer lastAnglerButtonPressed;
	private Integer lastShooterButtonPressed;
	
	private boolean flashlightButtonReleased = true;
	private boolean isGoingToFire;
	
	public boolean endGameMode = false;
	
	public Commands [] getCommands() {
		
		Commands [] commands = new Commands[7];
		
		isGoingToFire = (driver.getButton(COLLECTOR_FIRE).get() || operator.getButton(COLLECTOR_FIRE).get());
				
		// Collector Control
		if(driver.getButton(COLLECTOR_IN).get() || operator.getButton(COLLECTOR_IN).get()) {
			commands[0] = Commands.COLLECT_IN;
		} else if(driver.getButton(COLLECTOR_OUT).get() || operator.getButton(COLLECTOR_OUT).get()) {
			commands[0] = Commands.COLLECT_OUT;
		} else if(isGoingToFire) {
			commands[0] = Commands.COLLECT_FIRE;
			commands[2] = Commands.SHOOTER_STOP;
		} else {
			commands[0] = Commands.COLLECT_STOP;
		}
		
		// Angler Control
		if((driver.getButton(ANGLER_UP).get() || operator.getButton(ANGLER_UP).get()) &&
				lastAnglerButtonPressed != ANGLER_UP) {
			commands[1] = Commands.ANGLER_TO_UP;
			lastAnglerButtonPressed = ANGLER_UP;
		} else if((driver.getButton(ANGLER_DOWN).get() || operator.getButton(ANGLER_DOWN).get())
				&& lastAnglerButtonPressed != ANGLER_DOWN) {
			commands[1] = Commands.ANGLER_TO_DOWN;
			lastAnglerButtonPressed = ANGLER_DOWN;
		} else {
			commands[1] = Commands.ANGLER_DO_NOTHING;
			lastAnglerButtonPressed = null;
		}
		
		// Shooter Control
		if(!isGoingToFire) {
			if(lastShooterButtonPressed != SHOOTER_START && 
					(driver.getButton(SHOOTER_START).get() || driver.getButton(SHOOTER_START).get())) {
				commands[2] = Commands.SHOOTER_START;
			} else if(lastShooterButtonPressed != SHOOTER_STOP &&
					(driver.getButton(SHOOTER_STOP).get() || driver.getButton(SHOOTER_STOP).get())) {
				commands[2] = Commands.SHOOTER_STOP;
			} else {
				commands[2] = Commands.SHOOTER_DO_NOTHING;
				lastShooterButtonPressed = null;
			}
		} else {
			lastShooterButtonPressed = null;
		}
		
		// Flashlight Control
		if(driver.getButton(TOGGLE_FLASHLIGHT).get() || operator.getButton(TOGGLE_FLASHLIGHT).get()) {
			if(flashlightButtonReleased)
				commands[3] = Commands.FLASHLIGHT_TOGGLE;
			else
				commands[3] = Commands.FLASHLIGHT_DO_NOTHING;
			
			flashlightButtonReleased = false;
		} else {
			flashlightButtonReleased = true;
			commands[3] = Commands.FLASHLIGHT_DO_NOTHING;
		}
		
		return commands;
	}
	
	public double getSteer() {
		if(Math.abs(driver.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL)) < .25)
			return operator.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL) * .5;
		else
			return driver.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL);
	}
	
	public double getRightForward(){
		return HoundMath.checkRange(driver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftForward(){
		return HoundMath.checkRange(driver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
	}
	public double getRightBackward(){
		return HoundMath.checkRange(-driver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftBackward(){
		return HoundMath.checkRange(-driver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
}
}
