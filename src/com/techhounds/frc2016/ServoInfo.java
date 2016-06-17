package com.techhounds.frc2016;

import edu.wpi.first.wpilibj.Servo;

public class ServoInfo {

	public double min, max;
	public Servo servo;
	
	public ServoInfo(Servo servo) {
		this(servo, 0, 1);
	}
	
	public ServoInfo(Servo servo, double min, double max) {
		this.servo = servo;
		this.min = min;
		this.max = max;
	}
}
