package mech;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import visual.ButtonPanel;
import visual.GameFieldPanel;

public class ButtonActions {

	ButtonPanel corePanel;
	
	public ButtonActions(ButtonPanel corePanel) {
		this.corePanel = corePanel;
	}
	
	public Action[] getActions(){
		Action[] actions = {importAction, randomAction, resetAction, deleteAction, screenshotAction};
		return actions;
	}
	
	@SuppressWarnings("serial")
	private Action importAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile()); // starting directory
			fileChooser.setFileFilter(new FileNameExtensionFilter("NLP file with puzzles", "nlp")); // file extension filter
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	// only files can be selected
			int answer = fileChooser.showDialog(corePanel, "Choose text file with levels");
			if (answer == JFileChooser.APPROVE_OPTION){
				try {
					corePanel.getMainPanel().getFileWork().importNewPack(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(corePanel.getMainPanel(), "Level import complete", 
						"Success", JOptionPane.INFORMATION_MESSAGE);
				corePanel.enableNewRandomLevelButton();
			}
		}
	};
	
	@SuppressWarnings("serial")
	private Action randomAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (corePanel.getMainPanel().isGameFieldPanelEmpty())
				doImport();
			else{
				int answer = JOptionPane.showConfirmDialog(corePanel.getMainPanel(), 
						"Open new level? Current progress will be lost.", "Open new level?", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION)
					doImport();
			}
		}
		
		private void doImport(){
			try {
				corePanel.getMainPanel().getFileWork().openRandomLevel();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(corePanel.getMainPanel(), "Something wrong with level file (empty or damaged)", 
						"Error", JOptionPane.ERROR_MESSAGE);
				corePanel.disableNewRandomLevelButton();
			};
		}
	};
	
	@SuppressWarnings("serial")
	private Action resetAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int answer = JOptionPane.showConfirmDialog(corePanel.getMainPanel(), "Are you sure you want to reset current level?", 
					"Reset level?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (answer == JOptionPane.YES_OPTION)
				corePanel.getMainPanel().getFileWork().resetCurrentLevel();
		}
	};
	
	@SuppressWarnings("serial")
	private Action deleteAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int answer = JOptionPane.showConfirmDialog(corePanel.getMainPanel(), "Confirm to delete current level from file.", 
					"Delete level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (answer == JOptionPane.YES_OPTION){
				try {
					corePanel.getMainPanel().getFileWork().deleteCurrentLevel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				corePanel.getMainPanel().deleteGameField();
				corePanel.getMainPanel().removeGlass();
				corePanel.disableResetButton();
				corePanel.disableDeleteButton();
			}
		}
	};
	
	@SuppressWarnings("serial")
	private Action screenshotAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameFieldPanel gameFieldPanel = corePanel.getMainPanel().getGameFieldPanel();
			if (gameFieldPanel != null){
				try {
					corePanel.getMainPanel().getFileWork().doScreenshot(gameFieldPanel);
					JOptionPane.showMessageDialog(corePanel.getMainPanel(), "Screenshot saved in \"Nonogram Logic Puzzle\" folder "
							+ "at your Desktop", 
							"Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
}
