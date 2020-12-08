package com.tfc.physics.wrapper.common.backend.interfaces;

import java.util.Collection;

public interface IPhysicsWorld {
	void addCollider(ICollider collider);
	Collection<ICollider> getColliders();
	void tick();
}
