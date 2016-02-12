package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CollectorAnglerSubsystem extends Subsystem {
	
	private static CollectorAnglerSubsystem instance;
	private CANTalon angler;
	private Encoder enc;
    
	public CollectorAnglerSubsystem(CANTalon ang) {
		angler = ang;
		angler.enableForwardSoftLimit(true);
		angler.enableReverseSoftLimit(true);
		angler.changeControlMode(TalonControlMode.Position);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPosition(double position) {
		angler.set(position);
	}
	
	public double getPosition(){
		return angler.getPosition();
	}
	
	public double getSpeed(){
		return enc.getRate();
	}
	
	public void resetEncoder(){
		enc.reset();
	}
	
	public boolean getIsForwardLimitHit(){
		return angler.isFwdLimitSwitchClosed();
	}
	
	public boolean getIsReverseLimitHit(){
		boolean limit;
		limit = angler.isRevLimitSwitchClosed();
		if(limit){
			angler.setEncPosition(0);
		}
		return limit;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Speed", getSpeed());
		SmartDashboard.putNumber("Angler_Position", getPosition());
	}
	
	public static CollectorAnglerSubsystem getInstance(){
		if(instance == null){
			CANTalon x = new CANTalon(RobotMap.Collector.COLLECTOR_ANGLER);
			instance = new CollectorAnglerSubsystem(x);
		}
		return instance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

