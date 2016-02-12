package com.techhounds.commands;

import com.techhounds.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *   This is a basic placeholder/test command that will utilized the vision subsystem to... do something...    maybe.
 */
public class VisionAutoCorrectCommand extends CommandGroup {
	
	private VisionSubsystem sight;
	double degreeOffAllowed/*(In either direction)*/ = 3;//This can be changed as needed
    
    public  VisionAutoCorrectCommand() {
    	sight = VisionSubsystem.getInstance();
    	requires(sight);
    	if(sight.getAngle() >= degreeOffAllowed ){
//Use drive Command to turn to correct angle    		
    	}else if(sight.getAngle() <= -degreeOffAllowed){
//Use drive Command to turn to correct angle (from opposite direction)    		
    	}
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
}
