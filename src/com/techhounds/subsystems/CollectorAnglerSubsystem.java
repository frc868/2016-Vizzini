package com.techhounds.subsystems;

import com.techhounds.RobotMap;
import com.techhounds.commands.LimitCheckCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

/**
 *
 */
public class CollectorAnglerSubsystem extends Subsystem {
	
	private static CollectorAnglerSubsystem instance;
	private CANTalon angler;
	private final double P = 0, I = 0, D = 0;
	private PIDController controller;
    
	private CollectorAnglerSubsystem() {
		angler = new CANTalon(RobotMap.Collector.COLLECTOR_ANGLER);
		angler.enableForwardSoftLimit(true);
		angler.enableReverseSoftLimit(true);
		angler.changeControlMode(TalonControlMode.Position);
		controller = new PIDController(P, I, D, new PIDSource(){

			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
			}
			
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public double pidGet() {
				return angler.getSpeed();
			}
    		
    	}, new PIDOutput(){
    		
			public void pidWrite(double output) {
				angler.set(output);
			}
    		
    	});
    	controller.setAbsoluteTolerance(2);
    	controller.setOutputRange(-1, 1);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPosition(int position) {
		angler.setEncPosition(position);
	}
	
	public void stopPower(){
		angler.set(0);
	}
	
	public int getPosition(){
		return angler.getEncPosition();
	}
	
	public boolean getIsForwardLimitHit(){
		return angler.isFwdLimitSwitchClosed();
	}
	
	public boolean getIsReverseLimitHit(){
		boolean limit = angler.isRevLimitSwitchClosed();
		if(limit){
			angler.setEncPosition(0);
		}
		return limit;
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Position", getPosition());
	}
	
	public static CollectorAnglerSubsystem getInstance(){
		if(instance == null){
			instance = new CollectorAnglerSubsystem();
		}
		return instance;
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new LimitCheckCommand());
    }
}

