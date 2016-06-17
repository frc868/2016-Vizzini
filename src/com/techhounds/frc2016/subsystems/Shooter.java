package com.techhounds.frc2016.subsystems;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.lib.util.HoundMath;
import com.techhounds.lib.util.HoundSubsystem;
import com.techhounds.lib.util.SynchronousPID;
import com.techhounds.lib.util.SynchronousPIDWPI;
import com.techhounds.lib.util.Updateable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends HoundSubsystem implements Updateable {

	private CANTalon shooter = HardwareAdaptor.kMotor_Shooter;
	private Counter counter = HardwareAdaptor.kDIO_Shooter;
	
	private SynchronousPIDWPI controller = new SynchronousPIDWPI(P, I, D, PIDSourceType.kRate);
	
	//private PIDController controller = new PIDController(P, I, D, F, this, this, PERIOD);
	// Legacy 
	private static final double 
		MAX_SPEED = 100,
		//MAX_SPEED_DROP_PERCENT = .2,
		PERIOD = 1 / 100.0,
		COUNTS_TO_DISTANCE = 1,
		ABS_TOLERANCE = 2,
		MIN_OUTPUT = 0,
		MAX_OUTPUT = 1;
	

	private static final double 
		P = .025, 
		I = 0, 
		D = .075, 
		F = .005;
	
	private static Shooter instance;
	
//	public final double P = .02, I = 0, D = .75, F = .005;
//	public final double P = 0, I = 0, D = 0, F = .006;

	private double speedThreshold, lastSpeed, pidLastSpeed;
	
	private Shooter() {
		shooter.setInverted(getInverted());
		shooter.enableBrakeMode(true);
		
		counter.setSamplesToAverage(5);
		counter.setDistancePerPulse(COUNTS_TO_DISTANCE);

		//controller.setAbsoluteTolerance(ABS_TOLERANCE);
		controller.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
	}

	public void setSpeed(double setPoint) {
		setThreshold(setPoint);
		controller.reset();
		controller.setSetpoint(setPoint);
		controller.enable();
	}

	public void setPower(double power) {
		controller.disable();
		shooter.set(HoundMath.checkRange(power, -.4, 1));
	}

	public void stopPower() {
		controller.disable();
		shooter.set(0);
	}
	
	public void setThreshold(double setPoint){
		speedThreshold = setPoint * 1.3;
	}

	public double getSpeed() {
		double speed = counter.getRate();
		if (speed == Double.NaN || speed == Double.POSITIVE_INFINITY || speed == Double.NEGATIVE_INFINITY) {
			speed = lastSpeed;
		}
		if (speed > MAX_SPEED) {
			speed = lastSpeed;
		}
		
		lastSpeed = speed;
		return speed;
	}

	public double getFilteredSpeed(){
		double speed = getSpeed();
		if(speed > speedThreshold){
			speed = lastSpeed;
		}
		lastSpeed = speed;
		return speed;
		
	}

	public double getDistance() {
		return counter.getDistance();
	}

	public double getCount() {
		return counter.get();
	}

	public void resetEncoder() {
		counter.reset();
	}

	public boolean getInverted() {
		return HardwareConstants.Shooter.INVERTED;
	}

	public double getPower() {
		return shooter.get();
	}
	
	public double getCurrent() {
		return shooter.getOutputCurrent();
	}

	public void updatePeriodic() {
		SmartDashboard.putNumber("Shooter Count", getCount());
		SmartDashboard.putNumber("Shooter_Power", getPower());
		SmartDashboard.putNumber("PID Output", getPower());
		SmartDashboard.putNumber("Shooter Speed", getFilteredSpeed());
		SmartDashboard.putBoolean("Shooter PID OnTarget", controller.isEnabled() ? onTarget() : false);
		SmartDashboard.putBoolean("Shooter PID Running", controller.isEnabled());
	}

	public void initDefaultCommand() {
		
	}
	
	public double getSetPoint(){
		return controller.getSetpoint();
	}

	public static Shooter getInstance() {
		return instance == null ? instance = new Shooter() : instance;
	}

	@Override
	public void update() {
		if(controller.isEnabled()) {
			double output = controller.calculate(getFilteredSpeed())  // P I D Calculation
					+ F * controller.getSetpoint(); // Feedforward Calculation
			shooter.set(output);
		}
	}
	
	public boolean onTarget() {
		return isEnabled() ? Math.abs(controller.getError()) < 1 : true;
	}

	public boolean isEnabled() {
		return controller.isEnabled();
	}
}
