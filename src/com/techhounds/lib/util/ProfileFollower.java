package com.techhounds.lib.util;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

public class ProfileFollower {
	
	private Trajectory trajectory;
	private Double Kp, Ki, Kd, Kv, Ka, Kturn;
	
	private int currSeg;
	private double lastError;
	
	public ProfileFollower(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public ProfileFollower(Trajectory trajectory, double Kp, double Ki, double Kd, double Kv, double Ka) {
		this(trajectory);
		setGains(Kp, Ki, Kd, Kv, Ka);
	}
	
	public ProfileFollower(Trajectory trajectory, double Kp, double Ki, double Kd, double Kv, double Ka, double Kturn) {
		this(trajectory);
		setGains(Kp, Ki, Kd, Kv, Ka, Kturn);
	}

	public void setGains(double Kp, double Ki, double Kd, double Kv, double Ka) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kp = Kp;
		this.Kv = Kv;
		this.Ka = Ka;
	}
	
	public void setGains(double Kp, double Ki, double Kd, double Kv, double Ka, double Kturn) {
		setGains(Kp, Ki, Kd, Kv, Ka);
		this.Kturn = Kturn;
	}
	
	public void reset() {
		lastError = 0;
		currSeg = 0;
	}
	
	public boolean isFinished() {
		return currSeg > trajectory.length() - 1;
	}
	
	public double calculateOutput(double currDistance) {
		if(Kp == null || Ki == null || Kd == null || Kv == null || Ka == null 
				|| isFinished()) {
			return 0;
		}
		
		Trajectory.Segment segment = trajectory.get(currSeg);
			
		double error = currDistance - segment.position;
		
		double output =
				Kp * error + 													// Kp
				Kd * ((error - lastError) / segment.dt - segment.velocity) + 	// Kd
				
				// Feedforward Terms
				Kv * segment.velocity +						// Kv
				Ka * segment.acceleration;					// Ka
		
		lastError = error;
		currSeg++;
		
		return output;
	}
	
	public double getHeading() {
		if(isFinished())
			return 0;
		return trajectory.get(currSeg).heading;
	}
	
	public double calculateHeading(double currHeading) {
		if(Kturn == null || isFinished())
			return 0;
		return Kturn * (Pathfinder.boundHalfDegrees(trajectory.get(currSeg).heading) - Pathfinder.boundHalfDegrees(currHeading));
	}
}
