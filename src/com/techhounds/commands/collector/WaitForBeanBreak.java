package com.techhounds.commands.collector;

import com.techhounds.lib.util.Command200Hz;
import com.techhounds.subsystems.BeamBreakSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WaitForBeanBreak extends Command200Hz {

	private BeamBreakSubsystem beanBreak;
	private boolean waitForIn;

	public WaitForBeanBreak(boolean waitForIn) {
		beanBreak = BeamBreakSubsystem.getInstance();
		this.waitForIn = waitForIn;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected void init() {
	}

	@Override
	protected void doRun() {
	}

	@Override
	protected boolean doFinish() {
		return waitForIn == beanBreak.ballPresent();
	}

}
