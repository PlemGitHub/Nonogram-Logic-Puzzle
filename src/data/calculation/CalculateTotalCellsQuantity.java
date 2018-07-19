package data.calculation;

import java.util.ArrayList;
import data.GameFieldParameters;

public class CalculateTotalCellsQuantity {	
	
	public static void calculate(ArrayList<String> levelStrings, GameFieldParameters gfp) {
		String[] dimensionStrings = levelStrings.get(2).split(" ");
		
		gfp.horQ = Integer.valueOf(dimensionStrings[0]);		
		gfp.maxHorDigits = 0;
		int horIndexStart = levelStrings.indexOf("Horizontal")+1;
		int horIndexEnd = levelStrings.indexOf("Vertical")-1;
		for (int i = horIndexStart; i <= horIndexEnd; i++) {
			String[] digitsInColumn = levelStrings.get(i).split(" ");
			if (gfp.maxHorDigits < digitsInColumn.length)
				gfp.maxHorDigits = digitsInColumn.length;
		}
		gfp.horQwDigits = gfp.horQ + gfp.maxHorDigits;

		gfp.vertQ = Integer.valueOf(dimensionStrings[1]);
		gfp.maxVertDigits = 0;
		int vertIndexStart = levelStrings.indexOf("Vertical")+1;
		int vertIndexEnd = levelStrings.indexOf("ENDLEVEL")-1;
		for (int i = vertIndexStart; i <= vertIndexEnd; i++) {
			String[] digitsInColumn = levelStrings.get(i).split(" ");
			if (gfp.maxVertDigits < digitsInColumn.length)
				gfp.maxVertDigits = digitsInColumn.length;
		}
		gfp.vertQwDigits = gfp.vertQ + gfp.maxVertDigits;	
	}
}
