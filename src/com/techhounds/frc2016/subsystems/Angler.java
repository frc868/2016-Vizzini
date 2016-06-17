package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSubsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Angler extends HoundSubsystem implements PIDSource, PIDOutput {
	
	private static Angler instance;
	
	private CANTalon angler = HardwareAdaptor.kMotor_Angler;;
	private PIDController pid = new PIDController(P, I, D, this, this, PERIOD);
	
	private static final double 
		P = 0.004, 
		I = 0, 
		D = 0.005,
		TOLERANCE = 10,
		PERIOD = 1 / 200.0,
		MIN_OUTPUT = -0.45,
		MAX_OUTPUT = 0.45;
	
	private int state = 2;	//defaults as maximum height
    
	private Angler() {
		
		angler.setInverted(HardwareConstants.Angler.INVERTED);
		angler.enableForwardSoftLimit(false);
		angler.enableReverseSoftLimit(false);
		angler.changeControlMode(TalonControlMode.PercentVbus);
		angler.configPotentiometerTurns(1);
		angler.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		
		pid.setOutputRange(-MIN_OUTPUT, MAX_OUTPUT);
		pid.setInputRange(HardwareConstants.Angler.UP, HardwareConstants.Angler.DOWN);
		pid.setAbsoluteTolerance(TOLERANCE);

		SmartDashboard.putData("Angler PID", pid);
	}
	
	public void setPosition(double position) {
		pid.setSetpoint(HoundMath.checkRange(position, 
				HardwareConstants.Angler.UP, HardwareConstants.Angler.DOWN));
		pid.enable();
	}
	
	public double getSetPoint(){
		return pid.getSetpoint();
	}
	
	public boolean onTarget(){
		return pid.onTarget();
	}
	
	public void setPower(double output) {
		pid.disable();
		angler.set(output);
	}
	
	public void stopPower(){
		pid.disable();
		angler.set(0);
	}
	
	public void disable() {
		pid.disable();
	}
	
	public double getRawPosition(){
		return angler.getAnalogInRaw();
	}
	
	public boolean onTarget(double tolerance){
		return Math.abs(pid.getError()) < tolerance;
	}
	
	public double getError(){
		return pid.getError();
	}
	
	public void updatePeriodic() {
		SmartDashboard.putNumber("Angler_Position", getRawPosition());
		SmartDashboard.putNumber("Angler_Error: ", pid.getError());
	}
	
	public static Angler getInstance(){
		return instance == null ? instance = new Angler() : instance;
	}

    public void initDefaultCommand() {
    	
    }
    
    public int getState() {
    	return state;
    }
    
    public void increaseState() {
    	state++;
    	if(state >= 2)
    		state = 2;
    }
    
    public void decreaseState() {
    	state--;
    	if(state <= 0)
    		state = 0;
    }
    
    public void resetState() {
    	state = 2;
    }
    
	@Override
	public void pidWrite(double output) {
		
		if(output < 0)
			output -= .1;
		
		angler.set(-output);
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {		}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
	
	@Override
	public double pidGet() {
		return getRawPosition();
	}
}

