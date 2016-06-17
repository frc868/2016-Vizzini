package com.techhounds.frc2016;

import com.techhounds.frc2016.commands.CameraCommand;
import com.techhounds.frc2016.commands.EmergencyRelease;
import com.techhounds.frc2016.commands.EndEmergencyRelease;
import com.techhounds.frc2016.commands.MatchSetup;
import com.techhounds.frc2016.commands.SetEndGame;
import com.techhounds.frc2016.commands.SetFlashlight;
import com.techhounds.frc2016.commands.UpdateController;
import com.techhounds.frc2016.commands._experimental.DriveTrajectory;
import com.techhounds.frc2016.commands._experimental.RecordProfile;
import com.techhounds.frc2016.commands.angler.SetStateDown;
import com.techhounds.frc2016.commands.angler.SetStateUp;
import com.techhounds.frc2016.commands.auton.RetrieveAuton;
import com.techhounds.frc2016.commands.auton.RotateUsingVision;
import com.techhounds.frc2016.commands.collector.SetCollectorPower;
import com.techhounds.frc2016.commands.drive_legacy.LockWinch;
import com.techhounds.frc2016.commands.drive_legacy.RunWinch;
import com.techhounds.frc2016.commands.drive_legacy.ToggleDriveDirection;
import com.techhounds.frc2016.commands.servos.SetWinchEnable;
import com.techhounds.frc2016.commands.shooter.Fire;
import com.techhounds.frc2016.commands.shooter.PreFire;
import com.techhounds.frc2016.commands.shooter.SetShooterPower;
import com.techhounds.frc2016.commands.shooter.StopFire;
import com.techhounds.frc2016.commands.shooter.ToggleAlignVision;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.ControllerMap.Direction;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.util.HoundMath;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
	private static OI instance;
	
	private ControllerMap driver;
	private ControllerMap operator;
	private ControllerMap currentDriver;
	
	// DRIVER & OPERATOR CONTROLLER CHOOSER
	private SendableChooser driverChooser;
	private SendableChooser operatorChooser;
	
	// DRIVER AND OPERATOR CONTROLS
	final int startCollector = 		ControllerMap.Key.RT;
	final int stopCollector = 		ControllerMap.Key.RB;
	final int angleUp = 			ControllerMap.Key.Y;
	final int angleDown = 			ControllerMap.Key.A;
	final int startShooterOuterworks = ControllerMap.Key.X;//DPadButton.Direction.UP;
	final int toggleFlashLight = 	DPadButton.Direction.DOWN;
	final int stopShooter = 		DPadButton.Direction.LEFT;
	final int startShooter = 		DPadButton.Direction.UP;//ControllerMap.Key.X;
	final int fireShooter = 		ControllerMap.Key.B;
	final int toggleDrive =			ControllerMap.Key.START;
	final int visionTarget = 		ControllerMap.Key.LT;
	final int newVisionTarget = 	ControllerMap.Key.LB;
	final int emergencyRelease= 	DPadButton.Direction.RIGHT;
	final int toggleGameMode = 		ControllerMap.Key.BACK;
	
	final int runWinchForward = 	ControllerMap.Key.Y;
	final int runWinchBackward = 	ControllerMap.Key.A;
	final int lockWinch = 			ControllerMap.Key.LT;
	final int enableWinch = 		ControllerMap.Key.X;
	final int disableWinch = 		ControllerMap.Key.RB;
	final int unlockWinch = 		ControllerMap.Key.LB;
	final int normalGameMode = 		DPadButton.Direction.RIGHT;
	
	private OI() {
	
		driverChooser = createControllerChooser(true);
		operatorChooser = createControllerChooser(false);

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(HardwareConstants.OI.DRIVER_PORT), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(HardwareConstants.OI.OPERATOR_PORT), (ControllerMap.Type) operatorChooser.getSelected());

		setup();
	}
	
	private void setup() {
		if(Robot.oneControllerMode) {
			setUpController(driver);
		} else {
			setupDriver();
			setupOperator();
		}
		currentDriver = driver;
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
		setUpController(driver);
	}
	
	/**
	 * Gets the Operator Controller Ready with its Buttons
	 */
	public void setupOperator() {
		setUpController(operator);
	}
	
	/**
	 * Setup Single Controller Control
	 */
	public void setUpController(ControllerMap controller) {
		controller.clearButtons();
		
		controller.getButton(emergencyRelease).
			whenPressed(new EmergencyRelease()).
			whenReleased(new EndEmergencyRelease());
		controller.getButton(startCollector)
			.whenPressed(new SetCollectorPower(HardwareConstants.Collector.inPower))
			.whenReleased(new SetCollectorPower());
		
		controller.getButton(stopCollector)
			.whenPressed(new SetCollectorPower(HardwareConstants.Collector.outPower))
			.whenReleased(new SetCollectorPower());
		
		controller.getButton(angleUp)
			.whenPressed(new SetStateUp());
		
		controller.getButton(angleDown)
			.whenPressed(new SetStateDown());
		
		//controller.getButton(downShooterSpeed)
		//	.whenPressed(new IncrementShooterSpeed(1));
		
		controller.getButton(stopShooter)
			.whenPressed(new SetShooterPower());
		
		controller.getButton(startShooter)
			.whenPressed(new PreFire());

		controller.getButton(startShooterOuterworks)
			.whenPressed(new PreFire(71));
		
		controller.getButton(fireShooter)
			.whenPressed(Fire.getInstance())
			.whenReleased(new StopFire());

		if(controller == driver) {
			controller.getButton(toggleDrive)
				.whenPressed(new ToggleDriveDirection());
		} else {
			
				//.whenPressed(new ToggleManualOverride());
		}
		
		
		controller.getButton(visionTarget)
			.whenPressed(new RotateUsingVision(4));
		
		controller.getButton(toggleFlashLight)
			.whenPressed(new SetFlashlight());
		
		controller.getButton(newVisionTarget)
			.whenPressed(new ToggleAlignVision(true))
			.whenReleased(new ToggleAlignVision(false));
		if(controller == operator){
		controller.getMultiButton(ControllerMap.Key.BACK, ControllerMap.Key.START)
			.whenPressed(new SetEndGame(true));
		}
	//	controller.getButton(newVisionTarget)
	//		.whenPressed(new RotateUsingVisionContinuous());
	}
	
	public void initializeEndGame(ControllerMap controller){
		controller.clearButtons();
		controller.getButton(runWinchForward).whileHeld(new RunWinch("Winch Up Power", .5));
		controller.getButton(enableWinch).whenPressed(new SetWinchEnable(!HardwareConstants.Servo.WIN_DT_TO_ARM_UP));
		controller.getButton(disableWinch).whenPressed(new SetWinchEnable(HardwareConstants.Servo.WIN_DT_TO_ARM_UP));
		controller.getButton(runWinchBackward).whileHeld(new RunWinch("Winch Down Power", -.5));
		controller.getButton(lockWinch).whenPressed(new LockWinch(true));
		controller.getButton(unlockWinch).whenPressed(new LockWinch(false));
		controller.getButton(normalGameMode).whenPressed(new SetEndGame(false));
	}
	

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putData("Run Selected Auton", new RetrieveAuton());
		SmartDashboard.putData("Match Set Up", new MatchSetup());
		SmartDashboard.putData("Update The Controllers", new UpdateController());

		SmartDashboard.putData("Disable Collect Camera", new CameraCommand(true, false));
		SmartDashboard.putData("Enable Collect Cam", new CameraCommand(false, true));
		SmartDashboard.putData("Enable Both Cameras", new CameraCommand(true, true));

		SmartDashboard.putData("FLASHLIGHT ON", new SetFlashlight(true));
		SmartDashboard.putData("FLASHLIGHT OFF", new SetFlashlight(false));

		SmartDashboard.putData("Enable Winch", new SetWinchEnable(HardwareConstants.Servo.WINCH_LOCK_UP));
		SmartDashboard.putData("Disable Winch", new SetWinchEnable(HardwareConstants.Servo.WIN_DT_TO_ARM_UP));

		SmartDashboard.putData("Record Profile", new RecordProfile());
		SmartDashboard.putData("Run Recorded Profile", new DriveTrajectory());
		SmartDashboard.putData("Run Saved Profile 0", new DriveTrajectory(0, true));
		SmartDashboard.putData("Run Saved Profile 1", new DriveTrajectory(1, true));
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
	private SendableChooser createControllerChooser(boolean driver) {
		SendableChooser chooser = new SendableChooser();
		chooser.addObject("Logitech", ControllerMap.Type.LOGITECH);
		chooser.addDefault("XBOX ONE", ControllerMap.Type.XBOX_ONE);
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
	public void switchDriver(){
		if(currentDriver.equals(driver)){
			currentDriver = operator;
		}else{
			currentDriver = driver;
		}
	}
	
	public double getSteer() {
		if(Math.abs(currentDriver.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL)) < .25 && currentDriver != operator)
			return operator.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL) * .5;
		else
			return currentDriver.getAxis(ControllerMap.Direction.RIGHT_HORIZONTAL);
	}
	
	public double getRightForward(){
		return HoundMath.checkRange(currentDriver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftForward(){
		return HoundMath.checkRange(currentDriver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
	}
	public double getRightBackward(){
		return HoundMath.checkRange(-currentDriver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftBackward(){
		return HoundMath.checkRange(-currentDriver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
	}
}
