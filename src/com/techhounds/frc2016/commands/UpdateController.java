package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateController extends Command {

    public UpdateController() {
    	setRunWhenDisabled(true);
    }

    protected void initialize() {
    	OI.getInstance().updateControllers();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    }
}
