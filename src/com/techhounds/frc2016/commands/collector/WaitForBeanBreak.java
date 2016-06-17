package com.techhounds.frc2016.commands.collector;

import com.techhounds.frc2016.subsystems.BeamBreak;
import com.techhounds.frc2016.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WaitForBeanBreak extends Command {

	private BeamBreak beanBreak;
	private boolean waitForIn;

	public WaitForBeanBreak(boolean waitForIn) {
		beanBreak = BeamBreak.getInstance();
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
