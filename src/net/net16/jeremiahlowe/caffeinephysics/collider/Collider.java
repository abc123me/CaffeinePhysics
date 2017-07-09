package net.net16.jeremiahlowe.caffeinephysics.collider;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.Body;

public abstract class Collider {
	public int layer = 0;
	public boolean ignoresGravity = false;
	public boolean enabled = true;
	
	public abstract Vector2[] getVerticiesNonParented();
	public abstract Vector2[] getVerticiesOffsetted(float x, float y);
	public Vector2[] getVerticies(Body b){return getVerticiesOffsetted(b.getPositionX(), b.getPositionY());}
	public final boolean pointLiesIn(Body b, Vector2 p){return pointLiesIn(b, p.x, p.y);}
	public abstract boolean pointLiesIn(Body b, float x, float y);
	
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
}
