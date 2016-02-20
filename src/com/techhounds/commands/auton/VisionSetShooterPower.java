package com.techhounds.commands.auton;

import com.techhounds.commands.shooter.SetShooterPower;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionSetShooterPower extends CommandGroup {
    
	private double targetDistance = 0;
	private double shootPower = 0;
	private NetworkTable netTable;
	
    public VisionSetShooterPower() {
        
    	NetworkTable.setServerMode();
    	NetworkTable.initialize();
    	netTable = NetworkTable.getTable("SmartDashboard");
    	
    	if(netTable != null) {
    		targetDistance = netTable.getNumber("DistanceToTarget", 0);
    	} 
    	
    	//TODO convert from distance to power
    	
    	shootPower = targetDistance / 100000;
    	
    	addSequential(new SetShooterPower(shootPower));
    }
}
