package com.techhounds.subsystems;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.techhounds.Robot;
import com.techhounds.RobotMap;
import com.techhounds.commands.drive.DriveWithPower;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	public static boolean isForward = false;
	
	private PIDController gyroPID;
	private PIDController drivePID;
	
	private Encoder rightEncoder;
	private Encoder leftEncoder;

	private final double driver_p;
	private final double driver_i;
	private final double driver_d;
	
	
	private final double gyro_p;
	private final double gyro_i;
	private final double gyro_d;
	
	private final double percentTolerable = .1;
	
	private BuiltInAccelerometer accelerometer;
	private PowerDistributionPanel panel;
	
	private static DriveSubsystem instance;
	private GyroSubsystem gyro;
	private AnalogInput lightSensor;
	
	private boolean debugging = false;
	
	File file;
	BufferedWriter writer;
	FileWriter fw;
	
	private DriveSubsystem() {	
		
		accelerometer = new BuiltInAccelerometer();
		panel = new PowerDistributionPanel();
		gyro = GyroSubsystem.getInstance();
		
		left = new Spark(RobotMap.DriveTrain.DRIVE_LEFT_MOTOR);
		left.setInverted(RobotMap.DriveTrain.DRIVE_LEFT_IS_INVERTED);
		
		right = new Spark(RobotMap.DriveTrain.DRIVE_RIGHT_MOTOR);
		right.setInverted(RobotMap.DriveTrain.DRIVE_RIGHT_IS_INVERTED);

		rightEncoder = new Encoder(RobotMap.DriveTrain.ENC_RIGHT_A, RobotMap.DriveTrain.ENC_RIGHT_B);
		leftEncoder = new Encoder(RobotMap.DriveTrain.ENC_LEFT_A, RobotMap.DriveTrain.ENC_LEFT_B);
		
		lightSensor = new AnalogInput(RobotMap.DriveTrain.LIGHT_SENSOR);
		LiveWindow.addActuator("drive", "left motors", left);
		LiveWindow.addActuator("drive", "right motors", right);
		LiveWindow.addSensor("drive", "left encoder", leftEncoder);
		LiveWindow.addSensor("drive", "right encoder", rightEncoder);
		/*
		try{
			file = new File("/home/lvuser/Readings.txt");
			if(!file.exists()){
				file.createNewFile();
			}
				fw = new FileWriter(file);
				
		}catch(Exception winanas){
			winanas.printStackTrace();
		}
		writer = new BufferedWriter(fw);
		*/
		driver_p = 0.0001;
		driver_i = 0;
		driver_d = 0;
		
		gyro_p = 0.0001;
		gyro_i = 0;
		gyro_d = 0;
		
		
		
		gyroPID = new PIDController(gyro_p, gyro_i, gyro_d, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return getRotationX();
			}
			
		}, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				setPower(output, -output);
			}
		});
		gyroPID.setOutputRange(-1, 1);
		
		SmartDashboard.putData("Gyro PID", gyroPID);
		SmartDashboard.putData("DRIVE SUBSYSTEM", this);
	}
	
	public static DriveSubsystem getInstance() {
		if(instance == null) 
			instance = new DriveSubsystem();
		return instance;
	}
	
	
	public double getAccerometerX() {
		return accelerometer.getX();
	}

	public double getAccerometerY() {
		return accelerometer.getY();
	}

	public double getAccerometerZ() {
		return accelerometer.getZ();
		
	}

	public double getRotationX() {
		return gyro.getRotation();
	}

	public void setLeftPower(double power) {
		left.set(Robot.rangeCheck(power));
	}

	public void setRightPower(double speed) {
		right.set(Robot.rangeCheck(speed));
	}
	
	public void rotateWithPower(double power){
		right.set(-power);
		left.set(power);
	}
	
	public void encodersReset(){
		rightEncoder.reset();
		leftEncoder.reset();
	}
	
	public void setPower(double right, double left) {
		this.left.set(left);
		this.right.set(right);
	}
	
	public double getLeftCurrent() {
		return (panel.getCurrent(RobotMap.DriveTrain.DRIVE_LEFT_PDP_1) +
				panel.getCurrent(RobotMap.DriveTrain.DRIVE_LEFT_PDP_2) +
				 panel.getCurrent(RobotMap.DriveTrain.DRIVE_LEFT_PDP_3)) / 3;
	}
	
	public double getRightCurrent() {
		return (panel.getCurrent(RobotMap.DriveTrain.DRIVE_RIGHT_PDP_1) +
				panel.getCurrent(RobotMap.DriveTrain.DRIVE_RIGHT_PDP_2) +
				 panel.getCurrent(RobotMap.DriveTrain.DRIVE_RIGHT_PDP_3)) / 3;
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
		return -leftEncoder.getDistance();
	}
	
	public double getRightDistance() {
		return rightEncoder.getDistance();
	}
	
	public double getAvgDistance() {
		return (getLeftDistance() + getRightDistance()) / 2;
	}
	
	public double getPIDError() {
		return gyroPID.getError();
	}
	
	public double countsToDist(double counts){
		return counts * .0393686;
	}
	
	public void closeWriter(){
		try{
			writer.close();
		}catch(Exception e){
				e.printStackTrace();
			
		}
	}
	public int getLightReading(){
		return lightSensor.getValue();
	}
	public boolean onDefense(){
		return getLightReading() < RobotMap.DriveTrain.LIGHT_THRESHOLD;//Be aware that the greater than or less than may need to be switched
	}
	
	public void updateSmartDashboard() {

		SmartDashboard.putNumber("Light Sensor Value", getLightReading());
		SmartDashboard.putBoolean("Light Sensor On Defense", onDefense());
		
		SmartDashboard.putNumber("Driver Left Power", getLeftPower());
		SmartDashboard.putNumber("Driver Right Powers", getRightPower());
		double reading = getLightReading();
		double time = System.currentTimeMillis();
		
		try {
			//writer.write("Time: " + time + "Reading: " + reading);
			//writer.newLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		if(debugging && !Robot.competing){
		
		//SmartDashboard.putNumber("Driver PID Error", getPIDError());

			SmartDashboard.putNumber("Accelerometer X", getAccerometerX());
			SmartDashboard.putNumber("Accelerometer Y", getAccerometerY());
			SmartDashboard.putNumber("Accelerometer Z", getAccerometerZ());
		
			
			SmartDashboard.putNumber("Actual Avg Distance", countsToDist(getAvgDistance()));
			SmartDashboard.putNumber("Left Current", getLeftCurrent());
			SmartDashboard.putNumber("Right Current", getRightCurrent());
		
			SmartDashboard.putNumber("Left Speed", getLeftSpeed());
			SmartDashboard.putNumber("Right Speed", getRightSpeed());
		
			SmartDashboard.putNumber("Rotation X", getRotationX());
		
		}
		SmartDashboard.putNumber("Left Distance", getLeftDistance());
		SmartDashboard.putNumber("Right Distance", getRightDistance());
		SmartDashboard.putNumber("Avg Distance", getAvgDistance());

		
		SmartDashboard.putBoolean("DRIVING COLLECTOR FIRST", !isForward);
	
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithPower());
		// TODO: Add Default command for DriveWithGamepad()
	}
}
