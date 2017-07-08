package net.net16.jeremiahlowe.caffeinephysics.collider;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.Body;
import net.net16.jeremiahlowe.caffeinephysics.Utility;

public class BoxCollider extends Collider{
	private Vector2[] verticies;
	private float width = 1, height = 1;
	
	public BoxCollider(float s){this(s, s);}
	public BoxCollider(float width, float height){      
		setSize(width, height);
	}
	
	public void setSize(float width, float height){
		if(width <= 0 || height <= 0) throw new RuntimeException("Width or height cannot be a value less than or equal to zero!");
		this.width = width;
		this.height = height;
		makeRectangleVerticies(width, height);
	}
	
	@Override
	public Vector2[] getVerticiesNonParented() {
		return verticies;
	}
	@Override
	public boolean pointLiesIn(Body b, float x, float y) {
		float minX = width / 2 - b.getPositionX(), maxX = width / 2 + b.getPositionX();
		float minY = height / 2 - b.getPositionY(), maxY = height / 2 + b.getPositionY();
		System.out.println();
		return Utility.inRange(x, minX, maxX) && Utility.inRange(y, minY, maxY);
	}
	@Override
	public Vector2[] getVerticies(Body b) {
		Vector2[] nv = new Vector2[verticies.length];
		if(nv.length != 4) setSize(width, height);
		for(int i = 0; i < nv.length; i++){
			nv[i] = new Vector2();
			nv[i].x = verticies[i].x + b.getPositionX();
			nv[i].y = verticies[i].y + b.getPositionY();
		}
		return nv;
	}
	@Override
	public Vector2 getBoundingBoxSize() {
		return new Vector2(width, height);
	}
	@Override
	public Vector2 getMidpoint(Body b){
		if(b == null) return new Vector2(0, 0);
		else return b.getPosition().clone();
	}
	private void makeRectangleVerticies(float w, float h){
		float a = w / 2, b = h / 2, c = -a, d = -b;
		verticies = new Vector2[]{
				new Vector2(a, b), 
				new Vector2(c, b), 
				new Vector2(c, d), 
				new Vector2(a, d)
		};                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	}
}
