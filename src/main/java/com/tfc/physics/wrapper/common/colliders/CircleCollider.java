package com.tfc.physics.wrapper.common.colliders;

import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.interfaces.ICustomShapeCollider;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class CircleCollider extends Collider implements ICustomShapeCollider {
	private final double width;
	private final double height;
	private final ArrayList<Vector2> points = new ArrayList<>();
	
	public CircleCollider(double width, double height) {
		this(width,height,32);
	}
	
	public CircleCollider(double width, double height, float resolution) {
		this.width = width;
		this.height = height;
		
		for (int i = 0; i < 360/ resolution; i+=1) {
			double a = Math.toRadians((i * resolution) + (180));
			double v = Math.cos(Math.toRadians(i * resolution)) * width;
			double v1 = Math.sin(Math.toRadians(i * resolution)) * height;
			points.add(
					new Vector2(
							(float) v,
							(float) v1
					)
			);
			points.add(
					new Vector2(
							(float) (Math.cos(a) * width),
							(float) (Math.sin(a) * height)
					)
			);
			points.add(
					new Vector2(
							(float) v,
							(float) v1
					)
			);
			points.add(
					new Vector2(
							(float) v,
							(float) v1
					)
			);
		}
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	@Override
	public ArrayList<Vector2> createShape() {
		return points;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform transform = g2d.getTransform();
		g2d.translate(this.getX(),this.getY());
////		g2d.rotate(Math.toRadians(this.getAngle()));
////		g2d.scale(width,height);
////		g2d.translate(-30,-0);
//		g2d.drawArc((int)-width,(int)-width,(int)width*2,(int)height*2,0,360);
		
		for (int i = 0; i < points.size() - 1; i++) {
			Vector2 vec1 = points.get(i);
			Vector2 vec2 = points.get(i + 1);
			g2d.drawLine(
					(int) vec1.x, (int) vec1.y,
					(int) vec2.x, (int) vec2.y
			);
		}
		
		g2d.setTransform(transform);
	}
}
