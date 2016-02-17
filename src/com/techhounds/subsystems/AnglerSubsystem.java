package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;
import com.techhounds.commands.angler.LimitCheckCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AnglerSubsystem extends Subsystem {
	
	private static AnglerSubsystem instance;
	private CANTalon angler;
	private double P = 0, I = 0, D = 0;
    
	private AnglerSubsystem() {
		angler = new CANTalon(RobotMap.Collector.COLLECTOR_ANGLER);
		LiveWindow.addActuator("Angler", "Motor", angler);
		angler.enableForwardSoftLimit(false);
		angler.enableReverseSoftLimit(false);
		angler.changeControlMode(TalonControlMode.Position);
		angler.configPotentiometerTurns(1);
		angler.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		SmartDashboard.getNumber("Angler_P", P);
		SmartDashboard.getNumber("Angler_I", I);
		SmartDashboard.getNumber("Angler_D", D);
		angler.setPID(P, I, D);
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPosition(double position) {
		angler.changeControlMode(TalonControlMode.Position);
		P = SmartDashboard.getNumber("Angler_P", P);
		I = SmartDashboard.getNumber("Angler_I", I);
		D = SmartDashboard.getNumber("Angler_D", D);
		angler.setPID(P, I, D);
		angler.set(position);
		angler.enable();
	}
	
	public void stopPower(){
		angler.disable();
	}
	
	public void setPower(double pow){
		angler.changeControlMode(TalonControlMode.PercentVbus);
		angler.set(Robot.rangeCheck(pow));
	}
	
	public double getPosition(){
		return angler.get();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Position", getPosition());
	}
	
	public static AnglerSubsystem getInstance(){
		if(instance == null){
			instance = new AnglerSubsystem();
		}
		return instance;
	}

    public void initDefaultCommand() {
    	
    }
}

