package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance;
	private CANTalon shooter;
	private Counter count;
	
	private ShooterSubsystem(CANTalon s) {
		shooter = s;
	}
	
	public void setPower(double power){
		power = Math.min((Math.max(power, -1)), 1);
		if(getInverted()){
			shooter.set(-power);
		}else{
			shooter.set(power);
		}
	}
	
	public void stopPower(){
		shooter.set(0);
	}
	
	public double getSpeed(){
		return count.getRate();
	}
	
	public void resetEncoder(){
		count.reset();
	}
	
	public boolean getInverted(){
		return RobotMap.Shooter.SHOOTER_IS_INVERTED;
	}
	
	public double getPower(){
		return shooter.get();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Shooter_Power", getPower());
	}
	
	public void initDefaultCommand(){
	}
	
	public static ShooterSubsystem getInstance() {
		if(instance == null) {
			CANTalon s = new CANTalon(RobotMap.Shooter.SHOOTER_MOTOR);
			instance = new ShooterSubsystem(s);
		}
		return instance;
	}
}
