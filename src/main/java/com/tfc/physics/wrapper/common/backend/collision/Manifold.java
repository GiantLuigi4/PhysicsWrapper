package com.tfc.physics.wrapper.common.backend.collision;

import com.tfc.physics.wrapper.common.API.Vector2;

public class Manifold {
	public final ContactEdge edgeA;
	public final ContactEdge edgeB;
	
	public final int pointCount;
	
	public final Vector2 localNormal;
	public final Vector2 localPoint;
	
	public Manifold(ContactEdge edgeA, ContactEdge edgeB, int pointCount, Vector2 localNormal, Vector2 localPoint) {
		this.edgeA = edgeA;
		this.edgeB = edgeB;
		this.pointCount = pointCount;
		this.localNormal = localNormal;
		this.localPoint = localPoint;
	}
}
