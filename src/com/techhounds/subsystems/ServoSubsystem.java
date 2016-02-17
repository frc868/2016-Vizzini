package com.techhounds.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ServoSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	Servo servo;
	static ServoSubsystem instance;
	
	public ServoSubsystem(int port){
		servo = new Servo(port);
		LiveWindow.addActuator("servo", "servo1", servo);
	}
	public static ServoSubsystem getInstance(){
		if(instance == null){
			instance = new ServoSubsystem(2);
		}
		return instance;
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

