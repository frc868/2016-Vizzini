package com.techhounds.commands.collector;

import com.techhounds.subsystems.BeamBreakSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WaitForBeanBreak extends Command {

	private BeamBreakSubsystem beanBreak;
	private boolean waitForIn;

	public WaitForBeanBreak(boolean waitForIn) {
		beanBreak = BeamBreakSubsystem.getInstance();
		this.waitForIn = waitForIn;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return waitForIn == beanBreak.ballPresent();
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
