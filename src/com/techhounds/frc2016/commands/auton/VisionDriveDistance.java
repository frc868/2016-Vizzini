package com.techhounds.frc2016.commands.auton;

import com.techhounds.frc2016.commands.drive_legacy.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionDriveDistance extends CommandGroup {
    
	private double targetDistance = 0;
	private NetworkTable netTable;
	
    public  VisionDriveDistance() {
        
    	NetworkTable.setServerMode();
    	NetworkTable.initialize();
    	netTable = NetworkTable.getTable("SmartDashboard");
    	
    	if(netTable != null) {
    		targetDistance = netTable.getNumber("DistanceToTarget", 0);
    	}
    	
    	addSequential(new DriveDistance(targetDistance));
    }
}
