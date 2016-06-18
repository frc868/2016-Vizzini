package com.techhounds.frc2016.behavior.routine;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.Systems;

public class PreFireRoutine extends Routine {

	private boolean gotBallClear = false;
	private boolean gotBallIn = false;
	
	private Systems[] used = new Systems[] {
		Systems.COLLECTOR,
		Systems.SHOOTER
	};
	
	@Override
	public void update() {
		
		if(!gotBallClear) {
			collector.setPower(HardwareConstants.Collector.outPower);
		} else if(!gotBallIn && !beamBreak.ballPresent()) {
			gotBallClear = true;
			collector.setPower(HardwareConstants.Collector.inPower);
		} else {
			collector.setPower(0.0);
			shooter.setSpeed(HardwareConstants.Shooter.SHOOTER_SPEED);
			gotBallIn = true;
		}
	}
	
	public boolean isFinished() {
		return gotBallClear && gotBallIn;
	}
	
	public boolean uses(Systems system) {
		for(Systems u : used)
			if(u == system)
				return true;
		return false;
	}

}
