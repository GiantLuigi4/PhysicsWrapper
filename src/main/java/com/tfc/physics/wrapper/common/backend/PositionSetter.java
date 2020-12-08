package com.tfc.physics.wrapper.common.backend;

import java.util.function.Consumer;

public class PositionSetter {
	private final Consumer<Double> setterX;
	private final Consumer<Double> setterY;
	
	public PositionSetter(Consumer<Double> setterX, Consumer<Double> setterY) {
		this.setterX = setterX;
		this.setterY = setterY;
	}
	
	public void setX(double x) {
		setterX.accept(x);
	}
	
	public void setY(double y) {
		setterY.accept(y);
	}
}
