package net.net16.jeremiahlowe.caffeinephysics;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.collider.Collider;
import net.net16.jeremiahlowe.caffeinephysics.util.Utility;

public class Body {
	private boolean isStatic = false;
	private float positionX, positionY;
	private float velocityX, velocityY;
	private float mass;
	private Collider collider;
	public boolean ignoreGravity = false;
	
	public Body(){this(null);}
	public Body(Collider c){this(c, 0, 0);}
	public Body(float x, float y){this(null, x, y, 1);}
	public Body(Collider c, float x, float y){this(c, x, y, 1);}
	public Body(Collider c, float x, float y, float m){
		setCollider(c);
		setPosition(x, y);
		setVelocity(0, 0);
		setMass(m);
	}
	
	public void setPosition(float x, float y){
		positionX = x;
		positionY = y;
	}
	public void setVelocity(float x, float y){
		if(isStatic) throw new RuntimeException("Cannot set velociy of a static body!");
		velocityX = x;
		velocityY = y;
	}
	public void setVelocity(Vector2 velocity) {setVelocity(velocity.x, velocity.y);}
	public void setMass(float mass){this.mass = mass;}
	public void setCollider(Collider collider){this.collider = collider;}
	public float getMass(){return mass;}
	public float getPositionX() {return positionX;}
	public float getPositionY() {return positionY;}
	public Vector2 getPosition() {return new Vector2(getPositionX(), getPositionY());}
	public float getVelocityX() {return velocityX;}
	public float getVelocityY() {return velocityY;}
	public Vector2 getVelocity() {return new Vector2(getVelocityX(), getVelocityY());}
	public Collider getCollider() {return collider;}
	public float getMomentumX(){return velocityX * mass;}
	public float getMomentumY(){return velocityY * mass;}
	public Vector2 getMomentum(){return new Vector2(getMomentumX(), getMomentumY());}
	public void setVelocityFromMomentum(Vector2 m){setVelocityFromMomentum(m.x, m.y);}
	public void setVelocityFromMomentum(float momentX, float momentY){
		if(isStatic) throw new RuntimeException("Cannot set velociy of a static body!");
		velocityX = momentX / mass;
		velocityY = momentY / mass;
	}
	public void addVelocityFromMomentum(Vector2 m){setVelocityFromMomentum(m.x, m.y);}
	public void addVelocityFromMomentum(float momentX, float momentY){
		if(isStatic) throw new RuntimeException("Cannot change velociy of a static body!");
		velocityX += momentX / mass;
		velocityY += momentY / mass;
	}
	public void setStatic(boolean isStatic){this.isStatic = isStatic;}
	public boolean isStatic(){return isStatic;}
	public boolean hasCollider(){return collider != null && collider.enabled;}
	public void translateX(float by){positionX += by;}
	public void translateY(float by){positionY += by;}
	public void translate(Vector2 by){
		translateX(by.x);
		translateY(by.y);
	}
	
	@Override
	public String toString(){
		return "Body: " + Utility.vector2ToPair(getPosition()) + (isStatic ? " STATIC" : "");
	}
}
