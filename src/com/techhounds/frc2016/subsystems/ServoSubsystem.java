package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.RobotMap;
import com.techhounds.frc2016.ServoInfo;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.Servo;

public class ServoSubsystem extends HoundSubsystem {
    
	private Servo servo;
	private double min, max;
	
	private static ServoSubsystem 
		instance_winch_enable, 
		instance_winch_lock,
		instance_scissor_one, 
		instance_scissor_two;
	
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
	
	public static ServoSubsystem getWinchEnable(){
		return instance_winch_enable == null ? instance_winch_enable = 
				new ServoSubsystem(HardwareAdaptor.kWinch_DT_TO_ARM) : instance_winch_enable;
	}
	
	public static ServoSubsystem getWinchLock(){
		return instance_winch_lock == null ? instance_winch_lock = 
				new ServoSubsystem(HardwareAdaptor.kWinch_Lock) : instance_winch_lock;
	}
	
	public static ServoSubsystem getScissorOne(){
		return instance_scissor_one == null ? instance_scissor_one = 
				new ServoSubsystem(HardwareAdaptor.kRelease) : instance_scissor_one;
	}
	
	public static ServoSubsystem getScissorTwo(){
		return instance_scissor_two == null ? instance_scissor_two = new ServoSubsystem(RobotMap.Servo.SCISSOR_TWO, RobotMap.Servo.SCISSOR_TWO_MIN, RobotMap.Servo.SCISSOR_TWO_MAX) : instance_scissor_two;
	}
	
	public void setPosition(double position) {
		servo.set(HoundMath.checkRange(position, min, max));
	}
	
	public void set(boolean setMax) {
		servo.set(HoundMath.checkRange(setMax ? max : min));
		//if setMax servo is set to max, else set to min
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

