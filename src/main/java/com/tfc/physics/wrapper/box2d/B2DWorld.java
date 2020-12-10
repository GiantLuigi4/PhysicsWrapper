package com.tfc.physics.wrapper.box2d;

import com.tfc.physics.wrapper.common.backend.PositionSetter;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.Vec2Wrapper;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.colliders.BoxCollider;
import com.tfc.physics.wrapper.common.backend.interfaces.IPhysicsWorld;
import com.tfc.physics.wrapper.common.colliders.CircleCollider;
import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class B2DWorld extends World implements IPhysicsWorld {
	public HashMap<ICollider, Body> colliders = new HashMap<>();
	public HashMap<com.tfc.physics.wrapper.common.joint.Joint, Joint> joints = new HashMap<>();
	
	public B2DWorld(Vec2 gravity) {
		super(gravity);
		this.setWarmStarting(false);
		this.setSubStepping(false);
		this.setContinuousPhysics(false);
		setAllowSleep(true);
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
					fd.density = (isFirstDef?collider.getDensity():0.5f);
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
				(newX) -> body.setTransform(new Vec2(newX.floatValue(),body.getPosition().y),body.getAngle()),
				(newY) -> body.setTransform(new Vec2(body.getPosition().x, newY.floatValue()),body.getAngle())
		));
		collider.setVelocityVal(new Vec2Wrapper(
				() -> new Vector2(body.getLinearVelocity().x,body.getLinearVelocity().y),
				(newX) -> body.setLinearVelocity(new Vec2(newX.floatValue(), body.getLinearVelocity().y)),
				(newY) -> body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, newY.floatValue()))
		));
		collider.setAngularForceApplicator((amt) -> {
			body.applyAngularImpulse(amt.floatValue());
		});
		
		collider.setAngleGetter(() -> (double) body.getAngle());
		collider.setAngleSetter((val) -> body.setTransform(body.getWorldCenter(), val.floatValue()));
		
		collider.setGravityScaleSetter((value)->body.setGravityScale(value.floatValue()));
		
		colliders.put(collider, body);
	}
	
	@Override
	public Collection<ICollider> getColliders() {
		return colliders.keySet();
	}
	
	@Override
	public void tick() {
		step(1, 1, 8);
	}
	
	@Override
	public void addJoint(com.tfc.physics.wrapper.common.joint.Joint joint) {
		DistanceJointDef def = new DistanceJointDef();
		def.bodyA = colliders.get(joint.first);
		def.bodyB = colliders.get(joint.second);
		def.dampingRatio = joint.dampening;
		def.length = joint.length;
		def.frequencyHz = 10.0f;
		
		joints.put(joint,super.createJoint(def));
	}
}
