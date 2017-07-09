package testing.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.JPanel;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.caffeinephysics.Body;
import net.net16.jeremiahlowe.caffeinephysics.Utility;
import net.net16.jeremiahlowe.caffeinephysics.World;

public class WorldPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public Color bodyColor = Color.BLACK;
	public Color colliderColor = Color.CYAN;
	public Color boundingBoxColor = new Color(255, 200, 0, 125);
	public int xSize = 5;
	private World world = null;
	private float viewW = 10, viewH = 10;
	public float viewportPosX = 0, viewportPosY = 0;
	
	public WorldPanel(World world){
		if(world.hasFixedSize()) init(world, world.getFixedWidth(), world.getFixedHeight());
		else init(world, 10, 10);
	}
	public WorldPanel(World world, float viewportW, float viewportH){init(world, viewportW, viewportH);}
	private void init(World world, float viewportW, float viewportH){
		setBackground(Color.WHITE);
		setViewportSize(viewportW, viewportH);
		setWorld(world);
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("key press!");
				int k = e.getKeyCode();
				switch(k){
					case KeyEvent.VK_UP:
						viewportPosY++;
						break;
					case KeyEvent.VK_DOWN:
						viewportPosY--;
						break;
					case KeyEvent.VK_LEFT:
						viewportPosX--;
						break;
					case KeyEvent.VK_RIGHT:
						viewportPosX++;
						break;
					case KeyEvent.VK_EQUALS:
						viewW++;
						viewH++;
						break;
					case KeyEvent.VK_MINUS:
						viewW--;
						viewH--;
						break;
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics ug) {
		if(world == null) return;
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), ColorModel.TRANSLUCENT);
		Graphics g = buffer.getGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		drawWorld(g);
		ug.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
	}
	public void drawWorld(Graphics g){
		g.setColor(Color.BLACK);
		int h = g.getFontMetrics().getHeight() / 2;
		GraphicsUtil.drawCenteredString(g, world.toString(), getWidth() / 2, h);
		String viewportInfo = "Using viewport " + viewW + "x" + viewH + " at (" + viewportPosX + ", " + viewportPosY + ")";
		GraphicsUtil.drawCenteredString(g, viewportInfo, getWidth() / 2, 2 * h + h);
		for(Body b : world.getBodies()){
			drawBody(g, b);
		}
	}
	public void drawBody(Graphics g, Body b){
		Vector2 bodyPos = b.getPosition();
		int bx = worldToPixelX(bodyPos.x);
		int by = worldToPixelY(bodyPos.y);
		g.setColor(bodyColor);
		if(Utility.betweenOrEqual(bx, 0, getWidth()) && Utility.betweenOrEqual(by, 0, getHeight())){
			int xo = Math.round(xSize / 2);
			g.drawLine(bx - xo, by - xo, bx + xo, by + xo);
			g.drawLine(bx - xo, by + xo, bx + xo, by - xo);
			GraphicsUtil.drawCenteredString(g, b.toString(), bx, by + xSize);
			if(b.hasCollider()){
				Polygon p = new Polygon();
				Vector2[] verts = b.getCollider().getVerticies(b);
				for(int i = 0; i < verts.length; i++){
					Vector2 v = verts[i];
					if(v == null) continue;
					p.addPoint(worldToPixelX(v.x), worldToPixelY(v.y));
				}
				g.setColor(colliderColor);
				g.drawPolygon(p);
				g.setColor(boundingBoxColor);
				Vector2 bbs = b.getCollider().getBoundingBoxSize();
				Vector2 pos = b.getPosition();
				int bbw = worldToPixelW(bbs.x), bbh = worldToPixelH(bbs.y);
				Vector2 sbp = new Vector2(pos.x - bbs.x / 2, pos.y + bbs.y / 2);
				int bbx = worldToPixelX(sbp.x), bby = worldToPixelY(sbp.y);
				g.drawRect(bbx, bby, bbw, bbh);
			}
		}
	}
	
	private int worldToPixelX(float x){
		return Math.round(Utility.map(x + viewportPosX, viewW / -2, viewW / 2, 0, getWidth()));
	}
	private int worldToPixelY(float y){
		return Math.round(Utility.map(y + viewportPosY, viewH / -2, viewH / 2, getHeight(), 0));
	}
	private int worldToPixelW(float x){
		return Math.round(Utility.map(x, 0, viewW, 0, getWidth()));
	}
	private int worldToPixelH(float y){
		return Math.round(Utility.map(y, 0, viewH, 0, getHeight()));
	}
	
	public void setWorld(World world){
		if(world == null) throw new NullPointerException("World is null!");
		this.world = world;
	}
	public void setViewportSize(float width, float height){
		setViewportWidth(width);
		setViewportHeight(height);
	}
	public void setViewportWidth(float width){
		if(width <= 0) throw new RuntimeException("Invalid width for vieport!");
		viewW = width;
	}
	public void setViewportHeight(float height){
		if(height <= 0) throw new RuntimeException("Invalid height for vieport!");
		viewH = height;
	}
	public World getWorld(){return world;}
	public float getViewportWidth(){return viewW;}
	public float getViewportHeight(){return viewH;}
}
