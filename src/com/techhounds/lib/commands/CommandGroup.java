package com.techhounds.lib.commands;

import java.util.ArrayList;

import com.techhounds.commands.Debugger;

import edu.wpi.first.wpilibj.command.Command;

public class CommandGroup extends edu.wpi.first.wpilibj.command.CommandGroup {
	
//	private ArrayList<Command> commands = new ArrayList<>();
//	private ArrayList<CommandType> commandType = new ArrayList<>();

	private boolean debugger;
	
//	public enum CommandType {
//		SEQUENTIAL, PARALLEL
//	}
	
	public CommandGroup() {
		this(false);
	}
	
	public CommandGroup(boolean debugger) {
		super();
		this.debugger = debugger;
	}
	
	public void addSequentialCommand(Command command) {
		super.addSequential(command);

		if(debugger)
			super.addSequential(new Debugger());
		
//		commands.add(command);
//		commandType.add(CommandType.SEQUENTIAL);
	}

	public void addSequentialCommand(Command command, double timeOut) {
		super.addSequential(command, timeOut);
		
		if(debugger)
			super.addSequential(new Debugger());
		
//		commands.add(command);
//		commandType.add(CommandType.SEQUENTIAL);
	}
	
	public void addParallelCommand(Command command) {
		super.addParallel(command);
//		commands.add(command);
//		commandType.add(CommandType.PARALLEL);
	}
	
	public void addParallelCommand(Command command, double timeOut) {
		super.addParallel(command, timeOut);
//		commands.add(command);
//		commandType.add(CommandType.PARALLEL);
	}
}
