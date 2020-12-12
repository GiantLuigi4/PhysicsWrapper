package com.tfc.physics.wrapper.common.backend.interfaces;

import com.tfc.physics.wrapper.common.API.joint.Joint;
import com.tfc.physics.wrapper.common.API.listeners.CollisionListener;

import java.util.Collection;

public interface IPhysicsWorld {
	void addCollider(ICollider collider);
	void removeCollider(ICollider collider);
	Collection<ICollider> getColliders();
	
	void addJoint(Joint joint);
	void destroyJoint(Joint joint);
	Collection<Joint> getJoints();
	
	void tick();
	
	CollisionListener addCollisionListener(CollisionListener listener);
	void removeCollisionListener(CollisionListener listener);
	
	//Idk if this is the actual thing that "dt" refers to in Box2D
	void setDeltaTime(int dt);
	void setVelocityIterations(int vi);
	void setPositionIterations(int pi);
	
	void useContinuousPhysics(boolean continuousPhysics);
}
