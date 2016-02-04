package com.techhounds;

import com.techhounds.commands.Commado;
import com.techhounds.hid.ControllerMap;
import com.techhounds.hid.DPADButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class OI {
    
	private static OI instance;
	
	private ControllerMap driver;
	private ControllerMap operator;
	
	// DRIVER CONTROLS
	int driverTestCommando = ControllerMap.Key.A;
	int driverTestCommandoTwo = DPADButton.Direction.UP;
	
	// OPERATOR CONTROLS
	
	private OI() {
		
		driver = new ControllerMap(new Joystick(0), ControllerMap.Type.LOGITECH);
		operator = new ControllerMap(new Joystick(1), ControllerMap.Type.LOGITECH);
		
		setupDriver();
		setupOperator();
		setupSmartDashboard();
	}
	
	/**
	 * @return Gets the only instance of the OI
	 */
	public static OI getInstance() {
		if(instance == null)
			instance = new OI();
		return instance;
	}
	
	/**
	 * Gets the Driver Controller Ready with its Buttons
	 */
	public void setupDriver() {
		
		// SAMPLE USAGE
		driver.getButton(driverTestCommando)
			.whenPressed(new Commado(), new Commado())
			.whenReleased(new Commado())
			.whileHeld(new Commado(), new Commado(), new Commado());
		
		driver.getButton(driverTestCommandoTwo)
			.whenPressed(new Commado())
			.whenReleased(); // It Will Just do A 0 Second Wait Command
	}
	
	/**
	 * Gets the Operator Controller Ready with its Buttons
	 */
	public void setupOperator() {
		// TODO: Add Operator Controls
	}

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		// TODO: Add Smart Dashboard Controls
	}
}