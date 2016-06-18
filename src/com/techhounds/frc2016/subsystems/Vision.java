package com.techhounds.frc2016.subsystems;

import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends HoundSubsystem{

	private static Vision instance;
	
	private Vision(){
	}
	
	public static Vision getInstance() {
		return instance == null ? instance = new Vision() : instance;
	}
	
	public double getDistanceFromBase(){
		return SmartDashboard.getNumber("DistanceToBase", 0);
	}
	
	public double getDistanceToTarget(){
		return SmartDashboard.getNumber("DistanceToTarget", 0);
	}
	
	public double getAngle(){
		return SmartDashboard.getNumber("OffCenterDegreesX", 0);
	}

	@Override
	public void updatePeriodic() {
		
	}

}
