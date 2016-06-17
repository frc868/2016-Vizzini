package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.lib.util.DriveSignal;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSpeedController;
import com.techhounds.lib.util.HoundSubsystem;
import com.techhounds.lib.util.Updateable;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends HoundSubsystem implements Updateable {
	
	private HoundSpeedController leftMotor = HardwareAdaptor.kMotor_DriveLeft;
	private HoundSpeedController rightMotor = HardwareAdaptor.kMotor_DriveRight;
	
	private Encoder leftEncoder = HardwareAdaptor.kDIO_DRIVE_LEFT;
	private Encoder rightEncoder = HardwareAdaptor.kDIO_DRIVE_RIGHT;
	private AnalogInput lightSensor = HardwareAdaptor.kDIO_DRIVE_LIGHT;
	
	private BuiltInAccelerometer accelerometer = HardwareAdaptor.kAccelerometer;
	private Controller driveController = null;
	
	public static boolean isForward = false;
	
	private static final double 
		//DRIVE_P = 0.025,
		//DRIVE_I = 0.000,
		//DRIVE_D = 0.004,
		
		//TURN_P = 0.05,
		//TURN_I = 0.00,
		//TURN_D = 0.08,
		
		//PERCENT_TOLERANCE = 0.1,
		COUNTS_TO_DISTANCE = 0.0393686;
	
	private static Drive instance;
	
	private Drive() {	
		leftMotor.setInverted(HardwareConstants.Drive.LEFT_INVERTED);
		rightMotor.setInverted(HardwareConstants.Drive.RIGHT_INVERTED);
		
		leftEncoder.setDistancePerPulse(COUNTS_TO_DISTANCE);
		rightEncoder.setDistancePerPulse(COUNTS_TO_DISTANCE);
	}
	
	public static Drive getInstance() {
		return instance == null ? instance = new Drive() : instance;
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

	public void setLeftPower(double power) {
		leftMotor.set(HoundMath.checkRange(power));
	}

	public void setRightPower(double speed) {
		rightMotor.set(HoundMath.checkRange(speed));
	}
	
	public void rotateWithPower(double power){
		setPower(-power, power);
	}
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void setPower(double right, double left) {
		setLeftPower(left);
		setRightPower(right);
	}
	
	public void setPower(DriveSignal signal) {
		setPower(signal.right, signal.left);
	}
	
	public double getLeftCurrent() {
		return leftMotor.getCurrent();
	}
	
	public double getRightCurrent() {
		return rightMotor.getCurrent();
	}
	
	public double getLeftPower() {
		return leftMotor.get();
	}
	
	public double getRightPower() {
		return rightMotor.get();
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

	public int getLightReading(){
		return lightSensor.getValue();
	}
	
	public boolean onDefense(){
		//Be aware that the greater than or less than may need to be switched
		return getLightReading() < HardwareConstants.Drive.LIGHT_THRESHOLD;
	}
	
	public static void setDriveForward(boolean forward) {
		isForward = forward;
	}
	
	public static boolean getDriveForward() {
		return isForward;
	}
	
	public void updatePeriodic() {

		SmartDashboard.putNumber("Light Sensor Value", getLightReading());
		SmartDashboard.putBoolean("Light Sensor On Defense", onDefense());
		
		SmartDashboard.putNumber("Driver Left Power", getLeftPower());
		SmartDashboard.putNumber("Driver Right Powers", getRightPower());
		
		SmartDashboard.putNumber("Left Distance", getLeftDistance());
		SmartDashboard.putNumber("Right Distance", getRightDistance());
		SmartDashboard.putNumber("Avg Distance", getAvgDistance());

		SmartDashboard.putNumber("Left Speed", getLeftSpeed());
		SmartDashboard.putNumber("Right Speed", getRightSpeed());
		SmartDashboard.putBoolean("DRIVING COLLECTOR FIRST", !isForward);
	
	}
	
	public interface Controller {
		DriveSignal update();
		boolean isEnabled();
		boolean onTarget(double tolerance);
		void enable();
		void disable();
	}

	protected void initDefaultCommand() {
		// setDefaultCommand(new DriveWithGamepad());
	}

	public void driveJoysticks() {
		if(isForward){
    		setPower(HardwareAdaptor.kOperatorInterface.getRightBackward(), 
    				HardwareAdaptor.kOperatorInterface.getLeftBackward());
    	} else {
    		setPower(HardwareAdaptor.kOperatorInterface.getRightForward(), 
    				HardwareAdaptor.kOperatorInterface.getLeftForward());	
    	}
	}
	
	@Override
	public void update() {
		if(driveController != null && driveController.isEnabled()) {	// Closed Loop (i.e. PID / PIDVA)
			setPower(driveController.update());
		} else {														// Open Loop (i.e. Drive Input)
			driveJoysticks();
		}
	}
	
	public void setController(Controller controller) {
		driveController = controller;
	}
}
