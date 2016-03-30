package com.techhounds.commands.gyro;

public class RotateToLastAngle extends RotateUsingGyro {

	public RotateToLastAngle(double angle) {
		super(angle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize() {
		angle = gyro.getStoredAngle() - gyro.getRotation() + angle;
		super.initialize();
	}

}
