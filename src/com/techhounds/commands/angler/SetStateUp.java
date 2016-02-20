package com.techhounds.commands.angler;

import com.techhounds.RobotMap;
import com.techhounds.subsystems.AnglerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetStateUp extends Command {
	
	private final AnglerSubsystem angler;

    public SetStateUp() {
        // Use requires() here to declare subsystem dependencies
        requires(AnglerSubsystem.getInstance());
        angler = AnglerSubsystem.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angler.increaseState();
    	if(angler.getState() == 0) {


    		new SetAnglerPosition(RobotMap.Collector.COLLECTOR_MIN_HEIGHT);

    	} else if(angler.getState() == 1) {

    		new SetAnglerPosition(RobotMap.Collector.DEFENSE_PASS_HEIGHT);

    	} else if(angler.getState() == 2) {

    		new SetAnglerPosition(RobotMap.Collector.COLLECTOR_MIN_HEIGHT);

    	} else {
    		System.out.println("OH NO, THERE IS AN ERROR WITH THE STATES!... CALEB!!!");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
