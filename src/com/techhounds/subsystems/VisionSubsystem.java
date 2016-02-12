package com.techhounds.subsystems;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem{

	private static VisionSubsystem instance;
	private NetworkTable netTable;
	
	public VisionSubsystem(){
		NetworkTable.setServerMode();
		NetworkTable.initialize();
		netTable = NetworkTable.getTable("SmartDashboard");
	}
	
	public static VisionSubsystem getInstance() {
		if(instance == null)
			instance = new VisionSubsystem();
		return instance;
	}
	
	public double getDistanceFromBase(){
		return netTable.getNumber("DistanceToBase", 0);
	}
	
	public double getDistanceToTarget(){
		return netTable.getNumber("DistanceToTarget", 0);
	}
	
	public double getAngle(){
		return netTable.getNumber("OffCenterDegreesX", 0);
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Distance_From_Base", getDistanceFromBase());
		SmartDashboard.putNumber("Distance_To_Target", getDistanceToTarget());
		SmartDashboard.putNumber("Degree_Off_Center", getAngle());
		// TODO: Add Things That Need to be Sent to SmartDashboard for Information
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
