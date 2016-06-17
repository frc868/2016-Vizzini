package com.techhounds.frc2016.subsystems.controllers;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem.Controller;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.lib.util.DriveSignal;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.SynchronousPID;

public class TurnInPlaceController implements Controller {

	private static double
		Kp = .05, 
		Ki = 0, 
		Kd = .08;
	
	private GyroSubsystem gyro = HardwareAdaptor.kGyroSubsystem;
	
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
	
	public boolean onTarget(double tolerance) {
		return pidControl.getError() < tolerance;
	}

	@Override
	public DriveSignal update() {
		
		if(onTarget(1.0)) {
			return DriveSignal.STOP;
		}
			
		double output = pidControl.calculate(gyro.getRotation());
		
		return new DriveSignal(
				HoundMath.checkRange(output) , 
				HoundMath.checkRange(-output));
	}
}
