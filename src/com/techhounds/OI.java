package com.techhounds;

import com.techhounds.commands.ToggleManualOverride;
import com.techhounds.commands.CameraCommand;
import com.techhounds.commands.EmergencyRelease;
import com.techhounds.commands.EndEmergencyRelease;
import com.techhounds.commands.EndGame;
import com.techhounds.commands.MatchSetup;
import com.techhounds.commands.SetFlashlight;
import com.techhounds.commands.UpdateController;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.angler.SetAnglerPower;
import com.techhounds.commands.angler.SetStateDown;
import com.techhounds.commands.angler.SetStateUp;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.auton.CrossDefense;
import com.techhounds.commands.auton.RetrieveAuton;
import com.techhounds.commands.auton.RotateUsingVision;
import com.techhounds.commands.auton.RotateUsingVisionContinuous;
import com.techhounds.commands.auton.StopVisionRotate;
import com.techhounds.commands.auton.VisionRotateToTarget;
import com.techhounds.commands.collector.SetCollector;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.drive.DriveDistanceStraight;
import com.techhounds.commands.drive.DriveEncodersReset;
import com.techhounds.commands.drive.DriveUntilTiltPatternWithPower;
import com.techhounds.commands.drive.LockWinch;
import com.techhounds.commands.drive.RunWinch;
import com.techhounds.commands.drive.ToggleDriveDirection;
import com.techhounds.commands.gyro.ResetGyro;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.commands.gyro.SaveCurrentAngle;
import com.techhounds.commands.servos.ReleaseClimber;
import com.techhounds.commands.servos.SetScissorsTwo;
import com.techhounds.commands.servos.SetWinchEnable;
import com.techhounds.commands.servos.SetWinchLock;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.IncrementShooterSpeed;
import com.techhounds.commands.shooter.PreFire;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.SetShooterSpeed;
import com.techhounds.commands.shooter.SetShooterSpeedFromVision;
import com.techhounds.commands.shooter.StopFire;
import com.techhounds.commands.shooter.ToggleAlignVision;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.hid.ControllerMap.Direction;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
	private static OI instance;
	
	private ControllerMap driver;
	private ControllerMap operator;
	private ControllerMap currentDriver;
	private int collectorState = 0;
	
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
	//final int incrementShooterSpeed = DPadButton.Direction.UP;

	
