package data.calculation;

import java.awt.Point;

import data.GameFieldParameters;

public class CalculateDigitsStartPoint {

	public static void calculate(GameFieldParameters gfp){
		int x = gfp.cellSize*(gfp.maxHorDigits-1);
		int y = gfp.cellSize*(gfp.maxVertDigits-1);
		gfp.digitsStartPoint = new Point(x, y);
	}
}
