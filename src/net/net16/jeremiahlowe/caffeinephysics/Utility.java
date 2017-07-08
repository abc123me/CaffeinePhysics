package net.net16.jeremiahlowe.caffeinephysics;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;

public class Utility {
	public static final float map(float val, float min, float max, float newMin, float newMax){
		return (((val - min) * (newMax - newMin)) / (max - min)) + newMin;
	}
	public static final int map(int val, int min, int max, int newMin, int newMax){
		return (((val - min) * (newMax - newMin)) / (max - min)) + newMin;
	}
	public static final boolean inRange(float val, float min, float max){
		return val >= min && val <= max;
	}
	public static final boolean inBounds(Vector2 point, Vector2 pos1, Vector2 pos2){
		float ax = Math.min(pos1.x, pos2.x), ay = Math.min(pos1.y, pos2.y);
		float bx = Math.max(pos1.x, pos2.x), by = Math.max(pos1.y, pos2.y);
		return inRange(point.x, ax, bx) && inRange(point.y, ay, by);
	}
	public static final float clamp(float val, float min, float max){
		if(val > max) return max;
		if(val < min) return min;
		return val;
	}
	public static final String vector2ToPair(Vector2 v){
		return "(" + v.x + ", " + v.y + ")";
	}
	public static final Vector2 getDirection(Vector2 from, Vector2 to){
		Vector2 out = to.clone();
		out.x -= from.x;
		out.y -= from.y;
		return out;
	}
	public static final Vector2 negate(Vector2 a){
		return new Vector2(-a.x, -a.y);
	}
	public static final float vectorAsSlope(Vector2 a){
		if(a.x == 0.0f) return Float.POSITIVE_INFINITY;
		if(a.x == -0.0f) return Float.NEGATIVE_INFINITY;
		return a.y / a.x;
	}
	public static final boolean rayDirCheck(Vector2 ray, Vector2 a, Vector2 b){
		float slopeA = vectorAsSlope(a), slopeB = vectorAsSlope(b);
		float minSlope = Math.min(slopeA, slopeB), maxSlope = Math.max(slopeA, slopeB);
		float raySlope = vectorAsSlope(ray);
		if(raySlope == Float.POSITIVE_INFINITY){
			if(minSlope < 0 && maxSlope > 0) return true;
			else return false;
		}
		return inRange(raySlope, minSlope, maxSlope);
	}
}
