package com.techhounds.frc2016;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.techhounds.lib.trajectory.Trajectory;
import com.techhounds.lib.trajectory.TrajectoryPair;

public class ProfileGenerator {

	private static ProfileGenerator instance;
	public static ArrayList<TrajectoryPair> loadedTrajectories;
	public static Trajectory leftRecorded, rightRecorded;
	
	public static final String [] FILES_TO_LOAD = {
			"2_BALL_LOWBAR_PT1"
	};
	
	private ProfileGenerator() {
		
		loadedTrajectories = new ArrayList<>();
		
		for(int i = 0; i < FILES_TO_LOAD.length; i++) {
			
			try {

				ArrayList<Trajectory.Segment> left = new ArrayList<>();
				ArrayList<Trajectory.Segment> right = new ArrayList<>();

				Scanner file = new Scanner(new File("/trajectory/" + FILES_TO_LOAD[i] + "/LEFT.txt"));
				Scanner file2 = new Scanner(new File("/trajectory/" + FILES_TO_LOAD[i] + "/RIGHT.txt"));
				
				while(file.hasNextLine()) {
					left.add(getSeg(file.nextLine()));
				}
				
				while(file2.hasNextLine()) {
					right.add(getSeg(file2.nextLine()));
				}
				
				file.close();
				file2.close();
				
				Trajectory.Segment[] leftArr = new Trajectory.Segment[left.size()];
				Trajectory.Segment[] rightArr = new Trajectory.Segment[right.size()];
				
				left.toArray(leftArr);
				right.toArray(rightArr);
				
				loadedTrajectories.add(new TrajectoryPair(new Trajectory(leftArr), new Trajectory(rightArr)));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

	public Trajectory.Segment getSeg(String field) {
		
		String [] values = field.split(",");
		
		double dt = Double.parseDouble(values[0]);
		double x = Double.parseDouble(values[1]);
		double y = Double.parseDouble(values[2]);
		double position = Double.parseDouble(values[3]);
		double velocity = Double.parseDouble(values[4]);
		double acceleration = Double.parseDouble(values[5]);
		double jerk = Double.parseDouble(values[6]);
		double heading = Double.parseDouble(values[7]);
		
		return new Trajectory.Segment(dt, x, y, position, velocity, acceleration, jerk, heading);
	}
	
	public static ProfileGenerator getInstance() {
		return instance == null ? instance = new ProfileGenerator() : instance;
	}
}
