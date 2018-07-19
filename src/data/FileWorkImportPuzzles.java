package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

public class FileWorkImportPuzzles {
	
	private File levelsFile;

	public FileWorkImportPuzzles(FileWork fileWork, File selectedFile) throws IOException {
		levelsFile = fileWork.getLevelsFile();
		if (!fileWork.isLevelsFileExists())
			levelsFile.createNewFile();
		BufferedReader in = new BufferedReader(new FileReader(selectedFile));
		ArrayList<String> levelStrings = new ArrayList<>();
		String nextString = in.readLine();
		while (nextString != null) {
			levelStrings.add(nextString);
			if (nextString.equals("ENDLEVEL")){
				String levelHash = calculateMD5toFindDuplicate(levelStrings);
				if (levelHash != null)
					finishCurrentLevelImport(levelHash, levelStrings);
				levelStrings = new ArrayList<>();
			}
			nextString = in.readLine();
		}
		in.close();
	}

	private void finishCurrentLevelImport(String levelHash, ArrayList<String> levelStrings) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(levelsFile, true));
		out.append("HASH "+levelHash+System.lineSeparator());
		for (String nextString : levelStrings)
			out.append(nextString+System.lineSeparator());
		out.close();
	}

	/**
	 * Calculates MD5 for all new level's strings combined in one long string. <br>
	 * Checks levelsFile to find duplicate hash
	 * @param levelStrings - ArrayList of all new level's strings.
	 * @return <b>Hash</b> string - if no duplicates found. <br> <b>Null</b> - if level duplicate found.
	 * @throws IOException
	 */
	private String calculateMD5toFindDuplicate(ArrayList<String> levelStrings) throws IOException {
		String levelString = levelStrings.toString();
		String levelHash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(levelString.getBytes());
			byte[] digest = md.digest();
			levelHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		BufferedReader in = new BufferedReader(new FileReader(levelsFile));
		String nextLine = in.readLine();
		while (nextLine != null) {
			if (nextLine.equals("HASH "+levelHash)){
				in.close();
				return null;
			}
			nextLine = in.readLine();
		}
		in.close();
		return levelHash;
	}

}
