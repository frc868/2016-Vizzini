package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem{

	// TODO: Add Encoders
	// TODO: Add Implementation for Set Drive Power (maybe Voltage?)
	// TODO: Add Drive Distance PID Controller
	// TODO: Add Gyro Turn PID Controller
	
	private Spark left;
	private Spark right;
	private boolean rotating;
	private PIDController pid;
	

	private Encoder rightEncoder;
	private Encoder leftEncoder;

	private final double driver_p;
	private final double driver_i;
	private final double driver_d;
	
	
	private final double gyro_p;
	private final double gyro_i;
	private final double gyro_d;
	
	private final double percentTolerable = .1;

	
	private static DriveSubsystem instance;
	
	private DriveSubsystem() {		
		left = new Spark(RobotMap.DriveTrain.DRIVE_LEFT_MOTOR);
		left.setInverted(RobotMap.DriveTrain.DRIVE_LEFT_IS_INVERTED);
		
		right = new Spark(RobotMap.DriveTrain.DRIVE_RIGHT_MOTOR);
		right.setInverted(RobotMap.DriveTrain.DRIVE_RIGHT_IS_INVERTED);

		rightEncoder = new Encoder(RobotMap.DriveTrain.ENC_RIGHT_A, RobotMap.DriveTrain.ENC_RIGHT_B);
		leftEncoder = new Encoder(RobotMap.DriveTrain.ENC_LEFT_A, RobotMap.DriveTrain.ENC_LEFT_B);
		
		LiveWindow.addActuator("drive", "left motors", left);
		LiveWindow.addActuator("drive", "right motors", right);
		LiveWindow.addSensor("drive", "left encoder", leftEncoder);
		LiveWindow.addSensor("drive", "right encoder", rightEncoder);

		
		driver_p = 0.0001;
		driver_i = 0;
		driver_d = 0;
		
		
		gyro_p = 0.0001;
		gyro_i = 0;
		gyro_d = 0;

	}
	
	public static DriveSubsystem getInstance() {
		if(instance == null) 
			instance = new DriveSubsystem();
		return instance;
	}



	public void setLeftPower(double speed) {
		if(speed > 1){
			speed = 1;
		}
		else if(speed < -1){
			speed = -1;
		}

		left.set(speed);
	}
	

	public void setRightPower(double speed) {
		if(speed > 1){ 
			speed = 1;
	}
		else if(speed < -1){
			speed = -1;
	}

		right.set(speed);
	}
	
	public void setPower(double right, double left) {
		this.left.set(left);
		this.right.set(right);
	}
	
	public double getLeftPower() {
		return left.get();
	}
	
	public double getRightPower() {
		return right.get();
	}
	
	public double getLeftSpeed(){
		return leftEncoder.getRate();
	}
	
	public double getRightSpeed(){
		return rightEncoder.getRate();
	}
	
	public double getAvgSpeed() {
		return (getLeftSpeed() + getRightSpeed()) / 2;
	}
	
	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}
	
	public double getRightDistance() {
		return rightEncoder.getDistance();
	}
	
	public double getAvgDistance() {
		return (getLeftDistance() + getRightDistance()) / 2;
	}
	
	public double getPIDError() {
		return pid.getError();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Driver Left Speed", getLeftPower());
		SmartDashboard.putNumber("Driver Right Speed", getRightPower());
		SmartDashboard.putNumber("Driver PID Error", getPIDError());
	}

	protected void initDefaultCommand() {
		// TODO: Add Default command for DriveWithGamepad()
	}

	public double getDistance() {
		
		return 0;
	}
}
