package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class UltrasonicSubsystem extends Subsystem {

	private AnalogInput ultrasonic;
	private static UltrasonicSubsystem instance;
	
	private UltrasonicSubsystem() {
		ultrasonic = new AnalogInput(RobotMap.Ultrasonic.ULTRASONIC);
	}
	
	public static UltrasonicSubsystem getInstance() {
		return instance == null ? instance = new UltrasonicSubsystem() : instance;
	}
	
	public double getRawValue() {
		return ultrasonic.getValue();
	}
	
	public double getDistance() {
		return getRawValue() <= 59 ? -1 : 0.20318831919963 * getRawValue() - 0.71996108442685;
	}
	
	@Override
	protected void initDefaultCommand() {

	}

}
