package net.net16.jeremiahlowe.caffeinephysics;

import java.util.ArrayList;

public class World {
	public final float width, height;
	public final boolean hasWalls;
	private ArrayList<Body> bodies;
	
	public World(boolean hasWalls, float width, float height){
		this.hasWalls = hasWalls;
		this.height = height;
		this.width = width;
	}
	public World(){this(false, 0, 0);}
	
	public void addBody(Body body){bodies.add(body);}
	public void removeBody(Body body){bodies.remove(body);}
	public ArrayList<Body> getBodies(){return bodies;}
	public void clear(){bodies.clear();}
}
