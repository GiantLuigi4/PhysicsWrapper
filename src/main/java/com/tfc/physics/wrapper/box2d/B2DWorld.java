package com.tfc.physics.wrapper.box2d;

import com.tfc.physics.wrapper.common.backend.PositionSetter;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.Vec2Wrapper;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.colliders.BoxCollider;
import com.tfc.physics.wrapper.common.backend.interfaces.IPhysicsWorld;
import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import java.util.Collection;
import java.util.HashMap;

public class B2DWorld extends World implements IPhysicsWorld {
	public HashMap<ICollider, Body> colliders = new HashMap<>();
	
	public B2DWorld(Vec2 gravity) {
		super(gravity);
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
		
		if (collider instanceof BoxCollider)
			shape.setAsBox((float) ((BoxCollider) collider).width, (float) ((BoxCollider) collider).height);
		
		BodyDef bodyDef = new BodyDef();
		
		if (collider.isImmovable()) bodyDef.type = BodyType.STATIC;
		else bodyDef.type = BodyType.DYNAMIC;
		
		bodyDef.position.set((float) collider.getDefaultX(), (float) collider.getDefaultY());
		bodyDef.angle = (float) collider.getDefaultAngle();
		
		bodyDef.gravityScale = collider.isImmovable() ? 0 : 1;
		bodyDef.linearDamping = collider.getLinearDamping();
		
		Body body = this.createBody(bodyDef);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = collider.getDensity();
		fd.friction = collider.getFriction();
		body.createFixture(fd);
		
		collider.setPositionSupplier(() -> new Vector2(body.getPosition().x, body.getPosition().y));
		collider.setPositionSetter(new PositionSetter(
				(newX) -> body.getPosition().set(newX.floatValue(), body.getPosition().y),
				(newY) -> body.getPosition().set(body.getPosition().x, newY.floatValue())
		));
		collider.setVelocityVal(new Vec2Wrapper(
				() -> collider.getPositionSupplier().get(),
				(newX) -> body.setLinearVelocity(new Vec2(newX.floatValue(), body.getLinearVelocity().y)),
				(newY) -> body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, newY.floatValue()))
		));
		collider.setAngularForceApplicator((amt) -> {
			body.applyAngularImpulse(amt.floatValue());
		});
		
		collider.setAngleGetter(() -> (double) body.getAngle());
		collider.setAngleSetter((val) -> body.setTransform(body.getWorldCenter(), val.floatValue()));
		
		colliders.put(collider, body);
	}
	
	@Override
	public Collection<ICollider> getColliders() {
		return colliders.keySet();
	}
	
	@Override
	public void tick() {
		step(60, 3, 8);
	}
}
