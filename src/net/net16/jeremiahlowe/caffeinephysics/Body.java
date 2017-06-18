package net.net16.jeremiahlowe.caffeinephysics;

public abstract class Body {
	public float velocityX, velocityY;
	public float positionX, positionY;
	private float weight;
	
	public Body(){
		velocityX = 0;
		velocityY = 0;
		positionX = 0;
		positionY = 0;
		weight = 1;
	}
	
	public final float getWeight(){return weight;}
	public final void setWeight(float weight){this.weight = weight;}
	
	public abstract boolean isStaticBody();
	public abstract boolean isCollidingWith(float x, float y);
	public abstract boolean willCollideWith(float x, float y);
}
