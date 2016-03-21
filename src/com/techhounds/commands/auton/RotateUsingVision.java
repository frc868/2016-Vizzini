package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RotateUsingVision extends Command implements PIDSource, PIDOutput {
	
	private static double p = .1/*.05*/, i = 0, d = .12;//.08;
	protected DriveSubsystem drive;
	protected GyroSubsystem gyro;
	private PIDController pid;
	private double angle;
	private Double timeOut;
	public static final boolean DEBUG = true;
	//min turn power can be less here, as robot should already be moving
	private double MIN_TURN_POWER = .3;
	private double MIN_STRAIT_POWER = .16;
	
	private boolean setRotation, readyToRotate;
	private double lastFrame;
	private double checkFrame;
	/**
	 * Command to perform a relative rotation
	 * @param angle, in degrees, positive for clockwise, negative for counter-clockwise.
	 */
    public RotateUsingVision() {
    	gyro = GyroSubsystem.getInstance();
    	drive = DriveSubsystem.getInstance();
    	requires(drive);
    	pid = new PIDController(p, i, d, this, this);
    	if(DEBUG){
    		SmartDashboard.putData("Gyro PID", pid);
    	}
    	pid.setOutputRange(-.65, .65);
    	pid.setAbsoluteTolerance(1);
    	MIN_TURN_POWER = RobotMap.DriveTrain.MIN_TURN_POWER;
    	
    }
    
    public RotateUsingVision(double timeOut) {
    	this();
    	this.timeOut = timeOut;
    }
        // Called just before this Command runs the first time
    protected void initialize() {
    	readyToRotate = false;
    	setRotation = false;

		lastFrame = SmartDashboard.getNumber("FrameCount", -1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double frame = SmartDashboard.getNumber("FrameCount", -1);
    
    	
		if(readyToRotate && !setRotation) {
			double angleOff = SmartDashboard.getNumber("OffCenterDegreesX", 0);
			if(angleOff < 0)
				angleOff--;
			else if(angleOff > 0)
				angleOff++;
			
			pid.setSetpoint(angleOff + pidGet());
			pid.enable();
			setRotation = true;
		} else {
			if(lastFrame < frame) {
				readyToRotate = true;
			}
		}
		
		lastFrame = frame;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if(timeOut != null) {
    		if(timeSinceInitialized() > timeOut) {
    			return true;
    		}
    	}
    	
    	if(!setRotation)
    		return false;
    	
        return Math.abs(pid.getError()) < 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	public void pidWrite(double output) {
		/*if(Math.abs(output) < MIN_TURN_POWER){
			if(pid.onTarget()){
				output = 0;
			}else if(output > 0){
				output = MIN_TURN_POWER;
			}else if(output < 0){
				output = -MIN_TURN_POWER;
			}
		}*/
		if(DEBUG){
			SmartDashboard.putNumber("GyroPID Output", output);
		}
		drive.rotateWithPower(output);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		if(DEBUG){
			SmartDashboard.putNumber("Gyro Rotation", gyro.getRotation());
		}
		// TODO Auto-generated method stub
		return gyro.getRotation();
	}
}
