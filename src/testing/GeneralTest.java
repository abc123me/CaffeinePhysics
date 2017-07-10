package testing;

import javax.swing.JFrame;

import net.net16.jeremiahlowe.caffeinephysics.world.World;
import testing.util.GraphicsUtil;
import testing.util.WorldPanel;

public abstract class GeneralTest {
	public World world;
	public WorldPanel worldPanel;
	public Thread updateThread;
	public JFrame frame;
	
	public GeneralTest(){
		world = new World();
		worldPanel = new WorldPanel(world, 10, 10);
		frame = makeFrame(worldPanel, getFrameWidth(), getFrameHeight());
		updateThread = makeUpdateThread(worldPanel);
		onInit();
		onRunTest();
	}
	
	public static JFrame makeFrame(WorldPanel worldPanel, int width, int height){
		JFrame hld = GraphicsUtil.makeHolderFrame("Holder frame", width, height);
		hld.setContentPane(worldPanel);
		hld.setVisible(true);
		return hld;
	}
	public static Thread makeUpdateThread(WorldPanel renderer){
		return new Thread(new RepaintRunnable(renderer));
	}
	
	public abstract void onRunTest();
	public void onInit(){
		updateThread.start();
	}
	
	public int getFrameWidth(){
		return 400;
	}
	public int getFrameHeight(){
		return 400;
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
