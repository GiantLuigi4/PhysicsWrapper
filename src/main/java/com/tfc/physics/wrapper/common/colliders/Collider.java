package com.tfc.physics.wrapper.common.colliders;

import com.tfc.physics.wrapper.common.backend.PositionSetter;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.backend.Vec2Wrapper;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Collider implements ICollider {
	double defaultX = 0;
	double defaultY = 0;
	double defaultAngle = 0;
	
	@Override
	public double getDefaultX() {
		return defaultX;
	}
	
	@Override
	public double getDefaultY() {
		return defaultY;
	}
	
	@Override
	public double getDefaultAngle() {
		return defaultAngle;
	}
	
	@Override
	public abstract void draw(Graphics2D g2d);
	
	Supplier<Vector2> supplier;
	PositionSetter positionSetter;
	
	boolean isImmovable = false;
	
	public double getX() {
		return supplier.get().x;
	}
	
	public double getY() {
		return supplier.get().y;
	}
	
	@Override
	public void setX(double x) {
		supplier.get().x = (float) x;
	}
	
	@Override
	public void setY(double y) {
		supplier.get().y = (float) y;
	}
	
	@Override
	public void setPositionSupplier(Supplier<Vector2> supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public Supplier<Vector2> getPositionSupplier() {
		return supplier;
	}
	
	@Override
	public ICollider setImmovable() {
		isImmovable = true;
		return this;
	}
	
	@Override
	public ICollider setImmovable(boolean immovable) {
		isImmovable = immovable;
		return this;
	}
	
	@Override
	public boolean isImmovable() {
		return isImmovable;
	}
	
	public Collider move(int x, int y) {
		this.defaultX = x;
		this.defaultY = y;
		return this;
	}
	
	public Collider rotate(double angle) {
		this.defaultAngle = angle;
		return this;
	}
	
	@Override
	public void setPositionSetter(PositionSetter setter) {
		this.positionSetter = setter;
	}
	
	@Override
	public PositionSetter getPositionSetter() {
		return positionSetter;
	}
	
	Vec2Wrapper velocity;
	
	@Override
	public void setVelocityVal(Vec2Wrapper wrapper) {
		velocity = wrapper;
	}
	
	@Override
	public Vector2 getVelocity(double x, double y) {
		return velocity.getVal();
	}
	
	@Override
	public void setVelocity(double x, double y) {
		velocity.setVal(x,y);
	}
	
	@Override
	public void addVelocity(double x, double y) {
		velocity.addVal(x,y);
	}
	
	Consumer<Double> angleSetter;
	
	@Override
	public void setAngleSetter(Consumer<Double> angleSetter) {
		this.angleSetter = angleSetter;
	}
	
	Supplier<Double> angleGetter;
	
	@Override
	public void setAngleGetter(Supplier<Double> angleGetter) {
		this.angleGetter = angleGetter;
	}
	
	@Override
	public Supplier<Double> getAngleGetter() {
		return angleGetter;
	}
	
	@Override
	public Consumer<Double> getAngleSetter() {
		return angleSetter;
	}
	
	@Override
	public double getAngle() {
		return angleGetter.get();
	}
	
	@Override
	public void setAngle(double angle) {
		angleSetter.accept(angle);
	}
	
	Consumer<Double> angularForceApplicator;
	
	@Override
	public void setAngularForceApplicator(Consumer<Double> applicator) {
		this.angularForceApplicator = applicator;
	}
	
	@Override
	public Consumer<Double> getAngularForceApplicator() {
		return angularForceApplicator;
	}
	
	@Override
	public void addAngularForce(double amt) {
		angularForceApplicator.accept(amt);
	}
}
