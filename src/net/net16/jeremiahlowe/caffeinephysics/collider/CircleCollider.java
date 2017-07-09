package net.net16.jeremiahlowe.caffeinephysics.collider;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.Body;

public class CircleCollider extends Collider{
	private float radius = 0.5f, radius2 = 0.25f;
	private Vector2[] verticies;
	private int detail = 30;
	
	public CircleCollider(float radius) {
		verticies = new Vector2[30];
		setRadius(radius);
		setDetail(30);
	}
	public void setDetail(int detail){
		if(detail < 5) throw new RuntimeException("Detail must be greator than or equal to 5!");
		this.detail = detail;
		generateVerticies();
	}
	public void setRadius(float radius){
		this.radius = radius;
		this.radius2 = radius * radius;
	}
	public void setDiameter(float diameter){setRadius(diameter / 2);}
	
	@Override
	public Vector2[] getVerticiesNonParented() {
		int len = verticies.length;
		if(len != detail) setDetail(detail);
		return verticies;
	}
	@Override
	public Vector2[] getVerticiesOffsetted(float x, float y) {
		int len = verticies.length;
		Vector2[] nv = new Vector2[len];
		if(len != detail) setDetail(detail); 
		for(int i = 0; i < nv.length; i++){
			nv[i] = new Vector2();
			nv[i].x = verticies[i].x + x;
			nv[i].y = verticies[i].y + y;
		}
		return nv;
	}
	@Override
	public boolean pointLiesIn(Body b, float x, float y) {
		float dist2 = (float) Vector2.distance2(new Vector2(x, y),  b.getPosition());
		return dist2 < radius2;
	}
	
	private void generateVerticies(){
		verticies = new Vector2[detail];
		float moveAngle = (float) ((2f * Math.PI) / detail);
		for(int i = 0; i < detail; i++){
			float rot = moveAngle * i;
			verticies[i] = new Vector2();
			verticies[i].x = (float) (radius * Math.cos(rot));
			verticies[i].y = (float) (radius * Math.sin(rot));
		}
	}
}
