package com.techhounds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryLoader {
	
	private static TrajectoryLoader instance;
	public static TrajectoryPair recorded;
	
	public static final String [] FILES_TO_LOAD = {
			"LowBarOneBall",				// 2 Ball - First Low Bar Pass
			"LowBarTwoBall-Back",			// 2 Ball - Second Low Bar Pass Backwards
			"LowBarTwoBall-Cross",			// 2 Ball - Second Low Bar Pass
			"LowBarAutoBall-Cross",			// 1 Ball - Low Bar Pass Forward
			"LowBarAutoBall-Back",			// 1 Ball - Low Bar Pass Backward
			"RockWall-Cross",				// Rock Wall Cross
			"RockWall-Back",				// Rock Wall Back
		//	"LowBarOneBallAlt-Cross",				// 2 Ball - First Low Bar Pass
		//	"LowBarTwoBallAlt-Back",			// 2 Ball - Second Low Bar Pass Backwards
		//	"LowBarTwoBallAlt-Cross",			// 2 Ball - Second Low Bar Pass
			"PositionFive-Back",
			"PositionFive-Forward"
	};
	
	public static HashMap<String, TrajectoryPair> loadedTrajectories;
	
	private TrajectoryLoader() {
		loadTrajectories();
	}
	
	public void loadTrajectories() {
		(new LoadFiles()).run();
	}

	public class LoadFiles implements Runnable {
		
		public void run() {
			
			loadedTrajectories = new HashMap<>();
			
			for(int i = 0; i < FILES_TO_LOAD.length; i++) {
				
				Trajectory left2 = Pathfinder.readFromCSV(new File("/trajectory/" + FILES_TO_LOAD[i] + "/left.csv"));
				Trajectory right2 = Pathfinder.readFromCSV(new File("/trajectory/" + FILES_TO_LOAD[i] + "/right.csv"));
				
				loadedTrajectories.put(FILES_TO_LOAD[i], new TrajectoryPair(left2, right2));
			}
		}
	}
	
	public TrajectoryPair getTrajectory(String name) {
		return loadedTrajectories.get(name);
	}
	
	public static TrajectoryLoader getInstance() {
		return instance == null ? instance = new TrajectoryLoader() : instance;
	}
}
