package com.techhounds.frc2016.commands;

import edu.wpi.first.wpilibj.command.Command;

public class CreateRumble extends Command {

	@Override
	protected void initialize() {
		(new SetRumble()).start();
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
