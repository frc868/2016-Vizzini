package com.techhounds;

import com.techhounds.commands.Commando;
import com.techhounds.commands.UpdateController;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.DPadButton;

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
	int driverTestCommando = ControllerMap.Key.RT;
	int driverTestCommandoTwo = DPadButton.Direction.UP;
	
	// OPERATOR CONTROLS
	
	private OI() {
		
		driverChooser = createControllerChooser();
		operatorChooser = createControllerChooser();

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(0), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(1), (ControllerMap.Type) operatorChooser.getSelected());
	
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
		/* if(instance == null){
		 *   instance = new OI();
		 * }
		 * return instance
		 */
	}
	
	/**
	 * Gets the Driver Controller Ready with its Buttons
	 */
	public void setupDriver() {
		
		// SAMPLE USAGE
		driver.getButton(driverTestCommando)
			.whenPressed(new Commando(), new Commando())
			.whenReleased(new Commando())
			.whileHeld(new Commando(), new Commando(), new Commando());
		
		driver.getButton(driverTestCommandoTwo)
			.whenPressed(new Commando())
			.whenReleased(); // It Will Just do A 0 Second Wait Command
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

		// SAMPLE USAGE
		driver.getButton(driverTestCommando)
			.whenPressed(new Commando(), new Commando())
			.whenReleased(new Commando())
			.whileHeld(new Commando(), new Commando(), new Commando());
		
		driver.getButton(driverTestCommandoTwo)
			.whenPressed(new Commando())
			.whenReleased(); // It Will Just do A 0 Second Wait Command
	}

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		// TODO: Add Smart Dashboard Controls
		
		SmartDashboard.putData("Update Controller Choice", new UpdateController());
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
}