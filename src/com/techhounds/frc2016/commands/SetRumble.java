package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.OI;

import edu.wpi.first.wpilibj.command.Command;

public class SetRumble extends Command {
	
	public SetRumble() {
		
	}
	
	@Override
	protected void initialize() {
		OI.getInstance().getDriver().startRumble();
		OI.getInstance().getOperator().startRumble();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return timeSinceInitialized() > 5;
	}

	@Override
	protected void end() {
		OI.getInstance().getOperator().stopRumble();
		OI.getInstance().getDriver().stopRumble();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
