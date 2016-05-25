package com.techhounds.commands.drive;

import com.techhounds.Robot;
import com.techhounds.TheoryCurve;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivePath extends Command{
	TheoryCurve curve;
	int counter;
	double distance;
	double timeOut;
	double speed;
	boolean reverse;
	
	DriveSubsystem drive;
	
	public DrivePath(String startPoint, String controlPoint1, String controlPoint2, String endPoint, double timeOut, double speed)
    {
		this(startPoint, controlPoint1, controlPoint2, endPoint, timeOut, speed, false);
    }
	
	public DrivePath(String startPoint, String controlPoint1, String controlPoint2, String endPoint, double timeOut, double speed, boolean reverse)
    {
		curve = new TheoryCurve(startPoint, controlPoint1, controlPoint2, endPoint);
		distance = curve.findDistance();
		this.timeOut = timeOut;
		this.speed = speed;
		this.reverse = reverse;
		requires(drive = DriveSubsystem.getInstance());
    }
    
    protected void initialize()
    {
    	counter = 0;
    	setTimeout(timeOut);
        drive.encodersReset();
        GyroSubsystem.getInstance().gyrox = GyroSubsystem.getInstance().createRotationGyro();
    }
    
    protected void execute()
    {
    	double dist = drive.countsToDist(drive.getAvgDistance());
    	if(reverse){
    		if(-dist > curve.findHypotenuse(counter) && counter < 19)
        		counter++;
    		
    		drive.setPowerUsePIDCalc(-distance, speed, curve.findAngle(counter), 1);
    	}
    	else{
    		if(dist > curve.findHypotenuse(counter) && counter < 19)
        		counter++;
    		
    		SmartDashboard.putNumber("CounterVal", counter);
    		SmartDashboard.putNumber("DISTANCE", curve.findHypotenuse(counter));
    		drive.setPowerUsePIDCalc(distance, speed, curve.findAngle(counter), 1);
    	}
    }
    
    protected boolean isFinished()
    {
        return drive.countsToDist(drive.getAvgDistance()) > distance || 
        		isTimedOut();
    }
    
    protected void end()
    {
    	drive.setPower(0, 0);
    }
    
    protected void interrupted()
    {
        
    }
}
