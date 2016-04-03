package com.techhounds.commands;

import com.techhounds.subsystems.FlashlightSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SetFlashlight extends Command {

	private Boolean on;
	private FlashlightSubsystem flashlight;
	
	public SetFlashlight() {
		flashlight = FlashlightSubsystem.getInstance();
		this.on = null;
	}
	
	public SetFlashlight(boolean on) {
		setRunWhenDisabled(true);
		flashlight = FlashlightSubsystem.getInstance();
		this.on = on;
	}
	
	@Override
	protected void initialize() {
		if(on == null) {
			flashlight.toggle();
		} else {
			if(on) { 
				flashlight.on();
			} else {
				flashlight.off();
			}
		}	
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
