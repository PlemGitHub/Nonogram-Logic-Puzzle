package data.calculation;

import java.util.ArrayList;

import data.GameFieldParameters;

public class ExtractDigitsFromLevelStrings {
	
	public static void extract(ArrayList<String> levelStrings, GameFieldParameters gfp){
		int index = levelStrings.indexOf("Horizontal")+1;
		gfp.horDigitsArr = new String[gfp.maxHorDigits][gfp.vertQ];
		for (int j = 0; j < gfp.vertQ; j++) {
			String[] lineString = levelStrings.get(index+j).split(" ");
			for (int i = 0; i < gfp.maxHorDigits; i++) {
				if (i < lineString.length)
					gfp.horDigitsArr[i][j] = lineString[i];
			}
		}
		
		index = levelStrings.indexOf("Vertical")+1;
		gfp.vertDigitsArr = new String[gfp.horQ][gfp.maxVertDigits];
		for (int i = 0; i < gfp.horQ; i++) {
			String[] lineString = levelStrings.get(index+i).split(" ");
			for (int j = 0; j < gfp.maxVertDigits; j++) {
				if (j < lineString.length)
					gfp.vertDigitsArr[i][j] = lineString[j];
			}
		}
	}
}
