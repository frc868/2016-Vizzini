package com.techhounds.lib.util;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

public class ProfileSpeedFollower {
	
	private Trajectory trajectory;
	private Double Kp, Ki, Kd, Kv, Ka, Kturn;
	
	private int currSeg;
	private double lastError, sumError;
	
	public ProfileSpeedFollower(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public ProfileSpeedFollower(Trajectory trajectory, double Kp, double Ki, double Kd, double Kv, double Ka) {
		this(trajectory);
		setGains(Kp, Ki, Kd, Kv, Ka);
	}
	
	public ProfileSpeedFollower(Trajectory trajectory, double Kp, double Ki, double Kd, double Kv, double Ka, double Kturn) {
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
		sumError = 0;
	}
	
	public boolean isFinished() {
		return currSeg > trajectory.length() - 1;
	}
	
	public double calculateOutput(double currVelocity) {
		if(Kp == null || Ki == null || Kd == null || Kv == null || Ka == null 
				|| isFinished()) {
			return 0;
		}
		
		Trajectory.Segment segment = trajectory.get(currSeg);
			
		double error = currVelocity - segment.velocity;
		sumError += error;
		
		double output =
				Kp * error + 														// Kp
				///Ki * sumError +														// Ki
				Kd * ((error - lastError) / segment.dt - segment.acceleration) + 	// Kd
				
				// Feedforward Terms
				Kv * segment.velocity +						// Kv
				Ka * segment.acceleration;					// Ka
		
		lastError = error;
		currSeg++;
		
		return output;
	}
}
