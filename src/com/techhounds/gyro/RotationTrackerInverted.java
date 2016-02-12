/**
 * Copyright (c) 2015, www.techhounds.com
 * All rights reserved.
 *
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * </p>
 * <ul>
 * <li>Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.</li>
 * <li>Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.</li>
 * <li>Neither the name of the www.techhounds.com nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.</li>
 * </ul>
 *
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * </p>
 */

package com.techhounds.gyro;

import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A class to "software correct" a gyro that is mounted upside down.
 * 
 * <p>
 * If your gyro has been mounted to the robot incorrectly such that one of the
 * axis is flipped, this class can correct for the problem (a software fix if it
 * is too hard to physically place the gyro in the correct position).
 * </p>
 *
 */
public final class RotationTrackerInverted implements RotationTracker {

	/**
	 * The RotationTracker providing angle information that needs to be
	 * inverted.
	 */
	private RotationTracker m_rotationTracker;

	/**
	 * Constructs a new instance with a reference to the tracker that needs to
	 * be inverted.
	 * 
	 * @param rotationTracker
	 *            The RotationTracker providing angle information that needs to
	 *            be inverted (must be non-null).
	 */
	public RotationTrackerInverted(RotationTracker rotationTracker) {
		if (rotationTracker == null) {
			throw new NullPointerException();
		}
		m_rotationTracker = rotationTracker;
	}

	/**
	 * Method used by PID controllers.
	 * 
	 * @return The corrected signed angle in degrees (range of [-INF,+INF]).
	 */
	@Override
	public double pidGet() {
		return getAngle();
	}

	/**
	 * Get the corrected (inverted) angle of rotation since last zeroed.
	 * 
	 * @return The number of degrees the robot has rotated (range of
	 *         [-INF,+INF]).
	 */
	@Override
	public double getAngle() {
		return -m_rotationTracker.getAngle();
	}

	/**
	 * Resets the rotation tracker so that the current direction we are facing
	 * is now 0.0 degrees.
	 */
	@Override
	public void zero() {
		m_rotationTracker.zero();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

}
