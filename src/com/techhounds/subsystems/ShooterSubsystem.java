package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {

	private static final double MAX_SPEED = 100;
	private static final double MAX_SPEED_DROP_PERCENT = .2;
	private static ShooterSubsystem instance;
	private CANTalon shooter;
	private Counter count;
//	public final double P = .02, I = 0, D = .75, F = .005;
	public final double P = .025, I = 0, D = .075, F = .005;
//	public final double P = 0, I = 0, D = 0, F = .006;
	private PIDController controller;
	private double speedThreshold;
	
	private double lastSpeed;
	private double pidLastSpeed;
	
	public boolean debuging = true;
	private PowerDistributionPanel panel;
	
	private ShooterSubsystem() {
		shooter = new CANTalon(RobotMap.Shooter.SHOOTER_MOTOR);
		shooter.setInverted(getInverted());
		shooter.enableBrakeMode(true);
		
		panel = new PowerDistributionPanel();
		DigitalInput countIn = new DigitalInput(RobotMap.Shooter.SHOOTER_SPEED_DIO);
		count = new Counter(countIn);
		count.setSamplesToAverage(5);
		count.setDistancePerPulse(1);

		controller = new PIDController(P, I, D, F, new PIDSource() {
			
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
			}

			
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return PIDSourceType.kRate;
			}

			public double pidGet() {
				
				double speed = getFilteredSpeed();
				
				
				/* if (speed < pidLastSpeed) { 
					double dropPercent = (pidLastSpeed - speed) / pidLastSpeed; 
					
					if (dropPercent > MAX_SPEED_DROP_PERCENT) { 
						speed = pidLastSpeed; 
					} 
				}	*/ 
				
				return pidLastSpeed = speed;
			}

		}, new PIDOutput() {

			public void pidWrite(double output) {
				shooter.set(output);
			}

		});
		controller.setAbsoluteTolerance(2);
		controller.setOutputRange(0, 1);

		LiveWindow.addActuator("shooter", "motor", shooter);
		LiveWindow.addSensor("shooter", "counter", count);
		LiveWindow.addSensor("shooter", "input", countIn);

		//SmartDashboard.putData("Shooter PID", controller);
	}

	public void setSpeed(double setPoint) {
		pidLastSpeed = 0;
		controller.setSetpoint(setPoint);
		setThreshold(setPoint);
		controller.enable();
	}

	public boolean onTarget() {
		return Math.abs(controller.getError()) < 1;
	}

	public void setPower(double power) {
		controller.disable();
		shooter.set(Robot.rangeCheck(power, -.4, 1));
	}

	public void stopPower() {
		shooter.set(0);
	}
	public void setThreshold(double setPoint){
		speedThreshold = setPoint * 1.3;
	}

	public double getSpeed() {
		double speed = count.getRate();
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
		return count.getDistance();
	}

	public double getCount() {
		return count.get();
	}

	public void resetEncoder() {
		count.reset();
	}

	public boolean getInverted() {
		return RobotMap.Shooter.SHOOTER_IS_INVERTED;
	}

	public double getPower() {
		return shooter.get();
	}
	
	public double getCurrent() {
		return 0;//return panel.getCurrent(channel);
	}

	public PIDController getController()  {
		return controller;
	}
	
	public void updateSmartDashboard() {
		if (debuging && !Robot.competing) {
			SmartDashboard.putNumber("Shooter_Power", getPower());
			SmartDashboard.putNumber("Shooter Speed", getSpeed());
			SmartDashboard.putNumber("Shooter Distance", getDistance());
			SmartDashboard.putNumber("Shooter Error", controller.getError());
			SmartDashboard.putNumber("Shooter Count", getCount());
			SmartDashboard.putNumber("Shooter Period", count.getPeriod());
			
		}
		SmartDashboard.putNumber("Shooter Count", getCount());
		SmartDashboard.putNumber("Shooter_Power", getPower());
		SmartDashboard.putData("PID", controller);
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

	public static ShooterSubsystem getInstance() {
		if (instance == null) {
			instance = new ShooterSubsystem();
		}
		return instance;
	}
}
