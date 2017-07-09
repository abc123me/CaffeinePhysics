package net.net16.jeremiahlowe.caffeinephysics;

import java.util.ArrayList;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.collider.Collider;

public class World {
	private float width, height;
	private boolean hasFixedSize;
	private float gravityVelocityX;
	private float gravityVelocityY;
	private long lastUpdateTime, timeDifferential;
	private ArrayList<Body> bodies;
	
	public World(float width, float height){
		hasFixedSize = true;
		this.height = height;
		this.width = width;
		init();
	}
	public World(){
		hasFixedSize = false;
		this.height = 0;
		this.width = 0;
		init();
	}
	private void init(){
		bodies = new ArrayList<Body>();
		this.gravityVelocityX = 0;
		this.gravityVelocityY = 0;
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public void update(){
		timeDifferential = System.currentTimeMillis() - lastUpdateTime;
		applyVelocities();
		lastUpdateTime = System.currentTimeMillis();
	}
	private void applyVelocities(){
		for(Body b : bodies){
			if(b.isStatic()) continue;
			applyGravity(b);
			applyVelocity(b);
		}
	}
	private void applyVelocity(Body to){
		float velX = to.getVelocityX();
		float velY = to.getVelocityY();
		float chgVX = interpolateVelocityChange(velX, timeDifferential);
		float chgVY = interpolateVelocityChange(velY, timeDifferential);
		float nPosX = to.getPositionX() + chgVX;
		float nPosY = to.getPositionY() + chgVY;
		if(to.hasCollider()){
			Collider c = to.getCollider();
			Vector2 closest = null;
			float closestDist = Float.MAX_VALUE;
			for(Vector2 v : c.getVerticiesNonParented()){
				float dist = (float) Vector2.distance2(new Vector2(nPosX, nPosY), Vector2.add(v, to.getPosition()));
				if(dist < closestDist){
					closestDist = dist;
					closest = v;
				}
			}
			float offX = closest.x, offY = closest.y;
			Body cb = getCollisionBodyAt(nPosX + offX, nPosY + offY);
			if(cb != null){
				if(cb.isStatic()) return;
				else return;
			}
		}
		to.setPosition(nPosX, nPosY);
	}
	
	private void applyGravity(Body b){
		float vx = b.getVelocityX(), vy = b.getVelocityY();
		vy = gravityVelocityY;
		b.setVelocity(vx, vy);
	}
	public float interpolateVelocityChange(float unitsPerSecond, long time){
		if(unitsPerSecond == 0) return 0;
		if(time <= 0){
			System.err.println("Time differential is under 0, velocity inaccuracies will occur");
			time = 1;
		}
		return (unitsPerSecond * time) / 1000;
	}
	public Body getCollisionBodyAt(float x, float y){
		for(Body b : bodies){
			if(b.hasCollider() && b.getCollider().pointLiesIn(b, x, y)){
				return b; 
			}
		}
		return null;
	}
	public Body getBodyAt(float x, float y){
		for(Body b : bodies){
			if(b.getPosition().x == x && b.getPositionY() == y)
				return b;
			else if(b.hasCollider() && b.getCollider().pointLiesIn(b, x, y))
				return b; 
		}
		return null;
	}
	public Body raycast(Vector2 start, Vector2 dir){return raycast(start, dir, null);}
	public Body raycast(Vector2 start, Vector2 dir, LayerMask mask){
		Vector2 dirC = Utility.getDirection(dir, start);
		for(Body b : bodies){
			if(b.hasCollider() && (mask == null || mask.tryLayer(b.getCollider().layer))){
				Vector2[] verts = b.getCollider().getVerticies(b);
				for(int i = 1; i < verts.length; i++){
					Vector2 vert1 = verts[i - 1], vert2 = verts[i];
					Vector2 dirV1 = Utility.getDirection(vert1, start);
					Vector2 dirV2 = Utility.getDirection(vert2, start);
					if(Utility.rayDirCheck(dirC, dirV1, dirV2)) return b;
				}
			}
		}
		return null;
	}
	/*public RaycastHit raycastDetailed(Vector2 start, Vector2 dir){return raycastDetailed(start, dir, null);}
	public RaycastHit raycastDetailed(Vector2 start, Vector2 dir, LayerMask mask){
		Vector2 dirC = Utility.getDirection(dir, start);
		for(Body b : bodies){
			if(b.hasCollider() && (mask == null || mask.tryLayer(b.getCollider().layer))){
				Vector2[] verts = b.getCollider().getVerticies(b);
				for(int i = 1; i < verts.length; i++){
					Vector2 vert1 = verts[i - 1], vert2 = verts[i];
					Vector2 dirV1 = Utility.getDirection(vert1, start);
					Vector2 dirV2 = Utility.getDirection(vert2, start);
					if(Utility.rayDirCheck(dirC, dirV1, dirV2)){
						float slope1 = Utility.vectorAsSlope(dirV1);
						float slope2 = Utility.vectorAsSlope(dirV2);
						float slope3 = Utility.vectorAsSlope(dirC);
						//float d1 = 
						if(slope3 == Float.POSITIVE_INFINITY) slope3 = (slope1 + slope2) / 2;
						System.out.println(Utility.map(slope3, slope1, slope2, 0f, 1f));
						return new RaycastHit(b, 0, 0, 0);
					}
				}
			}
		}
		return null;
	}*/
	
	public void addBody(Body body){bodies.add(body);}
	public void removeBody(Body body){bodies.remove(body);}
	public ArrayList<Body> getBodies(){return bodies;}
	public void clear(){bodies.clear();}
	public void disableFixedSize(){hasFixedSize = false;}
	public void setFixedSize(float width, float height){
		if(width <= 0 || height <= 0) throw new RuntimeException("Width or height cannot be a value less than or equal to zero!");
		this.width = width;
		this.height = height;
		this.hasFixedSize = true;
	}
	public float getFixedWidth(){return width;}
	public float getFixedHeight(){return height;}
	public boolean hasFixedSize() {return hasFixedSize;}
	public void setGravity(float gravityVelocityX, float gravityVelocityY){
		this.gravityVelocityX = gravityVelocityX;
		this.gravityVelocityY = gravityVelocityY;
	}
	public float getGravityVelocityX(){return gravityVelocityX;}
	public float getGravityVelocityY(){return gravityVelocityY;}
	public long getLastTimeDifferential(){return timeDifferential;}
	
	@Override
	public String toString(){
		String walls = hasFixedSize ? " with walls" : ""; 
		int stcb = 0;
		for(Body b : bodies) if(b.isStatic()) stcb++;
		int nstcb = bodies.size() - stcb;
		String bodyInfo = ", " + nstcb + " active " + pluralSingular(nstcb, "bodies", "body")
		+ ", and " + stcb + " inactive " + pluralSingular(stcb, "bodies", "body");
		return "World" + walls + bodyInfo + "!";
	}
	private static final String pluralSingular(int amo, String plural, String singular){
		return amo > 1 ? plural : singular;
	}
}
