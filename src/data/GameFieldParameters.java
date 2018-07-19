package data;

import java.awt.Point;

public class GameFieldParameters {
	/**
	 * How many cells horizontally field has without digits.
	 */
		public int horQ;
	/**
	 * How many cells vertically field has without digits.
	 */
		public int vertQ;
	
	/**
	 * How many cells horizontally field has including digits.
	 */
		public int horQwDigits;
	/**
	 * How many cells vertically field has including digits.
	 */
		public int vertQwDigits;
		
	/**
	 * Max digits in lines.
	 */
		public int maxHorDigits;
	/**
	 * Max digits in columns.
	 */
		public int maxVertDigits;
		
	/**
	 * Array of arrays including strings which are representing digits in lines from field to edge.
	 */
	public String[][] horDigitsArr;
	/**
	 * Array of arrays including strings which are representing digits in columns from field to edge.
	 */
	public String[][] vertDigitsArr;
	/**
	 * Size of one cell.
	 */
	public int cellSize;
	/**
	 * Point in "light gray zone" from which +cellSize in x and y matches top-right horizontal digit cell and bottom-left vertical
	 * digit cell.
	 */
	public Point digitsStartPoint = new Point();
}
