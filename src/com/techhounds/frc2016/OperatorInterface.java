package com.techhounds.frc2016;

import com.techhounds.frc2016.behavior.Commands;
import com.techhounds.frc2016.behavior.Commands.Angler;
import com.techhounds.frc2016.behavior.Commands.Collector;
import com.techhounds.frc2016.behavior.Commands.Flashlight;
import com.techhounds.frc2016.behavior.Commands.Shooter;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.ControllerMap.Direction;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.util.HoundMath;

public class OperatorInterface {
	
	Commands commands = new Commands();

	private static ControllerMap driver = HardwareAdaptor.kDriverGamepad;
	private static ControllerMap operator = HardwareAdaptor.kOperatorGamepad;
	
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
	
	public void reset() {
		commands = new Commands();
	}
	
	public Commands getCommands() {
		
		
		isGoingToFire = (driver.getButton(COLLECTOR_FIRE).get() || operator.getButton(COLLECTOR_FIRE).get());
				
		// Collector Control
		if(driver.getButton(COLLECTOR_IN).get() || operator.getButton(COLLECTOR_IN).get()) {
			commands.m_collector = Collector.IN;
		} else if(driver.getButton(COLLECTOR_OUT).get() || operator.getButton(COLLECTOR_OUT).get()) {
			commands.m_collector = Collector.OUT;
		} else if(isGoingToFire) {
			commands.m_collector = Collector.FIRE;
		} else {
			commands.m_collector = Collector.STOP;
		}
		
		// Angler Control
		if((driver.getButton(ANGLER_UP).get() || operator.getButton(ANGLER_UP).get()) &&
				lastAnglerButtonPressed != ANGLER_UP) {
			commands.m_angler = Angler.UP;
			lastAnglerButtonPressed = ANGLER_UP;
		} else if((driver.getButton(ANGLER_DOWN).get() || operator.getButton(ANGLER_DOWN).get())
				&& lastAnglerButtonPressed != ANGLER_DOWN) {
			commands.m_angler = Angler.DOWN;
			lastAnglerButtonPressed = ANGLER_DOWN;
		} else {
			commands.m_angler = Angler.DO_NOTHING;
			
			if(!(driver.getButton(ANGLER_UP).get() || operator.getButton(ANGLER_UP).get()
					|| driver.getButton(ANGLER_DOWN).get() || operator.getButton(ANGLER_DOWN).get()))
				lastAnglerButtonPressed = null;
		}
		
		// Shooter Control
		if(!isGoingToFire) {
			if(lastShooterButtonPressed != SHOOTER_START && 
					(driver.getButton(SHOOTER_START).get() || operator.getButton(SHOOTER_START).get())) {
				commands.m_shooter = Shooter.START;
			} else if(lastShooterButtonPressed != SHOOTER_STOP &&
					(driver.getButton(SHOOTER_STOP).get() || operator.getButton(SHOOTER_STOP).get())) {
				commands.m_shooter = Shooter.STOP;
			} else {
				commands.m_shooter = Shooter.DO_NOTHING;
				
				if(!(driver.getButton(SHOOTER_START).get() || operator.getButton(SHOOTER_START).get() ||
						driver.getButton(SHOOTER_STOP).get() || operator.getButton(SHOOTER_STOP).get()))
					lastShooterButtonPressed = null;
			}
		} else {
			lastShooterButtonPressed = null;
		}
		
		// Flashlight Control
		if(driver.getButton(TOGGLE_FLASHLIGHT).get() || operator.getButton(TOGGLE_FLASHLIGHT).get()) {
			if(flashlightButtonReleased)
				commands.m_flashlight = Flashlight.TOGGLE;
			else
				commands.m_flashlight = Flashlight.DO_NOTHING;
			
			flashlightButtonReleased = false;
		} else {
			flashlightButtonReleased = true;
			commands.m_flashlight = Flashlight.DO_NOTHING;
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
