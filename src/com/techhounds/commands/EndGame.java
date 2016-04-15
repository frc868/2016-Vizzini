package com.techhounds.commands;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.servos.ReleaseClimber;
import com.techhounds.commands.servos.SetWinchEnable;
import com.techhounds.subsystems.AnglerSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class EndGame extends CommandGroup {
    
	private boolean dontDoEndGame;
	
    public  EndGame() {
    	//addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
    	addParallel(new ReleaseClimber(true));
    	addParallel(new SwitchControllerToEndGame());
    	//addParallel(new SetWinchEnable(RobotMap.Servo.WINCH_LOCK_IS_UP_DEFAULT));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
    
    @Override
    public void initialize() {
    	dontDoEndGame = false;
    	if(AnglerSubsystem.getInstance().getPosition() != RobotMap.Collector.COLLECTING) {
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
