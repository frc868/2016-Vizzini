package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.OI;
import com.techhounds.frc2016.commands.auton.AutonChooser;
import com.techhounds.frc2016.subsystems.*;
import com.techhounds.lib.util.HoundSubsystem;
import com.techhounds.lib.util.PeriodicCommand;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class UpdateSmartDashboard extends PeriodicCommand {

    public UpdateSmartDashboard() {
    	setRunWhenDisabled(true);
    }
 
	@Override
	protected void init() {
		
	}

	@Override
	protected void doRun() {
		HoundSubsystem.updateSubsystemsPeriodic();
		OI.getInstance().updateDashboard();
		GyroSubsystem.getInstance().updateSmartDashboard();
		
	}

	@Override
	protected boolean doFinish() {
		return false;
	}

	@Override
	protected void end() {
	}
}
