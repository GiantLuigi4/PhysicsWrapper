package com.tfc.physics.wrapper.common.backend.collision;

import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;

public class Collision {
	public final Manifold manifold;
	
	public final ICollider bodyA;
	public final ICollider bodyB;
	
	public Collision(Manifold manifold, ICollider bodyA, ICollider bodyB) {
		this.manifold = manifold;
		this.bodyA = bodyA;
		this.bodyB = bodyB;
	}
}
