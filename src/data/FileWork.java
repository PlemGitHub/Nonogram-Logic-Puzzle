package data;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import mech.Constants;
import visual.Cell;
import visual.GameFieldPanel;
import visual.Glass;
import visual.MainPanel;

public class FileWork implements Constants{
	private MainPanel mainPanel;
	private File levelsFile;
	private File saveFile;
	private File screenshotFile;
	private ArrayList<String> currentLevelStrings;
	
	public FileWork(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		defineLevelsFilePath();
		defineSaveFilePath();
	}
	
	public MainPanel getMainPanl(){
		return mainPanel;
	}

	private void defineLevelsFilePath() {
		String tmp = System.getenv("APPDATA");
		File plemFolder = new File(tmp+"/PlemCo");
		if (!plemFolder.exists())
			plemFolder.mkdir();
		File gameFolder = new File(plemFolder+"/Nonogram Logic Puzzle");
		if (!gameFolder.exists())
			gameFolder.mkdir();
		levelsFile = new File(gameFolder+"/levels.nlp");
	}

	private void defineSaveFilePath() {
		String tmp = System.getenv("APPDATA");
		File plemFolder = new File(tmp+"/PlemCo");
		if (!plemFolder.exists())
			plemFolder.mkdir();
		File gameFolder = new File(plemFolder+"/Nonogram Logic Puzzle");
		if (!gameFolder.exists())
			gameFolder.mkdir();
		saveFile = new File(gameFolder+"/save.snp");
	}

	private void defineScreenshotFilePath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		File plemFolder = new File(home+"/Nonogram Logic Puzzle");
		if (!plemFolder.exists())
			plemFolder.mkdir();
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String currentDateString = "/"+sdf.format(myDate);
		screenshotFile = new File(plemFolder+currentDateString+".jpg");
	}

	public boolean isLevelsFileExists(){
		if (levelsFile.exists())
			return true;
		else
			return false;
	}

	public boolean isSaveFileExists(){
		if (saveFile.exists())
			return true;
		else
			return false;
	}
	
	public File getLevelsFile(){
		return levelsFile;
	}
	
	public void importNewPack(File selectedFile) throws IOException{
		new FileWorkImportPuzzles(this, selectedFile);
	}
	
	public void openRandomLevel() throws IOException, IndexOutOfBoundsException{
		FileWorkRandomPuzzle fileWorkRandomPuzzle = new FileWorkRandomPuzzle(this);
		if (isLevelsFileExists()){
			currentLevelStrings = fileWorkRandomPuzzle.getRandomLevelStrings();
			mainPanel.createNewGameField(currentLevelStrings);
			getMainPanl().getButtonPanel().enableResetButton();
			getMainPanl().getButtonPanel().enableDeleteButton();
			deleteSaveFile();
		}
	}
	
	public void resetCurrentLevel(){
		mainPanel.createNewGameField(currentLevelStrings);
	}

	public void deleteCurrentLevel() throws IOException {
		ArrayList<String> newFileStrings = new ArrayList<>();
		BufferedReader in = new BufferedReader(new FileReader(levelsFile));
		String nextLine = in.readLine();
		while (nextLine != null) {
			if (nextLine.equals(currentLevelStrings.get(0))){
				while (!nextLine.equals("ENDLEVEL")) {
					nextLine = in.readLine();
				}
				nextLine = in.readLine();
			}else{
				newFileStrings.add(nextLine);
				nextLine = in.readLine();
			}
		}
		in.close();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(levelsFile));
		for (String string : newFileStrings) {
			out.write(string);
			out.write(System.lineSeparator());
		}
		out.close();
		deleteSaveFile();
	}
	
	private void deleteSaveFile(){
		if (isSaveFileExists()){
			saveFile.delete();
		}
	}

	public void saveLevel(GameFieldPanel gf) throws IOException {
		defineSaveFilePath();
		if (!isSaveFileExists())
			saveFile.createNewFile();
		ArrayList<String> levelStrings = gf.getLevelStrings();
		prepareLevelStringsToSave(gf, levelStrings);
		
		BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
		for (String string : levelStrings) {
			
			out.write(string);
			out.write(System.lineSeparator());
		}
		out.close();
	}

	private void prepareLevelStringsToSave(GameFieldPanel gf, ArrayList<String> extendedLevelStrings) {
		extendedLevelStrings.add(HORIZONTAL_CELL);
		addActualCellsToLevelStrings(gf, extendedLevelStrings, HORIZONTAL_CELL);
		extendedLevelStrings.add(VERTICAL_CELL);
		addActualCellsToLevelStrings(gf, extendedLevelStrings, VERTICAL_CELL);
		extendedLevelStrings.add(FIELD_CELL);
		addActualCellsToLevelStrings(gf, extendedLevelStrings, FIELD_CELL);
	}
	
		private void addActualCellsToLevelStrings(GameFieldPanel gf, ArrayList<String> levelStrings, String name){
			for (Component c : gf.getComponents()) {
				if (c instanceof Cell){
					Cell cell = (Cell) c;
					if (cell.getName().equals(name)){
						levelStrings.add(cell.getPointIjString());
						levelStrings.add(cell.cellState.toString());
					}
				}
			}
		}

	public void loadGameIfSaveFileExists() throws IOException {
		if (isSaveFileExists()){
			BufferedReader in = new BufferedReader(new FileReader(saveFile));
			ArrayList<String> extendedLevelStrings = new ArrayList<>();
			String nextString = in.readLine();
			while (nextString != null) {
				extendedLevelStrings.add(nextString);
				nextString = in.readLine();
			}
			in.close();
			ArrayList<String> levelStrings = new ArrayList<>();
			ArrayList<String> cellStates = new ArrayList<>();
			int i = 0;
			do {
				nextString = extendedLevelStrings.get(i);
				i++;
				levelStrings.add(nextString);
			} while (!nextString.equals("ENDLEVEL"));
			do {
				nextString = extendedLevelStrings.get(i);
				i++;
				cellStates.add(nextString);
			} while (i != extendedLevelStrings.size());
			currentLevelStrings = levelStrings;
			mainPanel.createNewGameField(levelStrings);
			mainPanel.loadCellsFromSaveFile(cellStates);
		}
	}
	
	public void doScreenshot(GameFieldPanel gameFieldPanel) throws IOException{
		defineScreenshotFilePath();
		int width = gameFieldPanel.getWidth();
		int height = gameFieldPanel.getHeight();
		BufferedImage firstImage = getGameFieldPanelImage(gameFieldPanel);
		BufferedImage secondImage = getThickLinesImage(gameFieldPanel);
		
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics combinedG = combined.createGraphics();
		combinedG.drawImage(firstImage, 0, 0, null);
		combinedG.drawImage(secondImage, 0, 0, null);
		ImageIO.write(combined, "PNG", screenshotFile);
	}

		private BufferedImage getGameFieldPanelImage(GameFieldPanel gameFieldPanel) {
			int width = gameFieldPanel.getWidth();
			int height = gameFieldPanel.getHeight();
			BufferedImage firstImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics firstG = firstImage.createGraphics();
			gameFieldPanel.paint(firstG);
			return firstImage;
		}
	
		private BufferedImage getThickLinesImage(GameFieldPanel gameFieldPanel) {
			Glass glass = gameFieldPanel.getGlassPane();
			int width = glass.getWidth();
			int height = glass.getHeight();
			BufferedImage secondImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics secondG = secondImage.createGraphics();
			glass.paint(secondG);
			return secondImage;
		}
}
