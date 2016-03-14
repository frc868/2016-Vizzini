package com.techhounds.commands;

import com.techhounds.OI;

import edu.wpi.first.wpilibj.command.Command;

public class SetRumble extends Command {
	
	private boolean driver, on;
	
	public SetRumble(boolean driver, boolean on) {
		this.driver = driver;
		this.on = on;
	}

	@Override
	protected void initialize() {
		if(driver) {
			if(on) {
				OI.getInstance().getDriver().startRumble();
			} else {
				OI.getInstance().getDriver().stopRumble();
			}
		} else {
			if(on) {
				OI.getInstance().getOperator().startRumble();
			} else {
				OI.getInstance().getOperator().stopRumble();
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
