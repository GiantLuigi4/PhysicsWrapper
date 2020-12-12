package com.tfc.physics.wrapper.common.API.joint;

import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;

import java.util.Objects;

public class Joint {
	public final ICollider first;
	public final ICollider second;
	public final JointDescriptor descriptor;
	
	public Joint(ICollider first, ICollider second) {
		this.first = first;
		this.second = second;
		descriptor = new JointDescriptor(JointType.DISTANCE);
	}
	
	public Joint(ICollider first, ICollider second, JointDescriptor descriptor) {
		this.first = first;
		this.second = second;
		this.descriptor = descriptor;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Joint joint = (Joint) o;
		return Objects.equals(first, joint.first) &&
				Objects.equals(second, joint.second) &&
				Objects.equals(descriptor, joint.descriptor);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(first, second, descriptor);
	}
}
