package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import data.GameFieldParameters;
import mech.Constants;
import visual.myComponents.MyLabel;

@SuppressWarnings("serial")
public class Cell extends MyLabel implements Constants{
		
	public enum CellState {FILLED, CLEARED, CROSSED, UNCROSSED, DOTTED, TEMPDOTTED;
		public CellState changeOnLMB(){
			if (this == CellState.FILLED)
				return CellState.CLEARED;
			if (this == CellState.CLEARED)
				return CellState.FILLED;
			if (this == CellState.CROSSED)
				return CellState.UNCROSSED;
			if (this == CellState.UNCROSSED)
				return CellState.CROSSED;
			if (this == CellState.DOTTED)
				return CellState.FILLED;
			if (this == CellState.TEMPDOTTED)
				return CellState.FILLED;
			
			return null;
		}
		};
	public CellState cellState;
	private Point pointIJ;

	public Cell(int i, int j, GameFieldParameters gfp, String side) {
		setLayout(null);
		setOpaque(true);
		setBackground(Color.WHITE);
		pointIJ = new Point(i, j);
		String text = "";
		int x = 0, y = 0;
		switch (side) {
			case HORIZONTAL_CELL:	{
				setName(HORIZONTAL_CELL);
				x = gfp.digitsStartPoint.x - i*gfp.cellSize;
				y = gfp.digitsStartPoint.y + j*gfp.cellSize;
				text = gfp.horDigitsArr[i][j-1];
				if (text == null)
					cellState = CellState.CROSSED;
				else
					cellState = CellState.UNCROSSED;
			}
			break;
			
			case VERTICAL_CELL:	{
				setName(VERTICAL_CELL);
				x = gfp.digitsStartPoint.x + i*gfp.cellSize;
				y = gfp.digitsStartPoint.y - j*gfp.cellSize;
				text = gfp.vertDigitsArr[i-1][j];
				if (text == null)
					cellState = CellState.CROSSED;
				else
					cellState = CellState.UNCROSSED;
			}
			break;
			
			case FIELD_CELL:		{
				setName(FIELD_CELL);
				x = gfp.digitsStartPoint.x + i*gfp.cellSize;
				y = gfp.digitsStartPoint.y + j*gfp.cellSize;
				cellState = CellState.CLEARED;
			}
		}
		
		setBounds(x, y, gfp.cellSize, gfp.cellSize);
		
		setText(text);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		
		switch (cellState) 	{
			case FILLED: 		{
				setBackground(FILLED_COLOR);
			}
				break;
			case CLEARED:  		{
				setBackground(Color.WHITE);
			}
				break;
			case CROSSED:		{
				setBackground(CROSSED_COLOR);
			}
				break;
			case UNCROSSED:		{
				if (!getText().equals(""))
					setBackground(Color.WHITE);
			}
				break;
			case DOTTED:		{
				setBackground(Color.WHITE);
				g.setColor(FILLED_COLOR);
				g.fillRect(getWidth()*3/8, getHeight()*3/8, getWidth()/4, getHeight()/4);
			}
				break;
				
			case TEMPDOTTED:	{
				setBackground(Color.WHITE);
				g.setColor(FILLED_COLOR);
				g.fillRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
			}
				break;
		}
	}
	
	public boolean isTempDotted(){
		return cellState == CellState.TEMPDOTTED? true:false;
	}
	
	public String getPointIjString(){
		return Integer.toString(pointIJ.x)+" "+Integer.toString(pointIJ.y);
	}
}
