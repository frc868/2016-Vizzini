package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.Debug;
import com.techhounds.commands.SetRumble;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.angler.SetStateDown;
import com.techhounds.commands.angler.SetStateUp;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;
import com.techhounds.commands.drive.CheckForTiltPattern;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.drive.DriveDistanceStraight;
import com.techhounds.commands.drive.DriveUntilTiltPatternWithPower;
import com.techhounds.commands.drive.CheckForTiltPattern.Motion;
import com.techhounds.commands.gyro.ResetGyro;
import com.techhounds.commands.gyro.RotateToLastAngle;
import com.techhounds.commands.gyro.RotateToPreviousAngle;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.commands.gyro.SaveCurrentAngle;
import com.techhounds.commands.shooter.AlignUsingVision;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.PreFire;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.SetShooterSpeed;
import com.techhounds.commands.shooter.WaitForShooterReady;
import com.techhounds.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewAutonChooser {

	private static NewAutonChooser instance;
	
	private NewAutonChooser() {
		setupDashboard();
	}
	
	public static NewAutonChooser getInstance() {
		return instance == null ? instance = new NewAutonChooser() : instance;
	}
	
	public enum Defense {
		LOW_BAR,
		PORTCULLIS,
		CHEVAL_DE_FRISE,
		MOAT,
		RAMPARTS,
		ROCK_WALL,
		ROUGH_TERRAIN,
		REACH_DEFENSE,
		DO_NOTHING
	}
	
	public enum Shoot {
		HIGH,
		LOW,
		ALIGN,
		DO_NOTHING
	}
	
	public enum Post {
		STRAIGHTEN,
		READY_TO_CROSS,
		FACE_DEFENSES,
		NOTHING
	}
	
	private SendableChooser chooseStart;
	private SendableChooser chooseDefense;
	private SendableChooser chooseShoot;
	private SendableChooser choosePost;
	
	private int getStart() {
		return ((Number) chooseStart.getSelected()).intValue();
	}
	
	private Defense getDefense() {
		return ((Defense) chooseDefense.getSelected());
	}
	
	private Shoot getShoot() {
		return ((Shoot) chooseShoot.getSelected());
	}
	
	private int getPost() {
		return ((Number) choosePost.getSelected()).intValue();
	}

	public void setupDashboard() {
		chooseStart = new SendableChooser();
		chooseStart.addObject("Low Bar (1)", new Integer(5));
		chooseStart.addObject("Position 2", new Integer(4));
		chooseStart.addDefault("Position 3", new Integer(3));
		chooseStart.addObject("Position 4", new Integer(2));
		chooseStart.addObject("Secret Passage (5)", new Integer(1));
		chooseStart.addObject("Two Ball Autonomous Midline", new Integer(6));
		
		chooseDefense = new SendableChooser();
		chooseDefense.addObject("A: Portcullis", Defense.PORTCULLIS);
		chooseDefense.addObject("A: Cheval De Frise", Defense.CHEVAL_DE_FRISE);
		chooseDefense.addObject("B: Moat", Defense.MOAT);
		chooseDefense.addObject("B: Ramparts", Defense.RAMPARTS);
		chooseDefense.addObject("D: Rock Wall", Defense.ROCK_WALL);
		chooseDefense.addDefault("D: Rough Terrain", Defense.ROUGH_TERRAIN);
		chooseDefense.addObject("Low Bar", Defense.LOW_BAR);
		chooseDefense.addObject("Reach Defense", Defense.REACH_DEFENSE);
		chooseDefense.addObject("Do Nothing", Defense.DO_NOTHING);
	
		chooseShoot = new SendableChooser();
		chooseShoot.addObject("High Goal", Shoot.HIGH);
		chooseShoot.addObject("Low Goal", Shoot.LOW);
		chooseShoot.addObject("Align To Target", Shoot.ALIGN);
		chooseShoot.addDefault("Do Nothing", Shoot.DO_NOTHING);
		
		choosePost = new SendableChooser();
		choosePost.addObject("Straighten Out", new Integer(3));
		choosePost.addObject("Cross Defenses For Midline", new Integer(2));
		choosePost.addObject("Face the Defenses", new Integer(1));
		choosePost.addObject("Do Nothing", new Integer(0));
		choosePost.addObject("Get Another Ball and Shoot", new Integer(4));

		SmartDashboard.putData("ChooseStart", chooseStart);
		SmartDashboard.putData("ChooseDefense", chooseDefense);
		SmartDashboard.putData("ChooseShoot", chooseShoot);
		SmartDashboard.putData("ChoosePost", choosePost);
	}
	
	public boolean isValid(){
		return true;	
	}
	
	public Command createAutonCommand() {
		SmartDashboard.putBoolean("Valid Auton", isValid());
		if(isValid()) {
			System.out.print("-- VALID AUTON --");
			
			if(getStart() == 6) {
				return new TwoBallFromLine();
			}
			
			return new AutonCommand();
		} else {
			// TODO: If Invalid Auton, just reach the defense so we get points
			return new DriveDistance(RobotMap.Defenses.DEFENSE_DISTANCE, RobotMap.Defenses.TO_DEFENSE_SPEED);
		}
	}
	
	private class AutonCommand extends CommandGroup {
		
		public AutonCommand() {
			this(getStart(), getDefense(), getShoot(), getPost());
		}
		
		public AutonCommand(int start, Defense defense, Shoot shoot, int post) {
			
			addSequential(new SaveCurrentAngle());
						
			switch(defense) {
				case LOW_BAR:
					addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
					addSequential(new DriveUntilTiltPatternWithPower(RobotMap.Defenses.LOW_BAR_SPEED, true, 3));
					break;
				
				case MOAT:
					addSequential(new DriveUntilTiltPatternWithPower(RobotMap.Defenses.MOAT_SPEED, true, 5));
					break;
					
				case RAMPARTS:
					addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.RAMPARTS_SPEED, true, 5));
					addSequential(new RotateToLastAngle(180), 2);
					addSequential(new SaveCurrentAngle());
					break;
					
				case ROCK_WALL:
					addSequential(new DriveUntilTiltPatternWithPower(RobotMap.Defenses.ROCK_WALL_SPEED, true, 5));
					break;
					
				case ROUGH_TERRAIN:
					addSequential(new DriveUntilTiltPatternWithPower(RobotMap.Defenses.ROUGH_TERRAIN_SPEED, true));
					break;
					
				case PORTCULLIS:
					addParallel(new DriveDistance(-RobotMap.Defenses.PORTCULLIS_DISTANCE_1, -RobotMap.Defenses.PORTCULLIS_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
			    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN, 3.0));//Lowers collector to position on ground
			    	addSequential(new WaitCommand(.25));
			    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));//drives through portcullis while raising collector
			    	addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.PORTCULLIS_SPEED_3, new CheckForTiltPattern.Motion [] {
			    			Motion.TILT_BACK,
			    			Motion.FLAT
			    	}, new Double [] { 0.375, 0.5 }, true));
			    	addSequential(new RotateToLastAngle(180), 2);
					addSequential(new SaveCurrentAngle());
					break;
					
				case CHEVAL_DE_FRISE:
					addSequential(new DriveDistance(-RobotMap.Defenses.CDF_DISTANCE_1, -RobotMap.Defenses.CDF_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
			    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN), 2);//lowers collector onto defense
			    	addSequential(new WaitCommand(.25));//waits until defense is lowered
			    	addParallel(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.CDF_SPEED_2, new CheckForTiltPattern.Motion [] {
			    			Motion.TILT_BACK,
			    			Motion.FLAT
			    	}, new Double [] { 0.375, 0.5 }, true));
			    	addSequential(new AnglerWithDelay(.5), 2);
			    	addSequential(new RotateToLastAngle(180), 2);
					addSequential(new SaveCurrentAngle());
					break;
				case DO_NOTHING:
				case REACH_DEFENSE:
					addSequential(new DriveDistanceStraight(RobotMap.Defenses.DEFENSE_DISTANCE, RobotMap.Defenses.TO_DEFENSE_SPEED));
					return;
			}
			
			if(shoot == Shoot.HIGH) {
				addParallel(new SetShooterSpeed(71));
			}
			
			if(start == 5) {
				addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP, 1.0));
				addSequential(new DriveDistanceStraight(60, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
				addSequential(new RotateUsingGyro(65), 1.5);
			} else if(start == 4) {
				addSequential(new DriveDistanceStraight(80, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
				addSequential(new RotateUsingGyro(45), 1.5);
			} else if(start == 3) {
				addSequential(new DriveDistanceStraight(24, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
			} else if(start == 2) {
				addSequential(new DriveDistanceStraight(24, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
			} else if(start == 1) {
				addSequential(new DriveDistanceStraight(80, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER));
			} else {
				return;
			}

			if(shoot == Shoot.HIGH) {
				addParallel(new RotateUsingVisionContinuous(2.25));
				addSequential(new WaitForShooterReady(1));
				addSequential(new WaitCommand(.4));
				addSequential(new SetCollectorPower(1, true));
				addSequential(new WaitCommand(0.5));
				addParallel(new SetCollectorPower(0, true));
				addSequential(new SetShooterPower());
			} else if(shoot == Shoot.LOW) {
				addSequential(new WaitCommand(0));
				addSequential(new RotateUsingVisionContinuous(2.25));
			} else if(shoot == Shoot.ALIGN) {
				addSequential(new WaitCommand(0));
				addSequential(new RotateUsingVisionContinuous(2.25));
			} else if(shoot == Shoot.DO_NOTHING) {
				// DO NOTHING
			} else {
				return;
			}
			
			addSequential(new ResetGyro(ResetGyro.Direction.LEAN));
			
			if(post == 3) {
				addSequential(new RotateToLastAngle());
			} else if(post == 1 || post == 2 || post == 4) {

				addSequential(new RotateToLastAngle());
				
				if(start == 2 || start == 3) {
					addSequential(new DriveDistanceStraight(-20, -1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
				} else if(start == 5){
					addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
					addSequential(new DriveDistanceStraight(-50, -1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
				} else if(start == 4 || start == 1) {
					addSequential(new DriveDistanceStraight(-70, -1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, null, true));
				} else {
					// What position are you in then? lol
				}
				
				if(post == 1)
					return;
				
				switch(defense) {
					case LOW_BAR:
						addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.LOW_BAR_SPEED, true, 3));
						break;
					case MOAT:
						addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.MOAT_SPEED, true, 6));
						break;
					case RAMPARTS:
						addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.RAMPARTS_SPEED, true, 6));
						break;
					case ROCK_WALL:
						addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.ROCK_WALL_SPEED, true, 6));
						break;
					case ROUGH_TERRAIN:
						addSequential(new DriveUntilTiltPatternWithPower(-RobotMap.Defenses.ROUGH_TERRAIN_SPEED, true));
						break;
					case PORTCULLIS:
						addParallel(new DriveUntilTiltPatternWithPower(-0.55));
						addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN, 3.0));
				    	
						if(post != 4)
							addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
						break;
					default:
						break;
				}
				
				if(post == 4) {
					addParallel(new SetCollectorPower(1, true));
					addParallel(new DriveBackAndCheckForBall(-20));
					addSequential(new SetCollectorPower(0, true));
					addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
					
					CommandGroup adjBall = new CommandGroup();
					adjBall.addParallel(new SetCollectorPower(-.4, true));
					adjBall.addSequential(new WaitForBeanBreak(false), .25);
					adjBall.addParallel(new SetCollectorPower(.4, false));
					adjBall.addSequential(new WaitForBeanBreak(true),.25);
					
					addParallel(adjBall);
					
					if(getDefense() == Defense.PORTCULLIS || getDefense() == Defense.RAMPARTS) {
						addSequential(new RotateToLastAngle(180));
					}
					
					if(getStart() == 5) {
						addSequential(new AutonCommand(5, getDefense(), Shoot.HIGH, 2));
					} else {
						addSequential(new AutonCommand(getStart(), getDefense(), Shoot.HIGH, 2));
					}
				}
			}
		}
	}
	
	public void updateSmartDashboard() {
			SmartDashboard.putBoolean("Valid Auton", isValid());
	}
}
