package com.techhounds.frc2016;

import com.techhounds.frc2016.commands.auton.AutonChooser;
import com.techhounds.frc2016.subsystems.AnglerSubsystem;
import com.techhounds.frc2016.subsystems.BeamBreakSubsystem;
import com.techhounds.frc2016.subsystems.CollectorSubsystem;
import com.techhounds.frc2016.subsystems.DriveSubsystem;
import com.techhounds.frc2016.subsystems.FlashlightSubsystem;
import com.techhounds.frc2016.subsystems.GyroSubsystem;
import com.techhounds.frc2016.subsystems.LEDSubsystem;
import com.techhounds.frc2016.subsystems.ServoSubsystem;
import com.techhounds.frc2016.subsystems.ShooterSubsystem;
import com.techhounds.frc2016.subsystems.VisionSubsystem;
import com.techhounds.lib.util.HoundSpeedController;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class HardwareAdaptor {
	
	// Motors
	public static final CANTalon 
		kMotor_Angler = new CANTalon(HardwareMap.Motors.ANGLER),
		kMotor_Collector = new CANTalon(HardwareMap.Motors.COLLECTOR),
		kMotor_Shooter = new CANTalon(HardwareMap.Motors.SHOOTER);
	
	public static final HoundSpeedController
		kMotor_DriveLeft = new HoundSpeedController(
				new Spark(HardwareMap.Motors.DRIVE_LEFT), new int[] {
						HardwareMap.PDP.DRIVE_LEFT_1,
						HardwareMap.PDP.DRIVE_LEFT_2,
						HardwareMap.PDP.DRIVE_LEFT_3						
				}),
		kMotor_DriveRight = new HoundSpeedController(
				new Spark(HardwareMap.Motors.DRIVE_RIGHT), new int[] {
						HardwareMap.PDP.DRIVE_RIGHT_1,
						HardwareMap.PDP.DRIVE_RIGHT_2,
						HardwareMap.PDP.DRIVE_RIGHT_3
				});
			
	public static final Relay
		kFlashlight = new Relay(HardwareMap.Motors.FLASHLIGHT);
	
	// Servos
	public static final ServoInfo
		kWinch_DT_TO_ARM = new ServoInfo(new Servo(HardwareMap.Servos.WINCH_DT_TO_ARM),
				HardwareConstants.Servo.WINCH_DT_TO_ARM_MIN,
				HardwareConstants.Servo.WINCH_DT_TO_ARM_MIN),
		
		kWinch_Lock = new ServoInfo(new Servo(HardwareMap.Servos.WINCH_LOCK),
				HardwareConstants.Servo.WINCH_LOCK_MIN,
				HardwareConstants.Servo.WINCH_LOCK_MAX),
				
		kRelease = new ServoInfo(new Servo(HardwareMap.Servos.ARM_RELEASE),
				HardwareConstants.Servo.ARM_RELEASE_MIN,
				HardwareConstants.Servo.ARM_RELEASE_MAX);
	
	// DIO
	public static final DigitalInput
		kDIO_BeamBreak = new DigitalInput(HardwareMap.DIO.BEAM_BREAK);
	public static final Counter
		kDIO_Shooter = new Counter(new DigitalInput(HardwareMap.DIO.SHOOTER));
	public static final AnalogInput
		kDIO_DRIVE_LIGHT = new AnalogInput(HardwareMap.DIO.DRIVE_LIGHT);
	public static final Encoder
		kDIO_DRIVE_LEFT = new Encoder(HardwareMap.DIO.DRIVE_ENC_LEFT_A, HardwareMap.DIO.DRIVE_ENC_LEFT_B),
		kDIO_DRIVE_RIGHT = new Encoder(HardwareMap.DIO.DRIVE_ENC_RIGHT_A, HardwareMap.DIO.DRIVE_ENC_RIGHT_B);
	public static final DigitalOutput
		kLED_1 = new DigitalOutput(HardwareMap.LED.DIO_MODE_0),
		kLED_2 = new DigitalOutput(HardwareMap.LED.DIO_MODE_1),
		kLED_COLLECTOR = new DigitalOutput(HardwareMap.LED.DIO_COLLECTOR);
	
	// Subsystems
	public static final AnglerSubsystem kAnglerSubsystem = AnglerSubsystem.getInstance();
	public static final BeamBreakSubsystem kBeamBreakSubsystem = BeamBreakSubsystem.getInstance();
	public static final CollectorSubsystem kCollectorSubsystem = CollectorSubsystem.getInstance();
	public static final DriveSubsystem kDriveSubsystem = DriveSubsystem.getInstance();
	public static final GyroSubsystem kGyroSubsystem = GyroSubsystem.getInstance();
	public static final FlashlightSubsystem kFlashlightSubsystem = FlashlightSubsystem.getInstance();
	public static final LEDSubsystem kLEDSubsystem = LEDSubsystem.getInstance();
	public static final ServoSubsystem kServo_WINCH_ENB = ServoSubsystem.getWinchEnable();
	public static final ServoSubsystem kServo_WINCH_LOC = ServoSubsystem.getWinchLock();
	public static final ServoSubsystem kServo_RELEASE = ServoSubsystem.getScissorOne();
	public static final ShooterSubsystem kShooterSubsystem = ShooterSubsystem.getInstance();
	public static final VisionSubsystem kVisionSubsystem = VisionSubsystem.getInstance();
	
	// Power Distribution Panel
	public static final PowerDistributionPanel kPDP = new PowerDistributionPanel();
	
	// Accelerometer
	public static final BuiltInAccelerometer kAccelerometer = new BuiltInAccelerometer();
	
	// Profile Generator
	public static final ProfileGenerator kProfileGenerator = ProfileGenerator.getInstance();
	
	// Operator Interface
	public static final OI kOperatorInterface = OI.getInstance();
	// Autonomous Chooser
	public static final AutonChooser kAutonChooser = AutonChooser.getInstance();
	
	public static void addToLiveWindow() {
		
		LiveWindow.addActuator("AnglerSubsystem", "Angler_Motor", kMotor_Angler);
		LiveWindow.addActuator("CollectorSubsystem", "Collector_Motor", kMotor_Collector);
		LiveWindow.addActuator("ShooterSubsystem", "Shooter_Motor", kMotor_Shooter);
		LiveWindow.addActuator("FlashlightSubsystem", "Flashlight", kFlashlight);

		LiveWindow.addSensor("BeamBreakSubsystem", "BeamBreak", kDIO_BeamBreak);
		LiveWindow.addSensor("DriveSubsystem", "LightSensor", kDIO_DRIVE_LIGHT);
		LiveWindow.addSensor("DriveSubsystem", "EncoderLeft", kDIO_DRIVE_LEFT);
		LiveWindow.addSensor("DriveSubsystem", "EncoderRight", kDIO_DRIVE_RIGHT);
		LiveWindow.addSensor("ShooterSubsystem", "ShooterDIO", kDIO_Shooter);

		LiveWindow.addActuator("LEDSubsystem", "Mode Bit 0", kLED_1);
		LiveWindow.addActuator("LEDSubsystem", "Mode Bit 1", kLED_2);
		LiveWindow.addActuator("LEDSubsystem Collector", "Collector", kLED_COLLECTOR);

		LiveWindow.addActuator("ServoSubsystem", "WinchEnable", kWinch_DT_TO_ARM.servo);
		LiveWindow.addActuator("ServoSubsystem", "WinchLock", kWinch_Lock.servo);
		LiveWindow.addActuator("ServoSubsystem", "Release", kRelease.servo);
	}
}