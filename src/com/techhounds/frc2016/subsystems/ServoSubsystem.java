package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.ServoInfo;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.Servo;

public class ServoSubsystem extends HoundSubsystem {
    
	private Servo servo;
	private double min, max;
	
	private static ServoSubsystem 
		instance_winch_dt_to_arm, 
		instance_winch_lock,
		instance_scissor_one;
	
	public ServoSubsystem(int port){
		this(port, 0, 1);
	}
	
	public ServoSubsystem(int port, double min, double max) {
		this(new ServoInfo(new Servo(port), min, max));
	}
	
	public ServoSubsystem(ServoInfo servoInfo) {
		servo = servoInfo.servo;
		min = servoInfo.min;
		max = servoInfo.max;
	}
	
	public static ServoSubsystem getWinchDT_TO_ARM(){
		return instance_winch_dt_to_arm == null ? instance_winch_dt_to_arm = 
				new ServoSubsystem(HardwareAdaptor.kWinch_DT_TO_ARM) : instance_winch_dt_to_arm;
	}
	
	public static ServoSubsystem getWinchLock(){
		return instance_winch_lock == null ? instance_winch_lock = 
				new ServoSubsystem(HardwareAdaptor.kWinch_Lock) : instance_winch_lock;
	}
	
	public static ServoSubsystem getScissorOne(){
		return instance_scissor_one == null ? instance_scissor_one = 
				new ServoSubsystem(HardwareAdaptor.kRelease) : instance_scissor_one;
	}
	
	public void setPosition(double position) {
		servo.set(HoundMath.checkRange(position, min, max));
	}
	
	public void set(boolean setMax) {
		servo.set(HoundMath.checkRange(setMax ? max : min));
	}
	
	public double getPosition() {
		return servo.get();
	}
	
	public boolean getIsMax() {
		return getPosition() >= max;
	}
	
    public void initDefaultCommand() {
    	
    }

	@Override
	public void updatePeriodic() {
		
	}
}

