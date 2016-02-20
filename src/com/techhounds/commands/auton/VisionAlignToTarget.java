package com.techhounds.commands.auton;

import com.techhounds.commands.gyro.SetGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionAlignToTarget extends CommandGroup {
    
	private double offAngle = 0;
	private NetworkTable netTable;
	private double maxAngle = 0.5;
	
    public  VisionAlignToTarget() {
    	
    	NetworkTable.setServerMode();
    	NetworkTable.initialize();
    	netTable = NetworkTable.getTable("SmartDashboard");
    	
    	if(netTable != null) {
    		offAngle = netTable.getNumber("OffCenterDegreesX", 0);
    	} else {
    		offAngle = 0;
    	}
    	
    	while(offAngle > maxAngle || offAngle < -maxAngle)
    		addSequential(new SetGyro(offAngle));
    }
}
