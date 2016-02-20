package com.techhounds.commands.auton;

import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.gyro.SetGyro;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.SetShooterPower;

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
		choosePost.addObject("Get into Position", new Integer(1));
		choosePost.addObject("Do Nothing", new Integer(0));


		SmartDashboard.putData("ChooseStart", chooseStart);
		SmartDashboard.putData("ChooseDefense", chooseDefense);
		SmartDashboard.putData("ChooseGoal", chooseGoal);
		SmartDashboard.putData("ChooseShoot", chooseShoot);
		SmartDashboard.putData("ChoosePost", choosePost);
	}
	
	public boolean isValid(){
		int start = getStart();
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
		}
	}
	
	public Command createAutonCommand() {
		int start = getStart();
		Defense defense = getDefense();
		Goal goal = getGoal();
		int shoot = getShoot();
		int post = getPost();
		
		if(!isValid()) {
			System.out.print("-- INVALID AUTON --");
			return new AutonCommand();
		} else {
			// TODO: If Invalid Auton, just reach the defense so we get points
			return new DriveDistance(2);
		}
	}
	
	private class AutonCommand extends CommandGroup {
		
		public AutonCommand() {
			
			int start = getStart();
			Defense defense = getDefense();
			Goal goal = getGoal();
			int shoot = getShoot();
			int post = getPost();
			
//			addSequential(new ResetGyro());
			
			if(defense == Defense.LOW_BAR) {
				// TODO: Drive moderately fast
			} else if(defense == Defense.MOAT || defense == Defense.RAMPARTS) {
				// TODO: Drive as fast as possible
			} else if(defense == Defense.ROCK_WALL || defense == Defense.ROUGH_TERRAIN) {
				// TODO: Drive moderately fast
			}else if(defense == Defense.PORTCULLIS) {
				// TODO: Drive moderately fast, lift collector and drive through
			} else if(defense == Defense.CHEVAL_DE_FRISE) {
				// TODO: Drive moderately fast, stop, collector down and drive slightly fast
			} else if(defense == Defense.REACH_DEFENSE) {
				addSequential(new DriveDistance(2, .5));
			} else {
				addSequential(new WaitCommand(0));
			} 
			
			if(defense != Defense.DO_NOTHING && defense != Defense.REACH_DEFENSE) {
				addSequential(new SetGyro(0));
			}
			
			if(goal == Goal.DO_NOTHING) {
				// TODO: Drive 1 foot
				addSequential(new DriveDistance(1));
			} else if(goal == Goal.LEFT) {
				addSequential(new SetGyro(5));
				addSequential(new DriveDistance(2));
				// TODO: Angle slightly then drive a distance
			} else if(start == 4 & goal == Goal.LEFT) {
				addSequential(new SetGyro(-5));
				addSequential(new DriveDistance(2));
				// TODO: Angle slightly then drive a distance
			} else if(start == 4 && goal == Goal.MIDDLE) {
				addSequential(new SetGyro(5));
				addSequential(new DriveDistance(.5));
				// TODO: Angle slightly then drive a very short distance
			} else if(start == 3) {
				addSequential(new SetGyro(2));
				addSequential(new DriveDistance(1));
				// TODO: Angle slightly the drive a short distance
			} else if(start == 2) {
				addSequential(new SetGyro(-1));
				addSequential(new DriveDistance(1));
				// TODO: Angle slightly then drive a short distance
			} else if(start == 1) {
				addSequential(new SetGyro(-5));
				addSequential(new DriveDistance(2));
				// TODO: Angle more than slightly then drive a short distance
			}
			
			if(goal != Goal.DO_NOTHING) {
				//addSequential(new VisionAim());
			}
			
			if(shoot == 0) {
				addSequential(new VisionAlignToTarget());
				addSequential(new VisionSetShooterPower());
				addSequential(new Fire());
				// TODO: Get distance and shoot
			} else if(shoot == 1) {
				addSequential(new VisionDriveDistance());
				// TODO: Determine distance and drive it and feed out
			} else if(shoot == 2) {
				addSequential(new WaitCommand(0));
			} else {
				addSequential(new WaitCommand(0));
			}
			
			if(post == 1) {
				// TODO: Aim our robot towards the defense! (Use Gyro 180 or 0)
			} else if(post == 0) {
				addSequential(new WaitCommand(0));
			} else {
				addSequential(new WaitCommand(0));
			}
		}
	}

	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Valid Auton", isValid());
	}
}
