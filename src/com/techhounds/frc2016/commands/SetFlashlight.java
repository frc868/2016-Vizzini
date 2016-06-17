package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.subsystems.Flashlight;

import edu.wpi.first.wpilibj.command.Command;

public class SetFlashlight extends Command {

	private Boolean on;
	private Flashlight flashlight;
	
	public SetFlashlight() {
		setRunWhenDisabled(true);
		flashlight = Flashlight.getInstance();
		this.on = null;
	}
	
	public SetFlashlight(boolean on) {
		setRunWhenDisabled(true);
		flashlight = Flashlight.getInstance();
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
