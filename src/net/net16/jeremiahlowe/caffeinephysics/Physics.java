package net.net16.jeremiahlowe.caffeinephysics;

public class Physics {
	public static Body[] raycast(World world, float startX, float startY, float dirX, float dirY, int layer){
		return null;
	}
	public static Body[] raycast(World world, float startX, float startY, float dirX, float dirY){
		return raycast(world, startX, startY, dirX, dirY, 0);
	}
}
