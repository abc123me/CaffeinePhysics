package net.net16.jeremiahlowe.caffeinephysics.collider;

import net.net16.jeremiahlowe.caffeinephysics.Body;

public class BoxCollider extends Body{
	public BoxCollider(){
		
	}
	@Override
	public boolean isCollidingWith(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean willCollideWith(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStaticBody() {
		return false;
	}

}
