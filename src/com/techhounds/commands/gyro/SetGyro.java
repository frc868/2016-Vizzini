package com.techhounds.commands.gyro;

import edu.wpi.first.wpilibj.command.Command;

public class SetGyro extends Command {
	
	private double angle;
	private boolean displacement;
	
	public SetGyro(double angle) {
		this(angle, false);
	}
	
	public SetGyro(double angle, boolean displacement) {
		this.angle = angle;
		this.displacement = displacement;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
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
