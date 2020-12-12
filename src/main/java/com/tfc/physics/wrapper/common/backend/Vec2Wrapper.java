package com.tfc.physics.wrapper.common.backend;

import com.tfc.physics.wrapper.common.API.Vector2;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Vec2Wrapper {
	Supplier<Vector2> val;
	Consumer<Double> xSetter;
	Consumer<Double> ySetter;
	
	public Vec2Wrapper(Supplier<Vector2> val, Consumer<Double> xSetter, Consumer<Double> ySetter) {
		this.val = val;
		this.xSetter = xSetter;
		this.ySetter = ySetter;
	}
	
	public void setVal(double x, double y) {
		xSetter.accept(x);
		ySetter.accept(y);
	}
	
	public void addVal(double x, double y) {
		xSetter.accept(val.get().x+x);
		ySetter.accept(val.get().y+y);
	}
	
	public Vector2 getVal() {
		return val.get();
	}
}