/*	final int opCollectIn = 		ControllerMap.Key.Y;
	final int opCollectOut = 		ControllerMap.Key.B;
	final int opShooterOn = 		ControllerMap.Key.A;
	final int opShooterOff = 		ControllerMap.Key.X;
	final int opShooterUp = 		DPadButton.Direction.UP;
	final int opShooterDown = 		DPadButton.Direction.DOWN;
*/
	
	
	private OI() {
	
		driverChooser = createControllerChooser(true);
		operatorChooser = createControllerChooser(false);

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(RobotMap.OI_Constants.DRIVER_PORT), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(RobotMap.OI_Constants.OPERATOR_PORT), (ControllerMap.Type) operatorChooser.getSelected());

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
		
		
		/*operator.getButton(opCollectIn).
		whenPressed(new SetCollectorPower(RobotMap.Collector.inPower)).
		whenReleased(new SetCollectorPower());
		
		operator.getButton(opCollectOut).
		whenPressed(new SetCollectorPower(RobotMap.Collector.outPower)).
		whenReleased(new SetCollectorPower());
		
		operator.getButton(opShooterOn).whenPressed(new SetShooterSpeed(69)); 
		//This will change to the line below when we implement the vision distance tracking to shooter speed
		//operator.getButton(opShooterOn).whenPressed(new SetShooterSpeedFromVision());
		
		operator.getButton(opShooterOff).whenPressed(new SetShooterSpeed(0));
		
		operator.getButton(opShooterUp).whenPressed(new IncrementShooterSpeed(1));
		operator.getButton(opShooterDown).whenPressed(new IncrementShooterSpeed(-1));*/
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
			.whenPressed(new SetCollectorPower(RobotMap.Collector.inPower))
			.whenReleased(new SetCollectorPower());
		
		controller.getButton(stopCollector)
			.whenPressed(new SetCollectorPower(RobotMap.Collector.outPower))
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
			.whenPressed(new EndGame());
		}
	//	controller.getButton(newVisionTarget)
	//		.whenPressed(new RotateUsingVisionContinuous());
	}
	
	public void initializeEndGame(ControllerMap controller){
		controller.clearButtons();
		controller.getButton(runWinchForward).whileHeld(new RunWinch("Winch Up Power", .5));
		controller.getButton(enableWinch).whenPressed(new SetWinchEnable(!RobotMap.Servo.WINCH_ENABLE_IS_UP_DEFAULT));
		controller.getButton(disableWinch).whenPressed(new SetWinchEnable(RobotMap.Servo.WINCH_ENABLE_IS_UP_DEFAULT));
		controller.getButton(runWinchBackward).whileHeld(new RunWinch("Winch Down Power", -.5));
		controller.getButton(lockWinch).whenPressed(new LockWinch(true));
		controller.getButton(unlockWinch).whenPressed(new LockWinch(false));
	}
	

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putData("Run Selected Auton", new RetrieveAuton());
		SmartDashboard.putData("Match Set Up", new MatchSetup());
		SmartDashboard.putData("Drive Until Passed Defense", new DriveUntilTiltPatternWithPower(.55, true));
		SmartDashboard.putData("Drive 24 Inches Straight", new DriveDistanceStraight(24, 1, 0.2, null, true));
		SmartDashboard.putData("Reset Lean", new ResetGyro(ResetGyro.Direction.LEAN));
		SmartDashboard.putData("Drive Until Passed Defense Back", new DriveUntilTiltPatternWithPower(-.55, true));
		SmartDashboard.putData("Drive Thru Lowbar", new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
		SmartDashboard.putData("Drive Back Lowbar", new DriveDistanceStraight(-RobotMap.Defenses.LOW_BAR_DISTANCE, -RobotMap.Defenses.LOW_BAR_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
		SmartDashboard.putData("Vision Align To Target", new RotateUsingVision(5));
//		if(RotateUsingGyro.DEBUG){//This will not show in the SD up unless we're debugging RotateUsingGyro
			SmartDashboard.putData("Rotate 60 Degrees", new RotateUsingGyro(60));
//			SmartDashboard.putData("Rotate -90 Degrees", new RotateUsingGyro(-90));
//		}
		
		SmartDashboard.putData("Save Angle", new SaveCurrentAngle());
		SmartDashboard.putData("Drive Distance Straight", new DriveDistanceStraight(1000, 0.5, 0.2, null, true));
//		SmartDashboard.putData("Reset Drive Encoders", new DriveEncodersReset());
//		SmartDashboard.putData("Set Shooter Speed", new SetShooterSpeed(69));
		SmartDashboard.putData("Update The Controllers", new UpdateController());
		//SmartDashboard.putData("Shooter PID", ShooterSubsystem.getInstance().getController());
		//SmartDashboard.putData("Toggle_Scissors_1", new SetScissorsOne());
		//SmartDashboard.putData("Toggle_Scissors_2", new SetScissorsTwo());
//		SmartDashboard.putData("Toggle_Winch_Enable", new SetWinchEnable());
//		SmartDashboard.putNumber("Shooter Set Speed", 100);
//		SmartDashboard.putData("Shooter:", ShooterSubsystem.getInstance());
//		SmartDashboard.putData("Drive Distance", new DriveDistance(45, 1, .35));
//		SmartDashboard.putNumber("Distance To Drive", 90);
//		SmartDashboard.putData(DriveSubsystem.getInstance());

//		SmartDashboard.putData("Enable USB Camera", new USBCameraCommand(true));
		SmartDashboard.putData("Disable Collect Camera", new CameraCommand(true, false));
		SmartDashboard.putData("Enable Collect Cam", new CameraCommand(false, true));
		SmartDashboard.putData("Enable Both Cameras", new CameraCommand(true, true));

//		SmartDashboard.putData("Set Angler Position", new SetAnglerPosition());
//		SmartDashboard.putNumber("Angler Set Height", 47);
		//SmartDashboard.putData("Toggle_Winch_Lock", new SetWinchLock());
		SmartDashboard.putData("FLASHLIGHT ON", new SetFlashlight(true));
		SmartDashboard.putData("FLASHLIGHT OFF", new SetFlashlight(false));
		SmartDashboard.putData("Auton Command", new RetrieveAuton());
		SmartDashboard.putData("Enable Winch", new SetWinchEnable(RobotMap.Servo.WINCH_LOCK_IS_UP_DEFAULT));
		SmartDashboard.putData("Disable Winch", new SetWinchEnable(RobotMap.Servo.WINCH_ENABLE_IS_UP_DEFAULT));
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
		
		//if(!driver)
		//	chooser.addDefault("Logitech", ControllerMap.Type.LOGITECH);
		//else
			chooser.addObject("Logitech", ControllerMap.Type.LOGITECH);
		
		//if(driver)
			chooser.addDefault("XBOX ONE", ControllerMap.Type.XBOX_ONE);
		//else
		//	chooser.addObject("XBOX ONE", ControllerMap.Type.XBOX_ONE);
		
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
		return Robot.rangeCheck(currentDriver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftForward(){
		return Robot.rangeCheck(currentDriver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
	}
	public double getRightBackward(){
		return Robot.rangeCheck(-currentDriver.getAxis(Direction.LEFT_VERTICAL) - getSteer());
	}
	public double getLeftBackward(){
		return Robot.rangeCheck(-currentDriver.getAxis(Direction.LEFT_VERTICAL) + getSteer());
	}
}
