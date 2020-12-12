package com.tfc.physics.wrapper.box2d;

import com.tfc.physics.wrapper.box2d.backend.Vector2Creator;
import com.tfc.physics.wrapper.common.backend.PositionSetter;
import com.tfc.physics.wrapper.common.API.Vector2;
import com.tfc.physics.wrapper.common.backend.Vec2Wrapper;
import com.tfc.physics.wrapper.common.backend.collision.Collision;
import com.tfc.physics.wrapper.common.backend.collision.CollisionPreSolve;
import com.tfc.physics.wrapper.common.backend.collision.ContactEdge;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.API.colliders.BoxCollider;
import com.tfc.physics.wrapper.common.backend.interfaces.IPhysicsWorld;
import com.tfc.physics.wrapper.common.API.colliders.CircleCollider;
import com.tfc.physics.wrapper.common.API.listeners.CollisionListener;
import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class B2DWorld extends World implements IPhysicsWorld {
	public HashMap<ICollider, Body> colliders = new HashMap<>();
	public HashMap<com.tfc.physics.wrapper.common.API.joint.Joint, Joint> joints = new HashMap<>();
	
	public ArrayList<CollisionListener> listeners = new ArrayList<>();
	
	public B2DWorld(Vec2 gravity) {
		super(gravity);
		this.setWarmStarting(false);
		this.setSubStepping(false);
		this.setContinuousPhysics(false);
		setAllowSleep(true);
		
		setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				listeners.forEach((listener)->{
					ICollider colliderA = getColliderFromBody(contact.m_nodeB.other);
					ICollider colliderB = getColliderFromBody(contact.m_nodeA.other);
					listener.beginContact(
							new Collision(
									new com.tfc.physics.wrapper.common.backend.collision.Manifold(
											new ContactEdge(colliderB),
											new ContactEdge(colliderA),
											contact.getManifold().pointCount,
											Vector2Creator.create(contact.getManifold().localNormal),
											Vector2Creator.create(contact.getManifold().localPoint)
									),
									colliderA,
									colliderB
							)
					);
				});
			}
			
			@Override
			public void endContact(Contact contact) {
				listeners.forEach((listener)->{
					ICollider colliderA = getColliderFromBody(contact.m_nodeB.other);
					ICollider colliderB = getColliderFromBody(contact.m_nodeA.other);
					listener.finishContact(
							new Collision(
									new com.tfc.physics.wrapper.common.backend.collision.Manifold(
											new ContactEdge(colliderB),
											new ContactEdge(colliderA),
											contact.getManifold().pointCount,
											Vector2Creator.create(contact.getManifold().localNormal),
											Vector2Creator.create(contact.getManifold().localPoint)
									),
									colliderA,
									colliderB
							)
					);
				});
			}
			
			@Override
			public void preSolve(Contact contact, org.jbox2d.collision.Manifold oldManifold) {
				listeners.forEach((listener)->{
					ICollider colliderA = getColliderFromBody(contact.m_nodeB.other);
					ICollider colliderB = getColliderFromBody(contact.m_nodeA.other);
					listener.preSolve(
							new CollisionPreSolve(
									new com.tfc.physics.wrapper.common.backend.collision.Manifold(
											new ContactEdge(colliderB),
											new ContactEdge(colliderA),
											contact.getManifold().pointCount,
											Vector2Creator.create(contact.getManifold().localNormal),
											Vector2Creator.create(contact.getManifold().localPoint)
									),
									colliderA,
									colliderB,
									new com.tfc.physics.wrapper.common.backend.collision.Manifold(
											new ContactEdge(colliderB),
											new ContactEdge(colliderA),
											oldManifold.pointCount,
											Vector2Creator.create(oldManifold.localNormal),
											Vector2Creator.create(oldManifold.localPoint)
									)
							)
					);
				});
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				listeners.forEach((listener)->{
					ICollider colliderA = getColliderFromBody(contact.m_nodeB.other);
					ICollider colliderB = getColliderFromBody(contact.m_nodeA.other);
					listener.postSolve(
							new Collision(
									new com.tfc.physics.wrapper.common.backend.collision.Manifold(
											new ContactEdge(colliderB),
											new ContactEdge(colliderA),
											contact.getManifold().pointCount,
											Vector2Creator.create(contact.getManifold().localNormal),
											Vector2Creator.create(contact.getManifold().localPoint)
									),
									colliderA,
									colliderB
							)
					);
				});
			}
		});
	}
	
	@Override
	public void setDestructionListener(DestructionListener listener) {
		super.setDestructionListener(listener);
	}
	
	@Override
	public void setContactFilter(ContactFilter filter) {
		super.setContactFilter(filter);
	}
	
	@Override
	public void setContactListener(ContactListener listener) {
		super.setContactListener(listener);
	}
	
	@Override
	public Body createBody(BodyDef def) {
		return super.createBody(def);
	}
	
	@Override
	public void destroyBody(Body body) {
		super.destroyBody(body);
	}
	
	@Override
	public Joint createJoint(JointDef def) {
		return super.createJoint(def);
	}
	
	@Override
	public void step(float dt, int velocityIterations, int positionIterations) {
		super.step(dt, velocityIterations, positionIterations);
	}
	
	@Override
	public void addCollider(ICollider collider) {
		PolygonShape shape = new PolygonShape();
		
		BodyDef bodyDef = new BodyDef();
		
		if (collider.isImmovable()) bodyDef.type = BodyType.STATIC;
		else bodyDef.type = BodyType.DYNAMIC;
		
		bodyDef.position.set((float) collider.getDefaultX(), (float) collider.getDefaultY());
		bodyDef.angle = (float) collider.getDefaultAngle();
		
		bodyDef.gravityScale = collider.isImmovable() ? 0 : 1;
		bodyDef.linearDamping = collider.getLinearDamping();
		
		Body body = this.createBody(bodyDef);
		FixtureDef fd = new FixtureDef();
		
		if (collider instanceof BoxCollider) {
			shape.setAsBox((float) ((BoxCollider) collider).width, (float) ((BoxCollider) collider).height);
			fd.shape = shape;
			fd.density = collider.getDensity();
			fd.friction = collider.getFriction();
			body.createFixture(fd);
		} else if (collider instanceof CircleCollider) {
			ArrayList<Vector2> vector2s = ((CircleCollider) collider).createShape();
			Vec2[] vertices = new Vec2[8];
			int amt = 0;
			boolean isFirstDef = true;
			for (int i = 0; i < vector2s.size(); i++) {
				Vector2 vec = vector2s.get(i);
				vertices[amt] = new Vec2(vec.x, vec.y);
				amt++;
				if (amt == 8) {
					shape.set(vertices, vertices.length);
					amt = 0;
					fd.shape = shape;
//					fd.density = (isFirstDef?collider.getDensity():0.5f);
					fd.density = (collider.getDensity());
					fd.friction = collider.getFriction();
					body.createFixture(fd);
					vertices = new Vec2[8];
					isFirstDef = false;
				}
			}
			
			if (amt != 0) {
				shape.set(vertices, amt);
				fd.shape = shape;
				fd.density = collider.getDensity();
				fd.friction = collider.getFriction();
				body.createFixture(fd);
			}
		} else {
			fd.shape = shape;
			fd.density = collider.getDensity();
			fd.friction = collider.getFriction();
			body.createFixture(fd);
		}
		
		collider.setPositionSupplier(() -> new Vector2(body.getPosition().x, body.getPosition().y));
		collider.setPositionSetter(new PositionSetter(
				(newX) -> body.setTransform(new Vec2(newX.floatValue(), body.getPosition().y), body.getAngle()),
				(newY) -> body.setTransform(new Vec2(body.getPosition().x, newY.floatValue()), body.getAngle())
		));
		collider.setVelocityVal(new Vec2Wrapper(
				() -> new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y),
				(newX) -> body.setLinearVelocity(new Vec2(newX.floatValue(), body.getLinearVelocity().y)),
				(newY) -> body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, newY.floatValue()))
		));
		collider.setAngularForceApplicator((amt) -> {
			body.applyAngularImpulse(amt.floatValue());
		});
		
		collider.setAngleGetter(() -> (double) body.getAngle());
		collider.setAngleSetter((val) -> body.setTransform(body.getWorldCenter(), val.floatValue()));
		
		collider.setGravityScaleSetter((value) -> body.setGravityScale(value.floatValue()));
		
		colliders.put(collider, body);
	}
	
	@Override
	public Collection<ICollider> getColliders() {
		return colliders.keySet();
	}
	
	int dt=1;
	int vi=1;
	int pi=8;
	
	@Override
	public void tick() {
		step(dt, vi, pi);
	}
	
	@Override
	public void addJoint(com.tfc.physics.wrapper.common.API.joint.Joint joint) {
		JointDef def;
		switch (joint.descriptor.type) {
			case DISTANCE:
				DistanceJointDef workingDefDist = new DistanceJointDef();
				workingDefDist.dampingRatio = joint.descriptor.dampening;
				workingDefDist.length = joint.descriptor.length;
				workingDefDist.frequencyHz = 10.0f;
				def = workingDefDist;
				break;
			case WHEEL:
				WheelJointDef workingDefWheel = new WheelJointDef();
				workingDefWheel.dampingRatio = joint.descriptor.dampening;
				workingDefWheel.enableMotor = joint.descriptor.motor.isOn;
				workingDefWheel.maxMotorTorque = joint.descriptor.motor.motorTorque;
				workingDefWheel.motorSpeed = joint.descriptor.motor.motorSpeed;
				workingDefWheel.frequencyHz = 10.0f;
				def = workingDefWheel;
				break;
			case PRISMATIC:
				PrismaticJointDef workingDefPrismatic = new PrismaticJointDef();
				workingDefPrismatic.enableMotor = joint.descriptor.motor.isOn;
				workingDefPrismatic.motorSpeed = joint.descriptor.motor.motorSpeed;
				workingDefPrismatic.maxMotorForce = joint.descriptor.motor.motorTorque;
				workingDefPrismatic.lowerTranslation = joint.descriptor.lowerTranslation;
				workingDefPrismatic.upperTranslation = joint.descriptor.upperTranslation;
				workingDefPrismatic.enableLimit = joint.descriptor.enableLimit;
			default:
				def = new JointDef();
				break;
		}
		
		def.bodyA = colliders.get(joint.first);
		def.bodyB = colliders.get(joint.second);
		joints.put(joint, super.createJoint(def));
	}
	
	@Override
	public void removeCollider(ICollider collider) {
		this.destroyBody(colliders.get(collider));
		colliders.remove(collider);
	}
	
	@Override
	public void destroyJoint(com.tfc.physics.wrapper.common.API.joint.Joint joint) {
		this.destroyJoint(joints.get(joint));
		joints.remove(joint);
	}
	
	@Override
	public Collection<com.tfc.physics.wrapper.common.API.joint.Joint> getJoints() {
		return joints.keySet();
	}
	
	@Override
	public CollisionListener addCollisionListener(CollisionListener listener) {
		listeners.add(listener);
		return listener;
	}
	
	@Override
	public void removeCollisionListener(CollisionListener listener) {
		listeners.remove(listener);
	}
	
	private ICollider getColliderFromBody(Body target) {
		Collection<Body> bodies = colliders.values();
		Iterator<ICollider> colliders = getColliders().iterator();
		for (Body body : bodies) {
			if (body.equals(target)) return colliders.next();
			colliders.next();
		}
		return null;
	}
	
	@Override
	public void setDeltaTime(int dt) {
		this.dt = dt;
	}
	
	@Override
	public void setVelocityIterations(int vi) {
		this.vi = vi;
	}
	
	@Override
	public void setPositionIterations(int pi) {
		this.pi = pi;
	}
	
	@Override
	public void useContinuousPhysics(boolean continuousPhysics) {
		super.setContinuousPhysics(continuousPhysics);
	}
	
	@Override
	public void allowSleep(boolean allowSleep) {
		super.setAllowSleep(allowSleep);
	}
}
