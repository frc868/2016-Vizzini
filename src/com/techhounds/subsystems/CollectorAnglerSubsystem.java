package com.techhounds.subsystems;

import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CollectorAnglerSubsystem extends Subsystem {
	
	private static CollectorAnglerSubsystem instance;
	
	private CANTalon angler;
    
	public CollectorAnglerSubsystem(CANTalon ang) {
		angler = ang;
		angler.enableForwardSoftLimit(true);
		angler.enableReverseSoftLimit(true);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPower(double power) {
		power = Math.min(Math.max(power, -1), 1);
		if(getInverted()){
			angler.set(-power);
		}else{
			angler.set(power);
		}
	}
	
	public void stopPower(){
		angler.set(0);
	}
	
	public double getPower(){
		return angler.get();
	}
	
	public boolean getInverted(){
		return RobotMap.Collector.ANGLER_IS_INVERTED;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Power", getPower());
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

