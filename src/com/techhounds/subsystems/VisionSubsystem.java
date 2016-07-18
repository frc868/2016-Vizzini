package com.techhounds.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem extends Subsystem{

	private static VisionSubsystem instance;
	
	public VisionSubsystem(){
	}
	
	public static VisionSubsystem getInstance() {
		return instance == null ? instance = new VisionSubsystem() : instance;
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

	protected void initDefaultCommand() {
		
	}

}
