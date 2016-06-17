package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.commands.servos.ReleaseClimber;
import com.techhounds.frc2016.subsystems.AnglerSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetEndGame extends CommandGroup {
    
	private boolean dontDoEndGame;
	
    public  SetEndGame(boolean endGame) {
    	//addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	if(endGame) {
    		addParallel(new ReleaseClimber(true));
    		addParallel(new SwitchControllerToEndGame(true));
    	} else {
    		addSequential(new SwitchControllerToEndGame(false));
    	}
    }
    
    @Override
    public void initialize() {
    	dontDoEndGame = false;
    	if(AnglerSubsystem.getInstance().getRawPosition() != HardwareConstants.Angler.COLLECT) {
    		dontDoEndGame = true;
    		return;
    	}
    	
    	super.initialize();
    }
    
    @Override
    public boolean isFinished() {
    	if(dontDoEndGame) {
    		return true;
    	}
    	
    	return super.isFinished();
    }
}
