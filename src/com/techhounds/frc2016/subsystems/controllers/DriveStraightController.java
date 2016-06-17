package com.techhounds.frc2016.subsystems.controllers;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem.Controller;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.lib.util.DriveSignal;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.SynchronousPID;

public class DriveStraightController implements Controller {

	private static double
		Kp = 0.025,
		Ki = 0.000,
		Kd = 0.004;
	
	private double initAngle;
	private boolean firstCall = true;
	
	private DriveSubsystem drive = HardwareAdaptor.kDriveSubsystem;
	private GyroSubsystem gyro = HardwareAdaptor.kGyroSubsystem;
	
	private SynchronousPID pidControl = new SynchronousPID(Kp, Ki, Kd);

	public DriveStraightController() {
		pidControl.setOutputRange(-1.0, 1.0);
	}
	
	public SynchronousPID getController() {
		return pidControl;
	}
	
	@Override
	public boolean isEnabled() {
		return pidControl.isEnabled();
	}
	
	public void enable() {
		pidControl.enable();
	}
	
	public void disable() {
		pidControl.disable();
		firstCall = true;
	}
	
	public boolean onTarget(double tolerance) {
		return pidControl.getError() < tolerance;
	}

	@Override
	public DriveSignal update() {
		
		if(onTarget(2.5)) {
			return DriveSignal.STOP;
		}
		
		if(firstCall) {
			initAngle = gyro.getRotation();
			firstCall = false;
		}
		
		double output = pidControl.calculate(drive.getAvgDistance());
		double turn = calculateTurn(output);
		
		return new DriveSignal(
				HoundMath.checkRange(output - turn) , 
				HoundMath.checkRange(output + turn));
	}
	
	private double calculateTurn(double output) {
		
		double currAngle = gyro.getRotation() - initAngle;
		double turn;
		
		double MAX_OFFSET = .25;
		
		if(currAngle > 9.11346) {
			turn = MAX_OFFSET;
		} else if(currAngle < -9.01606) {
			turn = -MAX_OFFSET;
		} else {
			turn = MAX_OFFSET * ((-0.000136434109 * Math.pow(currAngle, 3) + 0.0000199335555 * Math.pow(currAngle, 2)
				+ 0.03363132991 * (currAngle)) / .201608);
		}
		
		turn *= Math.abs(output);
		return turn;
	}

}
