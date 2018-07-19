package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import data.GameFieldParameters;
import mech.GlassMouseListener;

@SuppressWarnings("serial")
public class Glass extends JPanel{
	
	/**
	 * Defines how thick in pixels will be lines every 5 cells.
	 */
	private final int thickLineWidth = 3;
	private GameFieldParameters gfp;

	public Glass(GameFieldPanel gf, GameFieldParameters gfp) {
		this.gfp = gfp;
		setOpaque(false);
		setBounds(gf.getBounds());
			GlassMouseListener gpml = new GlassMouseListener(gf, gfp);
			addMouseMotionListener(gpml);
			addMouseListener(gpml);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawThickLines(g);
	}
	
	private void drawThickLines(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(thickLineWidth));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		
		int vertLines = gfp.horQ/5+1;
		int horLines = gfp.vertQ/5+1;
		int step = gfp.cellSize*5;
		
		int x = gfp.digitsStartPoint.x + gfp.cellSize;
		int y1 = 0;
		int y2 = getHeight();
		for (int i = 0; i < vertLines; i++) {
			g.drawLine(x+step*i, y1, x+step*i, y2);
		}
		
		int y = gfp.digitsStartPoint.y + gfp.cellSize;
		int x1 = 0;
		int x2 = getWidth();
		for (int i = 0; i < horLines; i++) {
			g.drawLine(x1, y+step*i, x2, y+step*i);
		}
	}
}
