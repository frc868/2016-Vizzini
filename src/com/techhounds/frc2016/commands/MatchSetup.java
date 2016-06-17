package com.techhounds.frc2016.commands;

import com.techhounds.frc2016.HardwareConstants;
import com.techhounds.frc2016.commands.angler.SetAnglerPosition;
import com.techhounds.frc2016.commands.collector.SetCollectorPower;
import com.techhounds.frc2016.commands.servos.ReleaseClimber;
import com.techhounds.frc2016.commands.servos.SetWinchEnable;
import com.techhounds.frc2016.commands.shooter.SetShooterPower;
import com.techhounds.frc2016.subsystems.AnglerSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MatchSetup extends CommandGroup {
    
    public  MatchSetup() {
    	
    	addParallel(new SetAnglerPosition(HardwareConstants.Angler.ANGLER_UP));
    	addParallel(new SetCollectorPower());
    	addParallel(new SetShooterPower());
    	addParallel(new ReleaseClimber(false));
    	addParallel(new SwitchControllerToNormal());
    	addParallel(new SetWinchEnable(HardwareConstants.Servo.WIN_DT_TO_ARM_UP));
    }
    
    public void initialize() {
    	super.initialize();

    	DriveSubsystem.isForward = false;
    	AnglerSubsystem.getInstance().resetState();
    	
    }
}
