package com.tfc.physics.wrapper.common.backend.interfaces;

import com.tfc.physics.wrapper.common.backend.PositionSetter;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.Vec2Wrapper;
import com.tfc.physics.wrapper.common.joint.Joint;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ICollider {
	double getDefaultX();
	double getDefaultY();
	double getDefaultAngle();
	
	void draw(Graphics2D g2d);
	
	void setX(double x);
	void setY(double y);
	
	void setPositionSupplier(Supplier<Vector2> supplier);
	Supplier<Vector2> getPositionSupplier();
	
	void setPositionSetter(PositionSetter setter);
	PositionSetter getPositionSetter();
	
	ICollider setImmovable();
	ICollider setImmovable(boolean immovable);
	boolean isImmovable();
	
	void setVelocityVal(Vec2Wrapper wrapper);
	
	Vector2 getVelocity(double x, double y);
	void setVelocity(double x, double y);
	void addVelocity(double x, double y);
	
	void setAngleSetter(Consumer<Double> angleSetter);
	Consumer<Double> getAngleSetter();
	
	void setAngleGetter(Supplier<Double> angleGetter);
	Supplier<Double> getAngleGetter();
	
	void setAngularForceApplicator(Consumer<Double> applicator);
	Consumer<Double> getAngularForceApplicator();
	void addAngularForce(double amt);
	
	double getAngle();
	void setAngle(double angle);
	
	default float getDensity() {
		return 1f;
	}
	
	default float getLinearDamping() {
		return 0.1f;
	}
	
	default float getFriction() {
		return 0.25f;
	}
	
	default ArrayList<Vector2> setShape() {
		return new ArrayList<>();
	}
	
	void setGravityScale(double i);
	void setGravityScaleSetter(Consumer<Double> setter);
	
	default Joint createJoint(ICollider other) {
		return new Joint(this,other);
	}
}
