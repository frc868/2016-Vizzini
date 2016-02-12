package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance;
	private CANTalon shooter;
	private Counter count;
	public final double P = 0, I = 0, D = 0;
	private PIDController controller;
	
	private ShooterSubsystem() {
		shooter = new CANTalon(RobotMap.Shooter.SHOOTER_MOTOR);
		shooter.setInverted(getInverted());
		controller = new PIDController(P, I, D, new PIDSource(){

			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
			}
			
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public double pidGet() {
				return shooter.getSpeed();
			}
    		
    	}, new PIDOutput(){
    		
			public void pidWrite(double output) {
				shooter.set(output);
			}
    		
    	});
    	controller.setAbsoluteTolerance(2);
    	controller.setOutputRange(-1, 1);
	}
	
	public void setPower(double power){
		shooter.set(Robot.rangeCheck(power));
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
			instance = new ShooterSubsystem();
		}
		return instance;
	}
}
