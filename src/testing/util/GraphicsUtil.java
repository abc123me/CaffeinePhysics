package testing.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GraphicsUtil {
	public static final void drawCenteredString(Graphics g, String str, int x, int y){
		int w = g.getFontMetrics().stringWidth(str), h = g.getFontMetrics().getHeight();
		g.drawString(str, x - w / 2, y + h / 2);
	}
	public static final JFrame makeHolderFrame(String title, int w, int h){
		JFrame holder = new JFrame(title);
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		holder.setBounds(scr.width / 2 - w /2, scr.height / 2 - h / 2, w, h);
		holder.setAlwaysOnTop(true);
		holder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return holder;
	}
}
