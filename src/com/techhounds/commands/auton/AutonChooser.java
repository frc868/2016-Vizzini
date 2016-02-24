package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.angler.SetStateDown;
import com.techhounds.commands.angler.SetStateUp;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.drive.DriveDistance;
import com.techhounds.commands.gyro.RotateToPreviousAngle;
import com.techhounds.commands.gyro.RotateUsingGyro;
import com.techhounds.commands.gyro.SaveCurrentAngle;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.SetShooterSpeed;

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
	
	enum Defense {
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
	
	enum Goal {
		LEFT,
		MIDDLE,
		RIGHT,
		DO_NOTHING
	}
	
	private SendableChooser chooseStart;
	private SendableChooser chooseDefense;
	private SendableChooser chooseGoal;
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
	
	private Goal getGoal() {
		return ((Goal) chooseGoal.getSelected());		
	}
	
	private int getPost() {
		return ((Number) choosePost.getSelected()).intValue();
	}

	public void setupDashboard() {
		chooseStart = new SendableChooser();
		chooseStart.addDefault("Low Bar (5)", new Integer(5));
		chooseStart.addObject("Position 4", new Integer(4));
		chooseStart.addObject("Position 3", new Integer(3));
		chooseStart.addObject("Position 2", new Integer(2));
		chooseStart.addObject("Secret Passage (1)", new Integer(1));
		
		chooseDefense = new SendableChooser();
		chooseDefense.addObject("A: Portcullis", Defense.PORTCULLIS);
		chooseDefense.addObject("A: Cheval De Frise", Defense.CHEVAL_DE_FRISE);
		chooseDefense.addObject("B: Moat", Defense.MOAT);
		chooseDefense.addObject("B: Ramparts", Defense.RAMPARTS);
		chooseDefense.addObject("D: Rock Wall", Defense.ROCK_WALL);
		chooseDefense.addObject("D: Rough Terrain", Defense.ROUGH_TERRAIN);
		chooseDefense.addObject("Low Bar", Defense.LOW_BAR);
		chooseDefense.addObject("Reach Defense", Defense.REACH_DEFENSE);
		chooseDefense.addDefault("Do Nothing", Defense.DO_NOTHING);
		
		chooseGoal = new SendableChooser();
		chooseGoal.addDefault("Left Goal", Goal.LEFT);
		chooseGoal.addObject("Middle Goal", Goal.MIDDLE);
		chooseGoal.addObject("Right Goal", Goal.RIGHT);
		chooseGoal.addObject("Do Nothing", Goal.DO_NOTHING);
		
		chooseShoot = new SendableChooser();
		chooseShoot.addObject("High Goal", new Integer(0));
		chooseShoot.addObject("Low Goal", new Integer(1));
		chooseShoot.addDefault("Do Nothing", new Integer(2));
		
		choosePost = new SendableChooser();
		choosePost.addObject("Get into Position", new Integer(2));
		choosePost.addObject("Angle toward defenses", new Integer(1));
		choosePost.addObject("Do Nothing", new Integer(0));


		SmartDashboard.putData("ChooseStart", chooseStart);
		SmartDashboard.putData("ChooseDefense", chooseDefense);
		SmartDashboard.putData("ChooseGoal", chooseGoal);
		SmartDashboard.putData("ChooseShoot", chooseShoot);
		SmartDashboard.putData("ChoosePost", choosePost);
	}
	
	public boolean isValid(){
		return true;
		/*int start = getStart();
		Defense defense = getDefense();
		Goal goal = getGoal();
		int shoot = getShoot();
		int post = getPost();
		
		if(start == 5) {
			if(defense == Defense.LOW_BAR) {
				if(goal == Goal.LEFT) {
					return true;
				} else {
					return false;
				}
			} else if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
				else
					return false;
			} else {
				return false;
			}
		} else if(start == 4) {
			if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
				else 
					return false;
			} else if(defense != null && defense != Defense.LOW_BAR) {
				if(goal == Goal.LEFT) {
					return true;
				} else if(goal == Goal.MIDDLE) {
					if(shoot == 1) {
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if(start == 3 || start == 2 || start == 1) {
			if(defense == Defense.REACH_DEFENSE || defense == Defense.DO_NOTHING) {
				if(goal == Goal.DO_NOTHING && shoot == 2 && post == 0)
					return true;
				else 
					return false;
			} else if(defense != null && defense != Defense.LOW_BAR) {
				if(goal == Goal.MIDDLE) {
					if(shoot == 1) {
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}*/
	}
	
	public Command createAutonCommand() {
		SmartDashboard.putBoolean("Valid Auton", isValid());
		if(isValid()) {
			System.out.print("-- VALID AUTON --");
			return new AutonCommand();
		} else {
			// TODO: If Invalid Auton, just reach the defense so we get points
			return new DriveDistance(RobotMap.Defenses.DEFENSE_DISTANCE, RobotMap.Defenses.TO_DEFENSE_SPEED);
		}
	}
	
	private class AutonCommand extends CommandGroup {
		
		public AutonCommand() {
			
			int start = getStart();
			Defense defense = getDefense();
			Goal goal = getGoal();
			int shoot = getShoot();
			int post = getPost();
			
			switch(defense) {//first sequence, crosses the designated defense
				case LOW_BAR:
					addParallel(new SetAnglerPosition(RobotMap.Collector.COLLECTING));
					addParallel(new CrossDefense(RobotMap.Defenses.LOW_BAR_DISTANCE, RobotMap.Defenses.LOW_BAR_SPEED));
					break;
				case MOAT:
					addSequential(new CrossDefense(RobotMap.Defenses.MOAT_DISTANCE, RobotMap.Defenses.MOAT_SPEED));
					break;
				case RAMPARTS:
					addSequential(new CrossDefense(RobotMap.Defenses.RAMPARTS_DISTANCE, RobotMap.Defenses.RAMPARTS_SPEED));
					break;
				case ROCK_WALL:
					addSequential(new CrossDefense(RobotMap.Defenses.ROCK_WALL_DISTANCE, RobotMap.Defenses.ROCK_WALL_SPEED));
					break;
				case ROUGH_TERRAIN:
					addSequential(new CrossDefense(RobotMap.Defenses.ROUGH_TERRAIN_DISTANCE, RobotMap.Defenses.ROUGH_TERRAIN_SPEED));
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
					return; // If only Reaching Defense and Do Nothing
			}
			
			// We have only made it here if not Reaching Defense or Do Nothing
			addSequential(new WaitCommand(.1));
			
			//This group of if statements determines what to do after crossing a defense
			if(goal == Goal.DO_NOTHING) {
				addSequential(new WaitCommand(0));
			} else if(start == 5 && goal == Goal.LEFT) {
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
				addSequential(new RotateUsingGyro(15));
				addSequential(new DriveDistance(12));
			} else if(start == 2 && goal == Goal.MIDDLE) {
				// You are basically close enough
			} else if(start == 2 && goal == Goal.RIGHT) {
				addSequential(new RotateUsingGyro(55.68));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(112.81));
				addSequential(new RotateToPreviousAngle(115.68));
			} else if(start == 1 && goal == Goal.MIDDLE) {
				addSequential(new RotateUsingGyro(-90));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(66.82));
				addSequential(new RotateToPreviousAngle(90));
			} else if(start == 1 && goal == Goal.RIGHT) {
				addSequential(new RotateUsingGyro(33.93));
				addSequential(new SaveCurrentAngle());
				addSequential(new DriveDistance(76.66));
				addSequential(new RotateToPreviousAngle(-93.93));
			}
	
			
			//This group of if statements determines what to do after positioning toward the enemy castle
			if(shoot == 0) {
				addParallel(new VisionRotateToTarget());// Get ourselves ready to target
				addParallel(new SetShooterSpeed(69));
				addSequential(new WaitCommand(.3));
				addSequential(new Fire());
				addSequential(new WaitCommand(.2));
			} else if(shoot == 1) {
				addSequential(new VisionRotateToTarget());// Should be targeted before moving -so Sequential instead of Parallel
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
					addSequential(new RotateUsingGyro(120));
				} else if(post == 2) {//back to defenses
					if(shoot == 0) {
						addSequential(new RotateUsingGyro(120));
						addSequential(new DriveDistance(43));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(120));
						addSequential(new DriveDistance(120));
					}
				}
			} else {//Goal.RIGHT
				if(post == 1){//angle to defenses
					if(shoot == 0){//in secretPassage
						addSequential(new RotateUsingGyro(-90));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(-120));
					}
				} else if(post == 2) {//back to defenses
					if(shoot == 0) {
						addSequential(new RotateUsingGyro(-90));
						addSequential(new DriveDistance(60));
						addSequential(new RotateUsingGyro(-30));
					} else {//at centerGoal
						addSequential(new RotateUsingGyro(-120));
						addSequential(new DriveDistance(120));
					}
				}
			}
		}
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Valid Auton", isValid());
	}
}
