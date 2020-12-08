package com.tfc.physics.wrapper;

public class Physics {
	private static boolean isBox2DUsed = false;
	
	public static void init() {
		try {
			Class.forName("org.jbox2d.collision.AABB");
			isBox2DUsed = true;
		} catch (Throwable ignored) {
		}
	}
	
	public static boolean isIsBox2DUsed() {
		return isBox2DUsed;
	}
}
