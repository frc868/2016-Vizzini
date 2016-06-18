package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.HardwareMap;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSubsystem;
import com.techhounds.lib.util.Updateable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Collector extends HoundSubsystem implements Updateable {

	private static Collector instance;
	private CANTalon motor = HardwareAdaptor.kMotor_Collector;
	
	private Collector() {
		motor.setInverted(getInverted());
		motor.enableBrakeMode(true);
	}
	
	public void setPower(double power){
		motor.set(HoundMath.checkRange(power));
	}
	
	public void stopPower(){
		motor.set(0);
	}
	
	public double getCurrent() {
		return HardwareAdaptor.kPDP.getCurrent(HardwareMap.PDP.COLLECTOR);
	}
	
	public double getPower(){
		return motor.get();
	}
	
	public boolean getIsIn(){
		return getPower() > 0;
	}
	
	public boolean getIsOut(){
		return !getIsIn() && getPower() != 0;
	}
	
	public boolean getInverted(){
		return HardwareConstants.Collector.INVERTED;
	}
	
	public void updatePeriodic(){
		SmartDashboard.putBoolean("Collector_Is_Going_Inward", getIsIn());
		SmartDashboard.putBoolean("Collector_Is_Going_Outward", getIsOut());
		SmartDashboard.putNumber("Collector_Power", getPower());
		SmartDashboard.putNumber("Collector Current", getCurrent());
	}
	
	public static Collector getInstance() {
		return instance == null ? instance = new Collector() : instance;
	}

	@Override
	public void update() {
		
	}
}