package com.techhounds.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ContinueDebug extends Command {

	public static boolean continueDebug;
	
	@Override
	protected void initialize() {
		continueDebug = true;
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		continueDebug = false;
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
