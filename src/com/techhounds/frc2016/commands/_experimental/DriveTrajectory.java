package com.techhounds.frc2016.commands._experimental;

import com.techhounds.frc2016.ProfileGenerator;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.lib.trajectory.Trajectory;
import com.techhounds.lib.trajectory.TrajectoryFollower;
import com.techhounds.lib.trajectory.TrajectoryPair;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.PeriodicCommand;

public class DriveTrajectory extends PeriodicCommand {

	private DriveSubsystem drive = DriveSubsystem.getInstance();//RobotAdaptor.kDriveSubsystem;
	private GyroSubsystem gyro = GyroSubsystem.getInstance();//RobotAdaptor.kGyroSubsystem;
	
	private TrajectoryFollower leftFollower, rightFollower;
	
	private double currHeading;
	
	private Trajectory left, right;
	private Integer modifierCount, savedProfileCount;
	
	private final double 
		Kp = 1.0, 
		Ki = 0.0, 
		Kd = 0.0, 
		Kv = 1 / 125, 
		Ka = 1 / 250, 
		Kturn = 0.0375;
	
	public DriveTrajectory() {
		requires(drive);
	}
	
	public DriveTrajectory(int modifierCount) {
		this();
		this.modifierCount = modifierCount;
	}
	
	public DriveTrajectory(Trajectory left, Trajectory right) {
		this();
		this.left = left;
		this.right = right;
	}
	
	public DriveTrajectory(int savedProfileCount, boolean doesntMatter) {
		this();
		this.savedProfileCount = savedProfileCount;
	}
	
	@Override
	protected void end() {
		drive.setPower(0, 0);
	}

	@Override
	protected void init() {
		
		if(modifierCount != null) {
			//TankModifier modifier = ProfileGenerator.getInstance().getModifier(modifierCount);
			//left = modifier.getLeftTrajectory();
			//right = modifier.getRightTrajectory();
		} else if(savedProfileCount != null) {
			TrajectoryPair traj = ProfileGenerator.loadedTrajectories.get(savedProfileCount);
			left = traj.getLeft();
			right = traj.getRight();
		} else if(left == null || right == null) {
			left = ProfileGenerator.leftRecorded;
			right = ProfileGenerator.rightRecorded;
		} else {
		}
		
		leftFollower = new TrajectoryFollower(left);
		rightFollower = new TrajectoryFollower(right);
		
        leftFollower.setGains(Kp, Ki, Kd, Kv, Ka);
        rightFollower.setGains(Kp, Ki, Kd, Kv, Ka);
        
        drive.resetEncoders();
        currHeading = GyroSubsystem.getInstance().getRotation();
	}

	@Override
	protected void doRun() {
		double leftSpeed = leftFollower.calculateOutput(drive.getLeftDistance());
		double rightSpeed = rightFollower.calculateOutput(drive.getRightDistance());

		double turn = 0;//leftFollower.calculateHeading(drive.getRotationX() - ProfileDrive.this.currHeading);
		
		double goalHeading = HoundMath.boundAngleNeg180to180Degrees(leftFollower.getHeading());
		double currHeading = HoundMath.boundAngleNeg180to180Degrees(gyro.getRotation() - DriveTrajectory.this.currHeading);
		double angleDiff = goalHeading - currHeading;

		turn = 0;// Kturn * angleDiff;//0;//calculateTurn(angleDiff);
		
		drive.setPower(rightSpeed - turn, leftSpeed + turn);
	}

	@Override
	protected boolean doFinish() {
		return leftFollower.isFinished();
	}
}
