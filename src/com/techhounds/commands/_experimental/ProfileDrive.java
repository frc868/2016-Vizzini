package com.techhounds.commands._experimental;

import com.techhounds.TrajectoryLoader;
import com.techhounds.TrajectoryPair;
import com.techhounds.lib.util.Command200Hz;
import com.techhounds.lib.util.ProfileFollower;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import jaci.pathfinder.Pathfinder;
//import jaci.pathfinder.Trajectory;
//import jaci.pathfinder.followers.DistanceFollower;
//import jaci.pathfinder.modifiers.TankModifier;

public class ProfileDrive extends Command200Hz {

	private DriveSubsystem drive;
//	private DistanceFollower leftFollower, rightFollower;
	
	private double currHeading;
	
//	private Trajectory left, right;
	private String savedProfileCount;
	
	private double Kp = 0.0, Ki = 0.0, Kd = 0.0, Kv = 1 / 105.0, Ka = 0.00007, Kturn = 0;
	
	public ProfileDrive() {
		super(100);
		requires(drive = DriveSubsystem.getInstance());
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean doFinish() {
		// TODO Auto-generated method stub
		return false;
	}

/*	public ProfileDrive(Trajectory left, Trajectory right) {
		this();
		this.left = left;
		this.right = right;
	}
	
	public ProfileDrive(String savedProfileCount, boolean doesntMatter) {
		this();
		this.savedProfileCount = savedProfileCount;
	}
	
	@Override
	protected void end() {
		drive.setPower(0, 0);
	}

	@Override
	protected void init() {
		if(savedProfileCount != null) {
			TrajectoryPair traj = TrajectoryLoader.loadedTrajectories.get(savedProfileCount);
			left = traj.getLeft();
			right = traj.getRight();
		} else if(left == null || right == null) {
			left = TrajectoryLoader.recorded.getLeft();
			right = TrajectoryLoader.recorded.getRight();
		} else {
		}
		
		leftFollower = new DistanceFollower(left);
		rightFollower = new DistanceFollower(right);
		
        leftFollower.configurePIDVA(Kp, Ki, Kd, Kv, Ka);
        rightFollower.configurePIDVA(Kp, Ki, Kd, Kv, Ka);
        
        drive.encodersReset();
        currHeading = GyroSubsystem.getInstance().getRotation();
	}

	@Override
	protected void doRun() {
		double leftSpeed = leftFollower.calculate(drive.getLeftDistance());
		double rightSpeed = rightFollower.calculate(drive.getRightDistance());

		double turn = 0;//leftFollower.calculateHeading(drive.getRotationX() - ProfileDrive.this.currHeading);
		
		double goalHeading = Pathfinder.boundHalfDegrees(leftFollower.getHeading());
		double currHeading = Pathfinder.boundHalfDegrees(drive.getRotationX() - ProfileDrive.this.currHeading);
		double angleDiff = goalHeading - currHeading;

		turn = 0;// Kturn * angleDiff;//0;//calculateTurn(angleDiff);
		
		drive.setPower(rightSpeed - turn, leftSpeed + turn);
	}

	@Override
	protected boolean doFinish() {
		return leftFollower.isFinished();
	}
	*/
}
