package com.tfc.physics.wrapper.common.backend.interfaces;

import com.tfc.physics.wrapper.common.joint.Joint;

import java.util.Collection;

public interface IPhysicsWorld {
	void addCollider(ICollider collider);
	Collection<ICollider> getColliders();
	void tick();
	
	void addJoint(Joint joint);
}
