package com.tfc.physics.wrapper.common.joint;

public enum JointType {
	DISTANCE("distance"),
	PRISMATIC("prismatic"),
	WHEEL("wheel"),
	;
	
	String name;
	
	JointType(String name) {
		this.name = name;
	}
}
