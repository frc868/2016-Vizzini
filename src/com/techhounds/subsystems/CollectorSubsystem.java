package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CollectorSubsystem extends Subsystem {

	private static CollectorSubsystem instance;
	private CANTalon motor;
	
	private CollectorSubsystem(CANTalon a) {
		motor = a;
	}
	
	public void setPower(double power){
		power = Math.max(Math.min(power, 1), -1);
		motor.set(power);
	}
	
	public double getPower(){
		return motor.get();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("COLLECTOR_POWER", getPower());
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null){
			CANTalon a = new CANTalon(RobotMap.COLLECTOR_MOTOR);
			instance = new CollectorSubsystem(a);
		}
		return instance;
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}