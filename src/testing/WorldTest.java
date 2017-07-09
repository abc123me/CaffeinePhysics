package testing;

import javax.swing.JFrame;

import net.net16.jeremiahlowe.caffeinephysics.Body;
import net.net16.jeremiahlowe.caffeinephysics.World;
import net.net16.jeremiahlowe.caffeinephysics.collider.BoxCollider;
import net.net16.jeremiahlowe.caffeinephysics.collider.CircleCollider;
import testing.util.GraphicsUtil;
import testing.util.WorldPanel;

public class WorldTest {
	public static final int HOLDER_WIDTH = 400;	
	public static final int HOLDER_HEIGHT = 400;
	public static void main(String[] args) {
		World world = new World();
		WorldPanel worldPanel = new WorldPanel(world);
		makeRenderer(worldPanel);
		Body square = new Body(new BoxCollider(2f), 0, 0);
		Body circle = new Body(new CircleCollider(1f), 0, 3);
		Body floor = new Body(new BoxCollider(9, 1f), 0, -4f);
		floor.setStatic(true);
		world.addBody(circle);
		world.addBody(floor);
		world.addBody(square);
		world.setGravity(0, -9.81f);
		//System.out.println("RAYCAST TEST:");
		//System.out.println(world.raycastDetailed(new Vector2(0, 5), new Vector2(0, -1)));
	}
	private static void makeRenderer(WorldPanel worldPanel){
		JFrame hld = GraphicsUtil.makeHolderFrame("Holder frame", HOLDER_WIDTH, HOLDER_HEIGHT);
		hld.setContentPane(worldPanel);
		hld.setVisible(true);
		final Thread updateThread = new Thread(new RepaintRunnable(worldPanel));
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run() {
				updateThread.interrupt();
			}
		}));
		updateThread.start();
	}
}
class RepaintRunnable implements Runnable{
	private WorldPanel w;
	public RepaintRunnable(WorldPanel w){
		this.w = w;
	}
	public void run() {
		while(true){
			w.getWorld().update();
			w.repaint();
			try{Thread.sleep(5);}catch(Exception e){ break; }
		}
	}
}
