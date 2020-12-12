package com.tfc.physics.wrapper.common.backend.collision;

import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;

public class ContactEdge {
	public final ICollider other;
	
	public ContactEdge(ICollider other) {
		this.other = other;
	}
}
