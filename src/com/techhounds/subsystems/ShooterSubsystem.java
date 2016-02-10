package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem {

	private static ShooterSubsystem instance;
	private Spark shoot;
	private boolean isInverted;
	
	private ShooterSubsystem() {
		shoot = new Spark(RobotMap.Shooter.SHOOTER_MOTOR);
		isInverted = RobotMap.Shooter.SHOOTER_IS_INVERTED;
	}
	
	public void setPower(double power){
		power = Math.min((Math.max(power, -1)), 1);
		if(isInverted){
			shoot.set(-power);
		}else{
			shoot.set(power);
		}
	}
	
	public void stopPower(){
		shoot.set(0);
	}
	
	public double getPower(){
		return shoot.get();
	}
	
	public void UpdateSmartDashboard(){
		SmartDashboard.putNumber("Shooter_Power", getPower());
	}
	
	public void initDefaultCommand(){
	}
	
	public static ShooterSubsystem getInstance() {
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}
}
