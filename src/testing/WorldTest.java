package testing;

import net.net16.jeremiahlowe.caffeinephysics.Body;
import net.net16.jeremiahlowe.caffeinephysics.collider.BoxCollider;
import net.net16.jeremiahlowe.caffeinephysics.collider.CircleCollider;

public class WorldTest extends GeneralTest{
	public static void main(String[] args) {
		new WorldTest();
	}
	@Override
	public void onRunTest() {
		Body square = new Body(new BoxCollider(1f), 0, 2, 1);
		Body circle = new Body(new CircleCollider(0.5f), 0, 3, 0.79f);
		Body floor = new Body(new BoxCollider(9, 1f), 0, -4f);
		floor.setStatic(true);
		world.addBody(circle);
		world.addBody(floor);
		world.addBody(square);
		square.ignoreGravity = true;
		world.setGravity(0, -2f);
	}
}
