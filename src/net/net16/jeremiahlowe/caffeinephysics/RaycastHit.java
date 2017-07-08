package net.net16.jeremiahlowe.caffeinephysics;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;

public class RaycastHit {
	private final Body bodyHit;
	private final float distance;
	private final float hitX;
	private final float hitY;
	public RaycastHit(Body bodyHit, float distance, float hitX, float hitY){
		this.bodyHit = bodyHit;
		this.distance = distance;
		this.hitX = hitX;
		this.hitY = hitY;
	}
	public Body getBodyHit() {return bodyHit;}
	public float getDistance() {return distance;}
	public float getHitX() {return hitX;}
	public float getHitY() {return hitY;}
	public Vector2 getHitPos(){return new Vector2(getHitX(), getHitY());}
	@Override
	public String toString(){
		return "Raycast hit " + bodyHit + " at distance " + distance + " on point (" + hitX + ", " + hitY + ")";
	}
}
