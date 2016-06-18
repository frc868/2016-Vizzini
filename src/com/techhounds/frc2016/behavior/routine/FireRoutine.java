package com.techhounds.frc2016.behavior.routine;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.Systems;

import edu.wpi.first.wpilibj.Timer;

public class FireRoutine extends Routine {

	private Double initTime;
	
	private Systems[] used = new Systems[] {
			Systems.SHOOTER,
			Systems.COLLECTOR
	};
	
	@Override
	public void update() {
		
		if(initTime == null)
			initTime = Timer.getFPGATimestamp();
		
		collector.setPower(HardwareConstants.Collector.shooterPower);
	}

	@Override
	public boolean isFinished() {
		if(Timer.getFPGATimestamp() - initTime > 1.0) {
			shooter.setPower(0.0);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean uses(Systems system) {
		for(Systems u : used)
			if(u == system)
				return true;
		return false;
	}

}
