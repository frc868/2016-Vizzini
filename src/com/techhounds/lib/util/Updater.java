package com.techhounds.lib.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Updater {

	private Vector<Updateable> updateables = new Vector<>();
	private Timer timer;
	private double period;
	
	public Updater(double period) {
		this.period = period;
	}
	
	public void addUpdateable(Updateable updateable) {
		updateables.addElement(updateable);
	}
	
	public void start() {
		if(timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new UpdaterTask(), 0, (long) ((1 / period) * 1000)); 
		}
	}
	
	public void stop() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	public class UpdaterTask extends TimerTask {

		@Override
		public void run() {
			for(Updateable updateable : updateables) {
				updateable.update();
			}
		}
		
	}
}
