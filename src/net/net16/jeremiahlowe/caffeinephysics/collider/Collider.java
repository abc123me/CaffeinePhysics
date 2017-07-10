package net.net16.jeremiahlowe.caffeinephysics.collider;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.Body;

public abstract class Collider {
	/**
	 * In radians
	 */
	protected float rotation = 0; 
	/**
	 * Angular velocity in rad/sec
	 */
	protected float radiansPerSecond = 0;
	
	public int layer = 0;
	public boolean enabled = true;
	
	public abstract Vector2[] getVerticiesNonParented();
	public abstract Vector2[] getVerticiesOffsetted(float x, float y);
	public abstract boolean pointLiesIn(Body b, float x, float y);
	
	public void setRotationRadians(float rotation){this.rotation = rotation;}
	public float getRotationRadians(){return rotation;}
	public void setRotationPerSecondRadians(float radiansPerSecond) {this.radiansPerSecond = radiansPerSecond;}
	public float getRotationPerSecondRadians() {return radiansPerSecond;}
	public boolean pointLiesIn(Body b, Vector2 p){return pointLiesIn(b, p.x, p.y);}
	public Vector2[] getVerticies(Body b){return getVerticiesOffsetted(b.getPositionX(), b.getPositionY());}
	public Vector2 getBoundingBoxSize(){
		float maxX = Float.MIN_VALUE, maxY = maxX, minX = Float.MAX_VALUE, minY = minX;
		for(Vector2 v : getVerticiesNonParented()){
			if(v.x > maxX) maxX = v.x;
			if(v.y > maxY) maxY = v.y;
			if(v.x < minX) minX = v.x;
			if(v.y < minY) minY = v.y;
		}
		return new Vector2(maxX - minX, maxY - minY);
	}
	public Vector2 getMidpoint(Body b){
		Vector2 m = new Vector2();
		Vector2[] verts = (b == null ? getVerticiesNonParented() : getVerticies(b));
		for(Vector2 v : verts) m.translate(v);
		m.x /= verts.length; m.y /= verts.length;
		return m;
	}
	/**
	 * Applies momentum to a specific point on the collider, 
	 * then calculates how much of that goes to angular momentum and normal momentum
	 * <bold>NON PARENTED</bold>
	 * @param x
	 * @param y
	 */
	public void applyMomentumToPoint(float x, float y){
		
	}
	/**
	 * Called when two objects collide
	 * @param current
	 * @param with
	 * @param collisionPointX
	 * @param collidionPointY
	 */
	public void onCollideWith(Body current, Body with, float collisionPointX, float collidionPointY){}

	public final void setRotation(){setRotationRadians((float) Math.toRadians(rotation));}
	public final float getRotation(){return (float) Math.toDegrees(getRotationRadians());}
	public final void setRotationPerSecond(){setRotationPerSecondRadians((float) Math.toRadians(rotation));}
	public final float getRotationPerSecond(){return (float) Math.toDegrees(getRotationPerSecondRadians());}
}
