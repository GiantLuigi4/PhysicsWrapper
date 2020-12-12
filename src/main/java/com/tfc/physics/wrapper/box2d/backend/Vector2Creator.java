package com.tfc.physics.wrapper.box2d.backend;

import com.tfc.physics.wrapper.common.API.Vector2;
import org.jbox2d.common.Vec2;

public class Vector2Creator {
	public static Vector2 create(Vec2 vec2) {
		return new Vector2(vec2.x,vec2.y);
	}
}
