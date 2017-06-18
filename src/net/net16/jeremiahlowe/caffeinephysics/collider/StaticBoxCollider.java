package net.net16.jeremiahlowe.caffeinephysics.collider;

public class StaticBoxCollider extends BoxCollider{
	@Override
	public boolean isStaticBody() {
		return true;
	}
}
