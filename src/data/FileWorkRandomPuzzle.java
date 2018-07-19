package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class FileWorkRandomPuzzle {
	
	private File levelsFile;
	private ArrayList<String> randomLevelStrings = new ArrayList<>();

	public FileWorkRandomPuzzle(FileWork fileWork) throws IOException, IndexOutOfBoundsException{
		levelsFile = fileWork.getLevelsFile();
		if (!fileWork.isLevelsFileExists()){
			JOptionPane.showMessageDialog(fileWork.getMainPanl(), "Files with levels did not exist");
			fileWork.getMainPanl().getButtonPanel().disableNewRandomLevelButton();
			fileWork.getMainPanl().getButtonPanel().disableResetButton();
			fileWork.getMainPanl().getButtonPanel().disableDeleteButton();
		}
		else{
			ArrayList<String> hashes = extractHashesFromLevelsFile();
			String randomHash = chooseRandomHash(hashes);
			extractLevelStringsByRandomHash(randomHash);
		}
	}

	private ArrayList<String> extractHashesFromLevelsFile() throws IOException {
		ArrayList<String> hashes = new ArrayList<>();
		BufferedReader in = new BufferedReader(new FileReader(levelsFile));
		Pattern p = Pattern.compile("HASH .*");
		String nextLine = in.readLine();
		while (nextLine != null) {
			Matcher m = p.matcher(nextLine);
			if (m.matches())
				hashes.add(nextLine.substring(5, nextLine.length()));	// without "HASH "
			nextLine = in.readLine();
		}
		in.close();
		return hashes;
	}
	
	private String chooseRandomHash(ArrayList<String> hashes) throws IndexOutOfBoundsException{
		int r = (int)(Math.random()*hashes.size());
		return hashes.get(r);
	}

	private void extractLevelStringsByRandomHash(String randomHash) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(levelsFile));
		String nextLine = in.readLine();
		
		while (!nextLine.equals("HASH "+randomHash)) {	// looking for randomHash string in levelFile
			nextLine = in.readLine();
		}
		
		while (!nextLine.equals("ENDLEVEL")) {
			randomLevelStrings.add(nextLine);
			nextLine = in.readLine();
		}
		randomLevelStrings.add("ENDLEVEL");
		in.close();
	}

	public ArrayList<String> getRandomLevelStrings(){
		return randomLevelStrings;
	}
}
