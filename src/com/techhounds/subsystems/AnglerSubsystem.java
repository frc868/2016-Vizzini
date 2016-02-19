package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AnglerSubsystem extends Subsystem {
	
	private static AnglerSubsystem instance;
	private CANTalon angler;
	private double P = 0.2, I = 0, D = 0;
	private PIDController pid;
    
	private AnglerSubsystem() {
		angler = new CANTalon(RobotMap.Collector.COLLECTOR_ANGLER);
		LiveWindow.addActuator("Angler", "Motor", angler);
		angler.enableForwardSoftLimit(false);
		angler.enableReverseSoftLimit(false);
		angler.changeControlMode(TalonControlMode.PercentVbus);
		angler.configPotentiometerTurns(1);
		angler.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		
		pid = new PIDController(P, I, D, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return Robot.rangeCheck(angler.getAnalogInRaw(), 100, 500);
			}
			
		}, angler);
		pid.setOutputRange(-.3, .3);
		pid.setInputRange(100, 500);
		pid.enable();
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPosition(double position) {
		//angler.changeControlMode(TalonControlMode.Position);
		P = SmartDashboard.getNumber("Angler_P", P);
		I = SmartDashboard.getNumber("Angler_I", I);
		D = SmartDashboard.getNumber("Angler_D", D);
		pid.setPID(P, I, D);
		pid.setSetpoint(Robot.rangeCheck(position, 100, 500)); //0.575 is greatest position ever (only @ portcullis)
		pid.enable();
	}
	
	public void stopPower(){
		pid.disable();
		angler.disable();
	}
	
	public void setPower(double pow){
		//angler.changeControlMode(TalonControlMode.PercentVbus);
		//angler.set(Robot.rangeCheck(pow));
	}
	
	public double getPosition(){
		return angler.getAnalogInRaw();
		//return angler.get();
	}
	
	public boolean reachedTarget(double tolerance){
		return Math.abs(pid.getError()) < tolerance;
	}
	public double getError(){
		return pid.getError();
	}
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Position", getPosition());
		SmartDashboard.putNumber("Error: ", pid.getError());
		//SmartDashboard.putNumber("Angler: ", angler.getPosition());
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

