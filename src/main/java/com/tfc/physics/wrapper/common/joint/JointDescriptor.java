package com.tfc.physics.wrapper.common.joint;

//TODO: move all type specific fields out of this class and into their own classes
public class JointDescriptor {
	/**
	 * the type of the joint
	 */
	public final JointType type;

	/**
	 * only on distance and wheel joints
	 */
	public float dampening = -1;

	/**
	 * only on distance joints
	 */
	public float length = 20;
	
	/**
	 * only on prismatic joints
	 */
	public float lowerTranslation = -10;
	
	/**
	 * only on prismatic joints
	 */
	public float upperTranslation = 10;
	
	/**
	 * only on prismatic joints
	 */
	public boolean enableLimit = false;
	
	public final JointMotor motor = new JointMotor();
	
	public JointDescriptor(JointType type) {
		this.type = type;
	}
}
