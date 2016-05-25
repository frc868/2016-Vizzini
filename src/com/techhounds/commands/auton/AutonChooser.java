package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.Debug;
import com.techhounds.commands.SetRumble;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.angler.SetStateDown;
import com.techhounds.commands.angler.SetStateUp;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.drive.DriveDistanceStraight;
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

public class AutonChooser {

	private static AutonChooser instance;
	
	private AutonChooser() {
		setupDashboard();
	}
	
	public static AutonChooser getInstance() {
		return instance == null ? instance = new AutonChooser() : instance;
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
	
	public enum BallOrigin {
		LINE,
		DO_NOTHING
	}
	public enum Shoot {
		
	}
	public enum Post {
		STRAIGHTEN,
		READY_TO_CROSS,
		FACE_DEFENSES,
		NOTHING
	}
	
	private SendableChooser chooseStart;
	private SendableChooser chooseDefense;
	private SendableChooser chooseBallOrigin;
	private SendableChooser chooseShoot;
	private SendableChooser choosePost;
	
	private int getStart() {
		return ((Number) chooseStart.getSelected()).intValue();
	}
	
	private Defense getDefense() {
		return ((Defense) chooseDefense.getSelected());
	}
	
	private int getShoot() {
		return ((Number) chooseShoot.getSelected()).intValue();
	}
	
	private BallOrigin getBallOrigin() {
		return ((BallOrigin) chooseBallOrigin.getSelected());		
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
		chooseStart.addObject("Two Ball Autonomous For Mid Defense", new Integer(7));
		
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
		
		chooseBallOrigin = new SendableChooser();
		chooseBallOrigin.addObject("Ball From Midline", BallOrigin.LINE);
		chooseBallOrigin.addDefault("Do Nothing", BallOrigin.DO_NOTHING);
		
		chooseShoot = new SendableChooser();
		chooseShoot.addObject("High Goal", new Integer(0));
		chooseShoot.addObject("Low Goal", new Integer(1));
		chooseShoot.addObject("Align To Target", new Integer(2));
		chooseShoot.addDefault("Do Nothing", new Integer(3));
		
		choosePost = new SendableChooser();
		choosePost.addObject("Straighten Out", new Integer(3));
		choosePost.addObject("Cross Defenses For Midline", new Integer(2));
		choosePost.addObject("Face the Defenses", new Integer(1));
		choosePost.addObject("Do Nothing", new Integer(0));
		choosePost.addObject("Go Back Get Ball & Shoot", new Integer(4));


		SmartDashboard.putData("ChooseStart", chooseStart);
		SmartDashboard.putData("ChooseDefense", chooseDefense);
		SmartDashboard.putData("ChooseBallOrigin", chooseBallOrigin);
		SmartDashboard.putData("ChooseShoot", chooseShoot);
		SmartDashboard.putData("ChoosePost", choosePost);
	}
	
	public boolean isValid(){
		return true;
/*		
		int start = getStart();
		Defense defense = getDefense();
		Goal goal = getGoal();
		int shoot = getShoot();
		int post = getPost();
		
		if(start == 5) {
			if(defense == Defense.LOW_BAR) {
				if(goal == Goal.LEFT) {
					return true;
				}
			} else if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
			} 
		} else if(start == 4) {
			if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
			} else if(defense != null && defense != Defense.LOW_BAR) {
				if(goal == Goal.LEFT) {
					return true;
				} else if(goal == Goal.MIDDLE) {
					if(shoot != 1) {
						return true;
					}
				} 
			}
		} else if(start == 3 || start == 2 || start == 1) {
			if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
			} else if(defense != null && defense != Defense.LOW_BAR) {
				if(goal == Goal.MIDDLE) {
					if(shoot != 1) {
						return true;
					}
				}
			}
		} else {
			return false;
		}
*/		
	}
	
	public Command createAutonCommand() {
		SmartDashboard.putBoolean("Valid Auton", isValid());
		if(isValid()) {
			System.out.print("-- VALID AUTON --");
			
			if(getStart() == 6) {
				// TWO BALL AUTON --> SPECIAL SETUP
				
/*				CommandGroup twoBall = new CommandGroup();
/				twoBall.addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING)); // SET DOWN
				twoBall.addParallel(new SetCollectorPower(RobotMap.Collector.inPower, true)); // COLLECTING
				twoBall.addSequential(new WaitForBeanBreak(true)); // UNTIL WE GOT A BALL
				twoBall.addParallel(new AutonCommand(5, Defense.LOW_BAR, Goal.LEFT, 0, 3)); // AUTON COMMAND
				twoBall.addParallel(new SetCollectorPower(-.2, true)); // POSITION BALL
				twoBall.addSequential(new WaitForBeanBreak(false)); // UNTIL BALL IS IN GOOD POSITION
/*DRIVE BACK	twoBall.addParallel(new DriveDistance(-RobotMap.Defenses.LOW_BAR_DISTANCE, -RobotMap.Defenses.LOW_BAR_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5));
				twoBall.addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING)); // SET DOWN
				twoBall.addParallel(new SetCollectorPower(RobotMap.Collector.inPower, true)); // COLLECTING
				twoBall.addSequential(new WaitForBeanBreak(true)); // UNTIL WE GOT A BALL
				twoBall.addParallel(new AutonCommand(5, Defense.LOW_BAR, Goal.LEFT, 0, 0)); // AUTON COMMAND
				twoBall.addParallel(new SetCollectorPower(-.2, true)); // POSITION BALL
				twoBall.addSequential(new WaitForBeanBreak(false)); // UNTIL BALL IN GOOD POSITION
			*/	
				return new TwoBallFromLine();
			} else if(getStart() == 7) {
				return new TwoBallForMidDefense(getDefense(), true);
			}
			
			return new AutonCommand();
		} else {
			// TODO: If Invalid Auton, just reach the defense so we get points
			return new DriveDistance(RobotMap.Defenses.DEFENSE_DISTANCE, RobotMap.Defenses.TO_DEFENSE_SPEED);
		}
	}
	
	private class AutonCommand extends CommandGroup {
		
		public AutonCommand() {
			this(getStart(), getDefense(), getBallOrigin(), getShoot(), getPost());
		}
		
		public AutonCommand(int start, Defense defense, BallOrigin ball, int shoot, int post) {
			
			// Save Initial Angle
			addSequential(new SaveCurrentAngle());
						
			if(ball == BallOrigin.LINE) {
				
				CommandGroup adjBall = new CommandGroup();
				adjBall.addSequential(new WaitCommand(0.5));
				adjBall.addParallel(new SetCollectorPower(-.4, true));
				adjBall.addSequential(new WaitForBeanBreak(false), .375);
				adjBall.addParallel(new SetCollectorPower(.4, false));
				adjBall.addSequential(new WaitForBeanBreak(true),.375);
				
		    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
		    	addParallel(new SetCollectorPower(RobotMap.Collector.inPower));
		    	addSequential(new WaitForBeanBreak(true), 2.5);
		    	addSequential(new SetCollectorPower(0));
		    	addParallel(adjBall);
			}
			
			if(defense != Defense.LOW_BAR)
				addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
			
			// We will add the Defense that will be crossing
			switch(defense) {
				case LOW_BAR:
					addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
					//addSequential(new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE * 3 / 4 , RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 4.0, true));
					//addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
					addSequential(new DriveDistanceStraight(RobotMap.Defenses.LOW_BAR_DISTANCE, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
					break;
				
				case MOAT:
					addSequential(new DriveDistanceStraight(RobotMap.Defenses.MOAT_DISTANCE, RobotMap.Defenses.MOAT_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
					break;
					
				case RAMPARTS:
					addSequential(new DriveDistanceStraight(-RobotMap.Defenses.RAMPARTS_DISTANCE, -RobotMap.Defenses.RAMPARTS_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
					addSequential(new RotateToLastAngle(180), 3);
					break;
					
				case ROCK_WALL:
					addSequential(new DriveDistanceStraight(RobotMap.Defenses.ROCK_WALL_DISTANCE, RobotMap.Defenses.ROCK_WALL_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
					break;
					
				case ROUGH_TERRAIN:
					addSequential(new DriveDistanceStraight(RobotMap.Defenses.ROUGH_TERRAIN_DISTANCE, RobotMap.Defenses.ROUGH_TERRAIN_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
					break;
					
				case PORTCULLIS:
					addSequential(new CrossPortcullis());
					break;
					
				case CHEVAL_DE_FRISE:
					addSequential(new CrossCDF());
					break;
					
				case DO_NOTHING:
				case REACH_DEFENSE:
					addSequential(new DriveDistance(RobotMap.Defenses.DEFENSE_DISTANCE, RobotMap.Defenses.TO_DEFENSE_SPEED));
					return;
			}
			
			if(defense == Defense.RAMPARTS || defense == Defense.CHEVAL_DE_FRISE || defense == Defense.PORTCULLIS)
				addSequential(new SaveCurrentAngle());
			
			if(shoot == 0) {
				if(defense == Defense.LOW_BAR){
					addParallel(new SetShooterSpeed(68.5));
				} else if(defense == Defense.CHEVAL_DE_FRISE) {
					addParallel(new SetShooterSpeed(69));
				}else {
					addParallel(new SetShooterSpeed(71));
				}
			}

			
			double TEMP_BACK_DEFENSE = 48;
			
			addSequential(new Debug("AUTON", "" + 1));
			
			if(start == 5) {
				// LOW BAR AUTONOMOUS (LEFT)
				addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP, 1.0));
				addSequential(new RotateUsingGyro(58), 2); //44
				
				if(post != 4 && post != 2)
					addSequential(new DriveDistance(44, RobotMap.Defenses.TO_DEFENSE_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2));
			} else if(start == 4) {
				addSequential(new DriveDistance(TEMP_BACK_DEFENSE, RobotMap.Defenses.TO_DEFENSE_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2));
				addSequential(new RotateUsingGyro(53));
			} else if(start == 3) {
				// PERFECT
			} else if(start == 2) {
				// PERFECT
				addSequential(new Debug("AUTON", "" + 2));
			} else if(start == 1) {
				// BRING BACK TOO CLOSE (MIDDLE)
				addSequential(new DriveDistance(-TEMP_BACK_DEFENSE, -RobotMap.Defenses.TO_DEFENSE_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2));
				addSequential(new RotateUsingGyro(-50), 1.25);
				addSequential(new DriveDistance(60), 1.25);
				addSequential(new RotateUsingGyro(35), 1);
			} // WHEN BALL IN NOTIFY USING LEDS
			
//			addSequential(new RotateUsingVision(2));

			addSequential(new Debug("AUTON", "" + 3));
			if(shoot == 0) {
				if(post == 2 || post == 4){
					addSequential(new RotateUsingVisionContinuous(3)); // Used to be 3
				}else{
					addSequential(new RotateUsingVisionContinuous(4));
				}
				//addSequential(new DriveDistance(12, RobotMap.Defenses.TO_DEFENSE_SPEED), 1.25);
				addSequential(new WaitCommand(.5));
				addSequential(new WaitForShooterReady(1));

				addSequential(new SetCollectorPower(1, true));
				addSequential(new WaitCommand(0.625));
				addParallel(new SetCollectorPower(0, true));
				addSequential(new SetShooterPower());
			} else if(shoot == 1) {
				
				addSequential(new WaitCommand(0));
				addSequential(new RotateUsingVision());
			} else if(shoot == 2) {
				addSequential(new WaitCommand(0));
				addSequential(new RotateUsingVision());
			} else {
				// Well I guess DO NOTHING
			}
			
			if(post == 3) {
				addSequential(new RotateToLastAngle());
			} else if(post == 2 || post == 4) {
				addSequential(new RotateToLastAngle());
				
				//if(start == 4) {
				//	addSequential(new DriveDistance(-24, -RobotMap.Defenses.LOW_BAR_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2));
				//}
				
				switch(defense) {
					case LOW_BAR:
						addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
						addSequential(new DriveDistanceStraight(-RobotMap.Defenses.LOW_BAR_DISTANCE + 42, 
								-RobotMap.Defenses.LOW_BAR_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
						break;
					
					case MOAT:
						addSequential(new DriveDistanceStraight(-RobotMap.Defenses.MOAT_DISTANCE + 39, 
								-RobotMap.Defenses.MOAT_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
						break;
						
					case RAMPARTS:
						addSequential(new DriveDistanceStraight(-RobotMap.Defenses.RAMPARTS_DISTANCE + 39, 
								-RobotMap.Defenses.RAMPARTS_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
						break;
						
					case ROCK_WALL:
						addSequential(new DriveDistanceStraight(-RobotMap.Defenses.ROCK_WALL_DISTANCE + 39, 
								-RobotMap.Defenses.ROCK_WALL_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
						break;
						
					case ROUGH_TERRAIN:
						addSequential(new DriveDistanceStraight(-RobotMap.Defenses.ROUGH_TERRAIN_DISTANCE + 39, 
								-RobotMap.Defenses.ROUGH_TERRAIN_SPEED, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5.0, true));
						break;	
					case PORTCULLIS:
						addParallel(new DriveDistance(-RobotMap.Defenses.PORTCULLIS_DISTANCE_3, -RobotMap.Defenses.PORTCULLIS_SPEED_1, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));//drives up on to ramp to its position
				    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_DOWN));//Lowers collector to position on ground
				    	addSequential(new WaitCommand(.75));
				    	addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));//drives through portcullis while raising collector
				    	addSequential(new DriveDistance(-RobotMap.Defenses.PORTCULLIS_DISTANCE_1 + 36, -RobotMap.Defenses.PORTCULLIS_SPEED_3, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER));
						break;
					case CHEVAL_DE_FRISE:
						addSequential(new DriveDistanceStraight(-30, -0.6, -RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2.5, true));
						break;
					case DO_NOTHING:
					case REACH_DEFENSE:
					default:
						break;
						
				}
				
				if(post == 4) {
					addParallel(new SetCollectorPower(0.8));
					addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
					addSequential(new DriveBackAndCheckForBall(-8, -0.4));
					addSequential(new WaitForBeanBreak(true));
					addSequential(new AutonCommand(getStart(), getDefense(), BallOrigin.DO_NOTHING, getShoot(), 2));
				} else {
					addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
				}
			} else if(post == 1) {
				// Face Defenses
				addSequential(new RotateToLastAngle(180));
		//		addSequential(new RotateUsingGyro(180, 3, 0));
			} else {
				// Do Nothing
			}
			
			if(true)
				return;
			/*
			
			addSequential(new DriveDistance(RobotMap.Defenses.LOW_BAR_DISTANCE + 36, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 5));
			addSequential(new RotateUsingGyro(45));
			addSequential(new RotateUsingVision());
			addSequential(new DriveDistance(24, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));
			addSequential(new SetShooterSpeed(69));
			addSequential(new WaitCommand(1));
			addSequential(new RotateUsingVision());
			addSequential(new WaitCommand(1));
			addSequential(new WaitForShooterReady(1));
			addSequential(new SetCollectorPower(.6, true));
			addSequential(new WaitCommand(.5));
			addSequential(new SetCollectorPower(0, true));
			addSequential(new SetShooterPower());
			
			if(start == 6) {
				addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
				addSequential(new CrossDefense(RobotMap.Defenses.LOW_BAR_DISTANCE - 10, RobotMap.Defenses.LOW_BAR_SPEED));	
				addSequential(new DriveDistance(43));
				addSequential(new SaveCurrentAngle());
				addSequential(new RotateUsingGyro(60));
				addSequential(new WaitCommand(2));
				addSequential(new RotateToPreviousAngle());
				addSequential(new RotateUsingGyro(180));
				addSequential(new DriveDistance(43));
				addSequential(new SaveCurrentAngle());
      			addSequential(new DriveDistance(RobotMap.Defenses.LOW_BAR_DISTANCE - 10, RobotMap.Defenses.LOW_BAR_SPEED, RobotMap.DriveTrain.MIN_STRAIGHT_POWER));
				addSequential(new RotateToPreviousAngle());
				addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_UP));
				addSequential(new RotateUsingGyro(180));
				addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
				return;
			}*/
			
			
			
			// We have only made it here if not Reaching Defense or Do Nothing
			// We are waiting so that theres some sort of gap
			addSequential(new WaitCommand(.1));
			
			switch(start) {
				case 5:
					addSequential(new DriveDistance(30, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 3));
					break;
				case 4:
					addSequential(new RotateUsingGyro(-50));
					//addSequential(new DriveDistance(24, 1, RobotMap.DriveTrain.MIN_STRAIGHT_POWER, 2));
					break;
				case 3:
					break;
				case 2:
					break;
				case 1:
					addSequential(new RotateUsingGyro(40));
					break;
			}
			
			switch(shoot) {
				case 0:
					addParallel(new VisionRotateToTarget());
					addParallel(new SetShooterSpeed(69));
					addSequential(new WaitCommand(4));
					addSequential(new SetCollectorPower(.6, true));
					addSequential(new WaitCommand(.5));
					addSequential(new SetCollectorPower(0, true));
					addSequential(new SetShooterPower());
					break;
				case 1:
					break;
				case 2:
					break;
			}
			
			/*
			}
			//This group of if statements determines what to do after crossing a defense
			if(start == 5) {
				addSequential(new DriveDistance(43));
				addSequential(new RotateUsingGyro(60));
			} else if(start == 4 && goal == Goal.LEFT) {
				addSequential(new RotateUsingGyro(-49.29));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(66.46));
				addSequential(new RotateToPreviousAngle(109.29));
			} else if(start == 4 && goal == Goal.MIDDLE) {
				addSequential(new RotateUsingGyro(90));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(88.94));
				addSequential(new RotateToPreviousAngle(-90));
			} else if(start == 3) {
				//addSequential(new RotateUsingGyro(15));
				//addSequential(new WaitCommand(.1));
				//addSequential(new DriveDistance(24));
				//addSequential(new Debug("AUTON STATUS", "DROVE 12 INCH"));
			} else if(start == 2 && goal == Goal.MIDDLE) {
				// You are basically close enough
			} else if(start == 2 && goal == Goal.RIGHT) {
				addSequential(new RotateUsingGyro(55.68));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(112.81));
				addSequential(new RotateToPreviousAngle(-115.68));
			} else if(start == 1 && goal == Goal.MIDDLE) {
				addSequential(new RotateUsingGyro(-90));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(66.82));
				addSequential(new RotateToPreviousAngle(90));
			} else if(start == 1 && goal == Goal.RIGHT) {
				addSequential(new DriveDistance(70));
				addSequential(new RotateUsingGyro(-30));
				//addSequential(VisionRotateToTarget.getInstance());
			} else {
				addSequential(new WaitCommand(.1));
			}
	*/
/*			
			//This group of if statements determines what to do after positioning toward the enemy castle
			if(shoot == 0) {
				addParallel(new VisionRotateToTarget());
				addParallel(new SetShooterSpeed(69));
				addSequential(new WaitCommand(4));
				addSequential(new SetCollectorPower(.6, true));
				addSequential(new WaitCommand(.5));
				addSequential(new SetCollectorPower(0, true));
				addSequential(new SetShooterPower());
				addSequential(new WaitCommand(.1));
			} else if(shoot == 1) {
				addSequential(VisionRotateToTarget.getInstance());// Should be targeted before moving -so Sequential instead of Parallel
				if(goal == Goal.LEFT) {
					addSequential(new SaveCurrentAngle());
					addSequential(new DriveDistance(140));
					addSequential(new RotateToPreviousAngle(180));
				} else if(goal == Goal.RIGHT) {
					addSequential(new SaveCurrentAngle());
					addSequential(new DriveDistance(100));
					addSequential(new RotateToPreviousAngle(180));
				} else {
					addSequential(new WaitCommand(1));
				}
				
				if(goal == Goal.LEFT || goal == Goal.RIGHT) {
					addSequential(new SetCollectorPower(-.8));
					addSequential(new WaitCommand(.25));
					addSequential(new SetCollectorPower());
					addSequential(new DriveDistance(12));
				}
			} else {
				addSequential(new WaitCommand(0));
			}
			
			
			//This group of if statements determines what to do after firing or not.
			if(shoot == 2){
				shoot = 0;
			}//shooting at the high goal and nothing have the same position on the field
			
			if(goal == Goal.MIDDLE || goal == Goal.DO_NOTHING){
				if(post != 0){//turns to defenses
					addSequential(new RotateUsingGyro(-180));
					if(post == 2 && goal != Goal.DO_NOTHING) {//was pretty much already at defenses
						addSequential(new DriveDistance(8));
					}
				}
			}else if(goal == Goal.LEFT) {
				if(post == 1){//angle to defenses
					if(shoot == 0){
						addSequential(new RotateUsingGyro(120));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(-60));
					}
				} else if(post == 2) {//back to defenses
					if(shoot == 0) {
						addSequential(new RotateUsingGyro(120));
						addSequential(new DriveDistance(43));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(-60));
						addSequential(new DriveDistance(120));
					}
				}
			} else {//Goal.RIGHT
				if(post == 1){//angle to defenses
					if(shoot == 0){//in secretPassage
						addSequential(new RotateUsingGyro(-90));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(60));
					}
				} else if(post == 2) {//back to defenses
					if(shoot == 0) {
						addSequential(new RotateUsingGyro(-90));
						addSequential(new DriveDistance(60));
						addSequential(new RotateUsingGyro(-30));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(60));
						addSequential(new DriveDistance(120));
					}
				}
			}
		}*/
		}
	}
	
	public void updateSmartDashboard() {
			SmartDashboard.putBoolean("Valid Auton", isValid());
	}
}
