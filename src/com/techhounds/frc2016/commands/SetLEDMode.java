package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.HardwareAdaptor;
import com.techhounds.frc2016.subsystems.BeamBreakSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.LEDSubsystem;
import com.techhounds.frc2016.subsystems.ShooterSubsystem;
import com.techhounds.frc2016.subsystems.LEDSubsystem.LEDMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLEDMode extends Command {
	
	private LEDSubsystem led;
	private LEDMode state;
	private ShooterSubsystem shooter;
	private BeamBreakSubsystem beam;
	private DriveSubsystem drive;

    public SetLEDMode() {
        shooter = ShooterSubsystem.getInstance();
        drive = DriveSubsystem.getInstance();
        beam = BeamBreakSubsystem.getInstance();
        requires(led = LEDSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = shooter.getSpeed();
    	double setPoint = shooter.getSetPoint();
    	double driveSpeed = drive.getAvgSpeed();
    	boolean collected = beam.ballPresent();
    	
    	led.setCollect(collected);
    	
    	if(shooter.isEnabled()) {
    		if(speed < 10 && setPoint > 10) {
    			led.set(false, true);
    		} else {
    			if(shooter.onTarget()) {
    				led.set(true, true);
    			} else {
    				led.set(true, false);
    			}
    		}
    	} else {
    		led.set(false, false);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
