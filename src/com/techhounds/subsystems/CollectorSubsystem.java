package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CollectorSubsystem extends Subsystem {

	private static CollectorSubsystem instance;
	private CANTalon motor;
	private Encoder enc;
	
	private CollectorSubsystem(CANTalon a) {
		motor = a;
	}
	
	public void setPower(double power){
		power = Math.max(Math.min(power, 1), -1);
		if(getInverted()){
			motor.set(-power);
		}else{
			motor.set(power);
		}
	}
	
	public void stopPower(){
		motor.set(0);
	}
	
	public double getPower(){
		return motor.get();
	}
	
	public double getSpeed(){
		return enc.getRate();
	}
	
	public void resetEncoder(){
		enc.reset();
	}
	
	public boolean getInverted(){
		return RobotMap.Collector.COLLECTOR_IS_INVERTED;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Collector_Power", getPower());
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null){
			CANTalon a = new CANTalon(RobotMap.Collector.COLLECTOR_MOTOR);
			instance = new CollectorSubsystem(a);
		}
		return instance;
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}