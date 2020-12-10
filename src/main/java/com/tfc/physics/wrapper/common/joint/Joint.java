package com.tfc.physics.wrapper.common.joint;

import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;

import java.util.Objects;

public class Joint {
	public final ICollider first;
	public final ICollider second;
	public float dampening = -1;
	public float length = 20;
	
	public Joint(ICollider first, ICollider second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Joint joint = (Joint) o;
		return Float.compare(joint.dampening, dampening) == 0 &&
				Objects.equals(first, joint.first) &&
				Objects.equals(second, joint.second);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(first, second, dampening);
	}
}
