package com.techhounds.commands.shooter;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForShooterReady extends Command {

	private ShooterSubsystem shooter;
	private Double timeout;
	
	public WaitForShooterReady() {
		this(0);
		this.timeout = null;
	}
	
	public WaitForShooterReady(double timeout) {
		shooter = ShooterSubsystem.getInstance();
		this.timeout = timeout;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
	   if(timeout != null) {
		   if(timeSinceInitialized() > timeout) {
		   		return true;
		   }
	   }
	   return shooter.onTarget();
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
