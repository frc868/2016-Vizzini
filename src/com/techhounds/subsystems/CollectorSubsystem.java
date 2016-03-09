package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CollectorSubsystem extends Subsystem {

	private static CollectorSubsystem instance;
	private CANTalon motor;
	private PowerDistributionPanel panel;
	private boolean debugging;
	
	private CollectorSubsystem() {
		
		panel = new PowerDistributionPanel();
		
		motor = new CANTalon(RobotMap.Collector.COLLECTOR_MOTOR);
		motor.setInverted(getInverted());
		motor.enableBrakeMode(true);
		LiveWindow.addActuator("collector", "motor", motor);
	}
	
	public void setPower(double power){
		motor.set(Robot.rangeCheck(power));
	}
	
	public void setToPercentVBus(){
		motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	public void stopPower(){
		motor.set(0);
	}
	
	public double getCurrent() {
		return panel.getCurrent(RobotMap.Collector.COLLECTOR_PDP);
	}
	
	public double getPower(){
		return motor.get();
	}
	
	public boolean getIsIn(){
		if(getPower() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean getIsOut(){
		if(!getIsIn() && getPower() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean getInverted(){
		return RobotMap.Collector.COLLECTOR_IS_INVERTED;
	}
	
	public void updateSmartDashboard(){
		if(debugging && !Robot.competing){
			SmartDashboard.putBoolean("Collector_Is_Going_Inward", getIsIn());
			SmartDashboard.putBoolean("Collector_Is_Going_Outward", getIsOut());
			SmartDashboard.putNumber("Collector_Power", getPower());
			SmartDashboard.putNumber("Collector Current", getCurrent());
		}
	}
	
	public static CollectorSubsystem getInstance() {
		if(instance == null){
			instance = new CollectorSubsystem();
		}
		return instance;
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}