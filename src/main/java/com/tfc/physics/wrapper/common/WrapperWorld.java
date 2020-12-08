package com.tfc.physics.wrapper.common;

import com.tfc.physics.wrapper.Physics;
import com.tfc.physics.wrapper.box2d.B2DWorld;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.backend.interfaces.IPhysicsWorld;
import org.jbox2d.common.Vec2;

import java.util.Collection;

public class WrapperWorld implements IPhysicsWorld {
	private IPhysicsWorld world = null;
	
	public WrapperWorld(Vector2 gravity) {
		if (Physics.isIsBox2DUsed()) {
			this.world = new B2DWorld(new Vec2(gravity.x,gravity.y));
		}
	}
	
	@Override
	public void addCollider(ICollider collider) {
		world.addCollider(collider);
	}
	
	public Collection<ICollider> getColliders() {
		return world.getColliders();
	}
	
	@Override
	public void tick() {
		world.tick();
	}
}
