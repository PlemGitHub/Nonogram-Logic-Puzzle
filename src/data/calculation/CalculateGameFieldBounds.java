package data.calculation;

import java.awt.Rectangle;

import data.GameFieldParameters;
import mech.Constants;

public class CalculateGameFieldBounds implements Constants{

	public static Rectangle calculate(GameFieldParameters gfp){
		int width = gfp.cellSize*gfp.horQwDigits;
		int height = gfp.cellSize*gfp.vertQwDigits;
		
		int x = FIELD_X_CENTER-width/2;
		int y = 0;
		return new Rectangle(x, y, width, height);
	}
}
