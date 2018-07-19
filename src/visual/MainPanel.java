package visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import data.FileWork;
import mech.Constants;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Constants, WindowListener{
	
	private final String exitStr = "exitStr";
	
	private MainPanel mainPanel = this;
	private GameFieldPanel gameFieldPanel;
	private ButtonPanel buttonPanel;
	private Screen screen;
	private FileWork fileWork = new FileWork(this);
	
	public MainPanel(Screen screen) {
		this.screen = screen;
		setLayout(null);
		setBackground(Color.WHITE);
		
		buttonPanel = new ButtonPanel(this);
		add(buttonPanel);
				
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), exitStr);
		getActionMap().put(exitStr, exitAction);
		
		loadGameIfSaveFileExists();
	}

	private void loadGameIfSaveFileExists() {
		try {
			fileWork.loadGameIfSaveFileExists();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Action exitAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			windowClosing(null);
		}
	};
	
	public FileWork getFileWork(){
		return fileWork;
	}
	
	public void createNewGameField(ArrayList<String> levelStrings){
		for (Component c: getComponents()) {
			if (c instanceof GameFieldPanel)
				remove(c);
		}
		gameFieldPanel = new GameFieldPanel(this, levelStrings);
		add(gameFieldPanel);
		repaint();
	}
	
	public void loadCellsFromSaveFile(ArrayList<String> cellStates){
		gameFieldPanel.loadCellsFromSaveFile(cellStates);
	}

	public void deleteGameField() {
		for (Component c : getComponents()) {
			if (c instanceof GameFieldPanel){
				remove(c);
				gameFieldPanel = null;
			}
		}
		repaint();
	}
	
	public ButtonPanel getButtonPanel(){
		return buttonPanel;
	}
	
	public boolean isGameFieldPanelEmpty(){
		if (gameFieldPanel == null)
			return true;
		else
			return false;
	}
	
	public void addComponentToLayer(JComponent c, int i){
		screen.addComponentToLayer(c, i);
	}
	
	public void removeGlass(){
		screen.removeGlass();
	}
	
	public GameFieldPanel getGameFieldPanel(){
		return gameFieldPanel;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (gameFieldPanel != null){
			int answer = JOptionPane.showConfirmDialog(mainPanel, "Save progress before exit?", "Save progress?", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch (answer) {
			case JOptionPane.YES_OPTION: 
				try {
					fileWork.saveLevel(gameFieldPanel);
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case JOptionPane.NO_OPTION:
				System.exit(0);
				break;
			}
		}else
			System.exit(0);			
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}