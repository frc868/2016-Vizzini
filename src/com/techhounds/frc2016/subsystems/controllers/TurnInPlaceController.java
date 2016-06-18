package com.techhounds.frc2016.subsystems.controllers;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.Drive;
import com.techhounds.frc2016.subsystems.Drive.Controller;
import com.techhounds.frc2016.subsystems.Gyro;
import com.techhounds.lib.util.DriveSignal;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.SynchronousPID;

public class TurnInPlaceController implements Controller {

	private static double
		Kp = .05, 
		Ki = 0, 
		Kd = .08;
	
	private Gyro gyro = HardwareAdaptor.kGyroSubsystem;
	private double tolerance;
	private SynchronousPID pidControl = new SynchronousPID(Kp, Ki, Kd);

	public TurnInPlaceController() {
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
	}
	
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
	
	public boolean onTarget() {
		return pidControl.getError() < tolerance;
	}

	@Override
	public DriveSignal update() {
		
		if(onTarget()) {
			return DriveSignal.STOP;
		}
			
		double output = pidControl.calculate(gyro.getRotation());
		
		return new DriveSignal(
				HoundMath.checkRange(output) , 
				HoundMath.checkRange(-output));
	}
}
