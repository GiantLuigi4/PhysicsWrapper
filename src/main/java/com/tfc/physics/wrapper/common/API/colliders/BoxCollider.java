package com.tfc.physics.wrapper.common.API.colliders;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BoxCollider extends Collider {
	public final int width, height;
	
	public BoxCollider(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform transform = g2d.getTransform();
		g2d.translate(this.getX(),this.getY());
		g2d.rotate((this.getAngle()));
		g2d.drawRect(
				-this.width, -this.height,
				this.width * 2, this.height * 2
		);
		g2d.setTransform(transform);
	}
}
