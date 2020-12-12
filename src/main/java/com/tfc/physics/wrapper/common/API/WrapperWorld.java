package com.tfc.physics.wrapper.common.API;

import com.tfc.physics.wrapper.Physics;
import com.tfc.physics.wrapper.box2d.B2DWorld;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.backend.interfaces.IPhysicsWorld;
import com.tfc.physics.wrapper.common.API.joint.Joint;
import com.tfc.physics.wrapper.common.API.listeners.CollisionListener;
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
	
	@Override
	public void addJoint(Joint joint) {
		world.addJoint(joint);
	}
	
	@Override
	public void removeCollider(ICollider collider) {
		world.removeCollider(collider);
	}
	
	@Override
	public void destroyJoint(Joint joint) {
		world.destroyJoint(joint);
	}
	
	@Override
	public Collection<Joint> getJoints() {
		return world.getJoints();
	}
	
	@Override
	public CollisionListener addCollisionListener(CollisionListener listener) {
		return world.addCollisionListener(listener);
	}
	
	@Override
	public void removeCollisionListener(CollisionListener listener) {
		world.removeCollisionListener(listener);
	}
	
	@Override
	public void setDeltaTime(int dt) {
		world.setDeltaTime(dt);
	}
	
	@Override
	public void setVelocityIterations(int vi) {
		world.setVelocityIterations(vi);
	}
	
	@Override
	public void setPositionIterations(int pi) {
		world.setPositionIterations(pi);
	}
}
