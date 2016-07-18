package com.techhounds.lib.util;

import java.util.Timer;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.command.Command;

public abstract class Command200Hz extends Command {

	private Timer timer;
	private double FREQUENCY;
	private boolean isFinished;
	
	public Command200Hz() { this(200);}
	public Command200Hz(double frequency) { this.FREQUENCY = frequency; }
	
	protected abstract void init();
	protected abstract void doRun();
	protected abstract boolean doFinish();
	
	@Override
	protected void initialize() {
		isFinished = false;
		init();
		timer = new Timer();
        timer.scheduleAtFixedRate(new Action(), 0, (long) ((1 / FREQUENCY) * 1000)); 
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void interrupted() {	
		timer.cancel();
		end(); 
	}
	
	private class Action extends TimerTask {

		@Override
		public void run() {
			Command200Hz.this.doRun();
			
			if(isFinished = Command200Hz.this.doFinish()) {
				cancel();
			}
		}
		
	}
}
