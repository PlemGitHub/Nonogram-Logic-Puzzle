package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import data.GameFieldParameters;
import data.calculation.CalculateCellSize;
import data.calculation.CalculateDigitsStartPoint;
import data.calculation.CalculateGameFieldBounds;
import data.calculation.CalculateTotalCellsQuantity;
import data.calculation.ExtractDigitsFromLevelStrings;
import mech.Constants;
import visual.Cell.CellState;

@SuppressWarnings("serial")
public class GameFieldPanel extends JPanel implements Constants{
	
	private MainPanel mainPanel;
	private GameFieldParameters gfp = new GameFieldParameters();
	private ArrayList<String> levelStrings = new ArrayList<>();
	private Glass glass;
	
	public GameFieldPanel(MainPanel mainPanel, ArrayList<String> levelStrings) {
		this.mainPanel = mainPanel;
		this.levelStrings = levelStrings;
		setLayout(null);
		setBackground(Color.WHITE);
			calculateTotalCellsQuantity(levelStrings);
			calculateCellSize();
			calculateGameFieldBounds();
		findDigitsStartPoint();
		extractDigitsFromLevelStrings(levelStrings);
			drawHorizontalDigits(levelStrings);
			drawVerticalDigits(levelStrings);
			drawFieldCells();
		createGlassPane();
	}
	
	/**
	 * Field cells with digit cells.
	 * @param levelStrings
	 */
	private void calculateTotalCellsQuantity(ArrayList<String> levelStrings) {
		CalculateTotalCellsQuantity.calculate(levelStrings, gfp);
	}

	private void calculateCellSize() {
		CalculateCellSize.calculate(gfp);
	}

	private void calculateGameFieldBounds() {
		setBounds(CalculateGameFieldBounds.calculate(gfp));
	}

	private void findDigitsStartPoint() {
		CalculateDigitsStartPoint.calculate(gfp);
	}

	private void extractDigitsFromLevelStrings(ArrayList<String> levelStrings) {
		ExtractDigitsFromLevelStrings.extract(levelStrings, gfp);
	}

	private void drawHorizontalDigits(ArrayList<String> levelStrings) {
		for (int i = 0; i < gfp.maxHorDigits; i++) {
			for (int j = 1; j <= gfp.vertQ; j++) {
				Cell cell = new Cell(i, j, gfp, HORIZONTAL_CELL);
				add(cell);
			}
		}
	}

	private void drawVerticalDigits(ArrayList<String> levelStrings) {
		for (int i = 1; i <= gfp.horQ; i++) {
			for (int j = 0; j < gfp.maxVertDigits; j++){
				Cell cell = new Cell(i, j, gfp, VERTICAL_CELL);
				add(cell);
			}
		}
	}
	
	private void drawFieldCells() {
		for (int i = 1; i <= gfp.horQ; i++) {
			for (int j = 1; j <= gfp.vertQ; j++) {
				Cell cell = new Cell(i, j, gfp, FIELD_CELL);
				add(cell);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		super.paint(g);
		// top-left Light-Gray corner
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, gfp.cellSize*gfp.maxHorDigits, gfp.cellSize*gfp.maxVertDigits);
	}

	private void createGlassPane() {
		mainPanel.removeGlass();
		glass = new Glass(this, gfp);
		mainPanel.addComponentToLayer(glass, 1);
	}
	
	public Glass getGlassPane(){
		return glass;
	}

	public void clearAllTempDots() {
		for (Component c: getComponents()) {
			if (c instanceof Cell){
				Cell cell = (Cell) c;
				if (cell.isTempDotted()){
					cell.cellState = CellState.CLEARED;
					cell.repaint();
				}
			}
		}
	}
	
	public MainPanel getMainPanel(){
		return mainPanel;
	}
	
	public ArrayList<String> getLevelStrings(){
		return levelStrings;
	}

	public void loadCellsFromSaveFile(ArrayList<String> cellStates) {
		ArrayList<String[]> parsedCellStates = new ArrayList<>();
		fillParseCellStates(parsedCellStates, cellStates);
		ExtractCellStatesOnGameFieldCells(parsedCellStates);
		mainPanel.getButtonPanel().enableResetButton();
		mainPanel.getButtonPanel().enableDeleteButton();
	}

	private void fillParseCellStates(ArrayList<String[]> parsedCellStates, ArrayList<String> cellStates) {
		String string1 = "";
		for (int i = 0; i < cellStates.size(); i++) {
//			String side = cellStates.get(i);
			switch (cellStates.get(i)) {
				case HORIZONTAL_CELL: {string1 = HORIZONTAL_CELL; continue;}
				case VERTICAL_CELL: {string1 = VERTICAL_CELL; continue;}
				case FIELD_CELL: {string1 = FIELD_CELL; continue;}
			}
			String string2 = cellStates.get(i);
			i++;
			String string3 = cellStates.get(i);
			String[] newCellState = {string1, string2, string3};
			parsedCellStates.add(newCellState);
		}
	}

	private void ExtractCellStatesOnGameFieldCells(ArrayList<String[]> parsedCellStates) {
		for (String[] strings : parsedCellStates) {
			for (Component c : getComponents()) {
				if (c instanceof Cell){
					Cell cell = (Cell) c;
					if (cell.getName().equals(strings[0]) &&
						cell.getPointIjString().equals(strings[1])){
						cell.cellState = CellState.valueOf(strings[2]);
					}
				}
			}	
		}
	}
}
