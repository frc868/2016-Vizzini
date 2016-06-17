package com.techhounds.frc2016.commands._experimental;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.techhounds.frc2016.ProfileGenerator;
import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.lib.trajectory.Trajectory;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.PeriodicCommand;


public class RecordProfile extends PeriodicCommand {
	
	private DriveSubsystem drive = DriveSubsystem.getInstance();
	private GyroSubsystem gyro = GyroSubsystem.getInstance();
	
	private double lastT, lastLeftVelocity, lastRightVelocity;
	private double lastLeftAcc, lastRightAcc;
	private double currHeading;
	
	private double maxAcc;
	private double minAcc;
	
	public ArrayList<Trajectory.Segment> segmentsLeft = new ArrayList<>();
	public ArrayList<Trajectory.Segment> segmentsRight = new ArrayList<>();
	
	@Override
	protected void init() {
		lastT = System.currentTimeMillis();
		lastLeftVelocity = 0;
		lastRightVelocity = 0;
		maxAcc = 0;
		
		segmentsLeft = new ArrayList<>();
		segmentsRight = new ArrayList<>();
		
		drive.resetEncoders();
		currHeading = gyro.getRotation();
	}

	@Override
	protected void doRun() {
		
		double dt = 0.005;
		
		double x = dt / 0.005;
		double y = dt / 0.005;
		
		double leftPosition = drive.getLeftDistance();
		double rightPosition = drive.getRightDistance();
		
		double leftVelocity = drive.getLeftSpeed();
		double rightVelocity = drive.getRightSpeed();
		
		double currT = timeSinceInitialized();
		double measuredDt = (currT - lastT);
		lastT = currT;
		
		double leftAcceleration = (leftVelocity - lastLeftVelocity) / measuredDt;
		double rightAcceleration = (rightVelocity - lastRightVelocity) / measuredDt;
		
		if(leftAcceleration > maxAcc) {
			maxAcc = leftAcceleration;
		}
		
		if(leftAcceleration < minAcc) {
			minAcc = leftAcceleration;
		}
		
		double leftJerk = (leftAcceleration - lastLeftAcc) / measuredDt;
		double rightJerk = (rightAcceleration - lastRightAcc) / measuredDt;
		
		double heading = HoundMath.boundAngleNeg180to180Degrees(gyro.getRotation() - currHeading);
		
		lastLeftVelocity = leftVelocity;
		lastRightVelocity = rightVelocity;
		
		lastLeftAcc = leftAcceleration;
		lastRightAcc = rightAcceleration;
		
		Trajectory.Segment segmentLeft = new Trajectory.Segment(dt, x, y, leftPosition, leftVelocity, leftAcceleration, leftJerk, heading);
		Trajectory.Segment segmentRight = new Trajectory.Segment(dt, x, y, rightPosition, rightVelocity, rightAcceleration, rightJerk, heading);
		
		segmentsLeft.add(segmentLeft);
		segmentsRight.add(segmentRight);
	}

	@Override
	protected boolean doFinish() {
		return timeSinceInitialized() > 20;
	}

	@Override
	protected void end() {
		Trajectory.Segment[] left = new Trajectory.Segment[segmentsLeft.size()];
		Trajectory.Segment[] right = new Trajectory.Segment[segmentsRight.size()];
		
		Thread thread = new Thread(new SaveProfile());
		thread.start();

		segmentsLeft.toArray(left);
		segmentsRight.toArray(right);

		ProfileGenerator.leftRecorded = new Trajectory(left);
		ProfileGenerator.rightRecorded = new Trajectory(right);
	}
	
	public class SaveProfile implements Runnable {

		@Override
		public void run() {

			Trajectory.Segment[] left = new Trajectory.Segment[segmentsLeft.size()];
			Trajectory.Segment[] right = new Trajectory.Segment[segmentsRight.size()];
			
			segmentsLeft.toArray(left);
			segmentsRight.toArray(right);
			
			Date date = Calendar.getInstance().getTime();
			String currTime = date.getDate() + "-" + (1 + date.getMonth()) + "-" + (1900 + date.getYear()) + "-AT-" + date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();
			
			String leftFile = "/trajectory/" + currTime + "/LEFT.txt";
			String rightFile = "/trajectory/" + currTime + "/RIGHT.txt";
			
			File leftFile1 = new File(leftFile);
			File rightFile1 = new File(rightFile);
		
			try {
				
				leftFile1.getParentFile().mkdirs();
				leftFile1.createNewFile();
				rightFile1.createNewFile();
				
				PrintWriter toFileLeft = new PrintWriter(leftFile1);
				PrintWriter toFileRight = new PrintWriter(rightFile1);
				
				for(Trajectory.Segment seg : left) {
					toFileLeft.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
						        seg.dt, seg.x, seg.y, seg.pos, seg.vel, 
						            seg.acc, seg.jerk, seg.heading);
				}
				
				for(Trajectory.Segment seg : right) {
					toFileRight.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
					        seg.dt, seg.x, seg.y, seg.pos, seg.vel, 
					            seg.acc, seg.jerk, seg.heading);
				}
				
				toFileLeft.close();
				toFileRight.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
