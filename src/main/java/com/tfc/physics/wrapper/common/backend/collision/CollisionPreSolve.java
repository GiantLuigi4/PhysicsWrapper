package com.tfc.physics.wrapper.common.backend.collision;

import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;

public class CollisionPreSolve extends Collision {
	public final Manifold oldManifold;
	
	public CollisionPreSolve(Manifold manifold, ICollider bodyA, ICollider bodyB, Manifold oldManifold) {
		super(manifold, bodyA, bodyB);
		this.oldManifold = oldManifold;
	}
}
