package com.techhounds;

import com.techhounds.commands.*;
import com.techhounds.lib.hid.Button;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.hid.JoystickButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
	private static OI instance;
	
	private ControllerMap driver;
	private ControllerMap operator;
	
	// DRIVER & OPERATOR CONTROLLER CHOOSER
	private SendableChooser driverChooser;
	private SendableChooser operatorChooser;
	
	// DRIVER CONTROLS
	int startCollector = 	ControllerMap.Key.RT;
	int stopCollector = 	ControllerMap.Key.RB;
	int angleUp = 			ControllerMap.Key.LT;
	int angleDown = 		ControllerMap.Key.LB;
	int upShooterSpeed = 	ControllerMap.Key.X;
	int downShooterSpeed = 	ControllerMap.Key.Y;
	int stopShooter = 		ControllerMap.Key.B;
	int startShooter = 		ControllerMap.Key.A;
	
	// OPERATOR CONTROLS
	
	private OI() {
		
		driverChooser = createControllerChooser();
		operatorChooser = createControllerChooser();

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(RobotMap.OI.DRIVER_PORT), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(RobotMap.OI.OPERATOR_PORT), (ControllerMap.Type) operatorChooser.getSelected());
		
		setup();
	}
	
	private void setup() {
		if(Robot.oneControllerMode) {
			setupController();
		} else {
			setupDriver();
			setupOperator();
		}

		setupSmartDashboard();
	}
	
	/**
	 * @return Gets the only instance of the OI
	 */
	public static OI getInstance() {
		return instance == null ? instance = new OI(): instance;
	}
	
	/**
	 * Gets the Driver Controller Ready with its Buttons
	 */
	public void setupDriver() {
		
	}
	
	/**
	 * Gets the Operator Controller Ready with its Buttons
	 */
	public void setupOperator() {
		// TODO: Add Operator Controls
	}
	
	/**
	 * Setup Single Controller Control
	 */
	public void setupController() {
		
		driver.getButton(startCollector)
			.whenPressed(new CollectorCommand(.5));
		
		driver.getButton(stopCollector)
			.whenPressed(new CollectorCommand());
		
		driver.getButton(angleUp)
			.whenPressed(new AnglerCommand(.95));
		
		driver.getButton(angleDown)
			.whenPressed(new AnglerCommand(.05));
		
		driver.getButton(upShooterSpeed)
			.whenPressed(new ChangeShooterSpeedCommand(.3));
		
		driver.getButton(downShooterSpeed)
			.whenPressed(new ChangeShooterSpeedCommand(-.3));
		
		driver.getButton(stopShooter)
			.whenPressed(new ShooterCommand());
		
		driver.getButton(startShooter)
			.whenPressed(new ShooterCommand(.5));
	}

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putData("Update Controllers", new UpdateController());
	}
	
	/**
	 * Gets the Driver's Gamepad
	 */
	public ControllerMap getDriver() {
		return driver;
	}
	
	/**
	 * Gets the Operator's Gamepad
	 */
	public ControllerMap getOperator() {
		return operator;
	}
	
	/**
	 * Create Chooser for Controllers
	 */
	private SendableChooser createControllerChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault("Logitech", ControllerMap.Type.LOGITECH);
		chooser.addObject("XBOX ONE", ControllerMap.Type.XBOX_ONE);
		chooser.addObject("XBOX 360", ControllerMap.Type.XBOX_360);
		chooser.addObject("Playstation 4", ControllerMap.Type.PS4);
		return chooser;
	}
	
	/** 
	 * Update Controllers
	 */
	public void updateControllers() {
		driver.setControllerType((ControllerMap.Type) driverChooser.getSelected());
		operator.setControllerType((ControllerMap.Type) operatorChooser.getSelected());
		
		setup();
	}
	
	/** 
	 * Update dashboard when Called
	 */
	public void updateDashboard() {
		SmartDashboard.putString("Driver Type", getControllerString(driver));
		SmartDashboard.putString("Operator Type", getControllerString(operator));
	}
	
	public String getControllerString(ControllerMap joystick) {
		switch(joystick.getType()) {
			case PS4:
				return "PS4";
			case XBOX_360:
				return "XBOX_360";
			case XBOX_ONE:
				return "XBOX_ONE";
			case LOGITECH:
				return "LOGITECH";
			default:
				return "";
			
		}
	}
	
	public double getRight(){
		return driver.getRightPower();
	}
	
	public double getLeft(){
		return driver.getLeftPower();
	}
}