package data.calculation;

import data.GameFieldParameters;
import mech.Constants;

public class CalculateCellSize implements Constants{
	
	private static final double kWidth = 0.8;
	private static final double kHeight = 0.95;
	
	public static void calculate(GameFieldParameters gfp){
		int horCellSize = (int)(WINDOW_WIDTH * kWidth) / gfp.horQwDigits;
		int vertCellSize = (int)(WINDOW_HEIGHT * kHeight) / gfp.vertQwDigits;
		// choose the smaller size
		gfp.cellSize = horCellSize<vertCellSize? horCellSize:vertCellSize;
	}
}
