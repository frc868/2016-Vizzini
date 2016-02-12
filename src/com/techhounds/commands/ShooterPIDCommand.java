package com.techhounds.commands;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class ShooterPIDCommand extends Command {
	
	private ShooterSubsystem shoot;
	private final double P = 0, I = 0, D = 0;
	private double setPower = 0;
	private PIDController controller;
	
    public ShooterPIDCommand(double set) {
    	shoot = ShooterSubsystem.getInstance();
    	requires(shoot);
    	setPower = set;
    	controller = new PIDController(P, I, D, new PIDSource(){

			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
			}
			
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public double pidGet() {
				return shoot.getSpeed();
			}
    		
    	}, new PIDOutput(){
    		
			public void pidWrite(double output) {
				shoot.setPower(output);
			}
    		
    	});
    	controller.setAbsoluteTolerance(3);
    	controller.setOutputRange(-1, 1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	controller.setSetpoint(setPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return controller.onTarget();
    }
    // Called once after isFinished returns true
    protected void end() {
    	controller.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
