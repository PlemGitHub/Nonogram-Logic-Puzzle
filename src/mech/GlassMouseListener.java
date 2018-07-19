package mech;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import data.GameFieldParameters;
import visual.Cell;
import visual.Cell.CellState;
import visual.GameFieldPanel;

public class GlassMouseListener implements MouseMotionListener, MouseListener {
	
	private GameFieldPanel gf;
	private GameFieldParameters gfp;
	private Cell cellUnderMouse;
	private CellState actionState;
	private String partOfField;

	public GlassMouseListener(GameFieldPanel gf, GameFieldParameters gfp) {
		this.gf = gf;
		this.gfp = gfp;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (mouseXYonGameField(e)){
			Cell cell = (Cell)gf.findComponentAt(x, y);
			if (partOfField == null){
				partOfField = cell.getName();
//				partOfField = partOfField.equals("Field cell")? "Field cell":"Side";
			}
			if (cellUnderMouse == null || cell != cellUnderMouse){
				cellUnderMouse = cell;
				if (SwingUtilities.isLeftMouseButton(e)){
					if (e.isShiftDown()){
						if (actionState == null)
							actionState = cell.cellState == CellState.TEMPDOTTED? CellState.CLEARED:CellState.TEMPDOTTED;
						if (cell.getName().equals("Field cell"))
							cell.cellState = actionState;
					}
					else
					if (e.isControlDown()){
						gf.clearAllTempDots();
					}
					else{
						if (actionState == null)
							actionState = cell.cellState.changeOnLMB();
						if (cell.getName().equals(partOfField))
							cell.cellState = actionState;
					}
				}
				if (SwingUtilities.isRightMouseButton(e)){
					if (actionState == null)
						actionState = cell.cellState == CellState.DOTTED? CellState.CLEARED:CellState.DOTTED;
					if (cell.getName().equals("Field cell"))
						cell.cellState = actionState;
				}
				cell.repaint();
			}
		}
	}

	private boolean mouseXYonGameField(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x > 0 && x < gfp.cellSize*gfp.maxHorDigits &&
				y > 0 && y < gfp.cellSize*gfp.maxVertDigits)
					return false;
		if (x > 0 & x < gf.getWidth() &
				y > 0 & y < gf.getHeight())
					return true;
		else
			return false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		cellUnderMouse = null;
		actionState = null;
		partOfField = null;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
