package com.techhounds.frc2016.commands.drive_legacy;

public class DriveUntilLightSensor extends DriveWithSpeed{

	private boolean sensorReading;
	public DriveUntilLightSensor(double speed, boolean waitForOnCarpet) {
		super(speed);
		sensorReading = waitForOnCarpet;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isFinished(){
		return sensorReading == drive.onDefense();
	}

}
