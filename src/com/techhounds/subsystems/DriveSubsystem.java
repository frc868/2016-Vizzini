package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem{

	// TODO: Add Encoders
	// TODO: Add Implementation for Set Drive Power (maybe Voltage?)
	// TODO: Add Drive Distance PID Controller
	// TODO: Add Gyro Turn PID Controller
	
	private Spark left;
	private Spark right;
	private boolean rotating;
	private PIDController pid;
	
	private final double driver_p;
	private final double driver_i;
	private final double driver_d;
	
	private final GyroSubsystem gyro;
	private final double gyro_p;
	private final double gyro_i;
	private final double gyro_d;
	
	private final double percentTolerable = .1;
	
	private static DriveSubsystem instance;
	
	private DriveSubsystem() {		
		left = new Spark(RobotMap.DriveTrain.DRIVE_LEFT_MOTOR);
		left.setInverted(RobotMap.DriveTrain.DRIVE_LEFT_IS_INVERTED);
		
		right = new Spark(RobotMap.DriveTrain.DRIVE_RIGHT_MOTOR);
		right.setInverted(RobotMap.DriveTrain.DRIVE_RIGHT_IS_INVERTED);
		
		driver_p = 0.0001;
		driver_i = 0;
		driver_d = 0;
		
		gyro = Robot.gyro;
		gyro_p = 0.0001;
		gyro_i = 0;
		gyro_d = 0;
	}
	
	public static DriveSubsystem getInstance() {
		if(instance == null) 
			instance = new DriveSubsystem();
		return instance;
	}
	
	public void loadStraightPIDValues() {
		rotating = false;
		pid = new PIDController(driver_p, driver_i, driver_d,
			new PIDSource() {
						
						@Override
						public void setPIDSourceType(PIDSourceType pidSource) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public double pidGet() {
							// TODO Auto-generated method stub
							return getLeftSpeed();
						}
						
						@Override
						public PIDSourceType getPIDSourceType() {
							// TODO Auto-generated method stub
							return PIDSourceType.kDisplacement;
						};
					},
			new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					setBothSpeed(output);
				}
			});
		pid.setPercentTolerance(percentTolerable);
		enablePID();
	}
	
	public void enablePID() {
		if(pid != null) {
			pid.enable();
		}
	}
	
	public void disablePID() {
		if(pid != null) {
			pid.disable();
		}
	}
	
	public void loadGyroPIDValues() {
		rotating = true;
		gyro.resetGyro();
		pid = new PIDController(gyro_p, gyro_i, gyro_d,
			new PIDSource() {
						
						@Override
						public void setPIDSourceType(PIDSourceType pidSource) {
							
						}
						
						@Override
						public double pidGet() {
							return GyroSubsystem.getInstance().getRotation();
						}
						
						@Override
						public PIDSourceType getPIDSourceType() {
							// TODO Auto-generated method stub
							return PIDSourceType.kDisplacement;
						};
					},
			new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					setBothSpeed(output);
				}
			});
		pid.setPercentTolerance(percentTolerable);
		enablePID();
	}
	
	public void loadLeftPIDValues() {
		pid = new PIDController(driver_p, driver_i, driver_d,
			new PIDSource() {
						@Override
						public void setPIDSourceType(PIDSourceType pidSource) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public double pidGet() {
							// TODO Auto-generated method stub
							return getLeftSpeed();
						}
						
						@Override
						public PIDSourceType getPIDSourceType() {
							// TODO Auto-generated method stub
							return PIDSourceType.kRate;
						};
					},
			new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					setLeftSpeed(output);
				}
			});
		enablePID();
	}
	
	public void loadRightPIDValues() {
		pid = new PIDController(driver_p, driver_i, driver_d,
			new PIDSource() {
						@Override
						public void setPIDSourceType(PIDSourceType pidSource) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public double pidGet() {
							// TODO Auto-generated method stub
							return getRightSpeed();
						}
						
						@Override
						public PIDSourceType getPIDSourceType() {
							// TODO Auto-generated method stub
							return PIDSourceType.kRate;
						};
					},
			new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					setRightSpeed(output);
				}
			});
		enablePID();
	}
	
	public void setLeftSpeed(double speed) {
		if(speed > 1) speed = 1;
		else if(speed < -1) speed = -1;
		left.set(speed);
	}
	
	public void setRightSpeed(double speed) {
		if(speed > 1) speed = 1;
		else if(speed < -1) speed = -1;
		right.set(speed);
	}
	
	public void setBothSpeed(double output) {
		left.set(output);
		if(rotating) right.set(-output);
		else right.set(output);
	}
	
	public double getLeftSpeed() {
		return left.get();
	}
	
	public double getRightSpeed() {
		return right.get();
	}
	
	public double getPIDError() {
		return pid.getError();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Driver Left Speed", getLeftSpeed());
		SmartDashboard.putNumber("Driver Right Speed", getRightSpeed());
		SmartDashboard.putNumber("Driver PID Error", getPIDError());
	}

	protected void initDefaultCommand() {
		// TODO: Add Default command for DriveWithGamepad()
	}
}
