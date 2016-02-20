package com.techhounds;

import com.techhounds.commands.ToggleDriveDirection;
import com.techhounds.commands.USBCameraCommand;
import com.techhounds.commands.UpdateController;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.angler.SetAnglerPower;
import com.techhounds.commands.angler.SetStateDown;
import com.techhounds.commands.angler.SetStateUp;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.servos.SetScissorsOne;
import com.techhounds.commands.servos.SetScissorsTwo;
import com.techhounds.commands.servos.SetWinchEnable;
import com.techhounds.commands.servos.SetWinchLock;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.SetShooterSpeed;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
	private static OI instance;
	
	private ControllerMap driver;
	private ControllerMap operator;
	private int collectorState = 0;
	
	// DRIVER & OPERATOR CONTROLLER CHOOSER
	private SendableChooser driverChooser;
	private SendableChooser operatorChooser;
	
	// DRIVER CONTROLS
	final int startCollector = 		ControllerMap.Key.RT;
	final int stopCollector = 		ControllerMap.Key.RB;
	final int angleUp = 			ControllerMap.Key.Y;
	final int angleDown = 			ControllerMap.Key.A;
	final int upShooterSpeed = 		DPadButton.Direction.UP;
	final int downShooterSpeed = 	DPadButton.Direction.DOWN;
	final int stopShooter = 		DPadButton.Direction.LEFT;
	final int startShooter = 		ControllerMap.Key.X;
	final int fireShooter = 		ControllerMap.Key.B;
	final int collectAngler = 		ControllerMap.Key.LB;
	final int collectDefenses = 	ControllerMap.Key.LT;
	final int toggleDrive =			ControllerMap.Key.START;
	
	private OI() {
	
		driverChooser = createControllerChooser();
		operatorChooser = createControllerChooser();

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(RobotMap.OI_Constants.DRIVER_PORT), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(RobotMap.OI_Constants.OPERATOR_PORT), (ControllerMap.Type) operatorChooser.getSelected());

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
			.whenPressed(new SetCollectorPower(.5))
			.whenReleased(new SetCollectorPower(0));
		
		driver.getButton(stopCollector)
			.whenPressed(new SetCollectorPower(-.5))
			.whenReleased(new SetCollectorPower(0));
		
		
		/*driver.getButton(angleUp)
			.whenPressed(new SetAnglerPosition(100.0))
			//.whenPressed(new SetAnglerPower(.3))
			.whenReleased(new SetAnglerPosition());

		driver.getButton(angleDown)
			.whenPressed(new SetAnglerPosition(518.0));
			//.whenPressed(new SetAnglerPower(-.3))*/
		
		driver.getButton(angleUp)
			.whenPressed(new SetStateUp());
		
		driver.getButton(angleDown)
			.whenPressed(new SetStateDown());

		driver.getButton(upShooterSpeed)
			.whenPressed(new SetShooterPower(.1, true));
		
		driver.getButton(downShooterSpeed)
			.whenPressed(new SetShooterPower(-.1, true));
		
		driver.getButton(stopShooter)
			.whenPressed(new SetShooterPower());
		
		driver.getButton(startShooter)
			.whenPressed(new SetShooterPower(.5));
		
		driver.getButton(fireShooter)
			.whenPressed(new Fire());
		
		driver.getButton(collectAngler)
			.whenPressed(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_HEIGHT));
		
		driver.getButton(collectDefenses)
			.whenPressed(new SetAnglerPosition(RobotMap.Collector.DEFENSE_PASS_HEIGHT));
		driver.getButton(toggleDrive)
			.whenPressed(new ToggleDriveDirection());
	}

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putData("Set Shooter Speed", new SetShooterSpeed());
		SmartDashboard.putData("Update Controllers", new UpdateController());
		SmartDashboard.putData("Toggle Camera", new USBCameraCommand(true));
		//SmartDashboard.putData("Toggle_Scissors_1", new SetScissorsOne());
		//SmartDashboard.putData("Toggle_Scissors_2", new SetScissorsTwo());
		SmartDashboard.putData("Toggle_Winch_Enable", new SetWinchEnable());
		SmartDashboard.putNumber("Shooter Set Speed", 100);
		SmartDashboard.putData("Shooter:", ShooterSubsystem.getInstance());
		//SmartDashboard.putData("Toggle_Winch_Lock", new SetWinchLock());
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
		SmartDashboard.putString("Driver Type", driver.getType().toString());
		SmartDashboard.putString("Operator Type", operator.getType().toString());
	}
	
	public double getRightBackward(){
		return driver.getBackwardsRightPower();
	}
	
	public double getLeftBackward(){
		return driver.getBackwardsLeftPower();
	}
	
	public double getRightForward(){
		return driver.getForwardsRightPower();
	}
	
	public double getLeftForward(){
		return driver.getForwardsLeftPower();
	}
}
