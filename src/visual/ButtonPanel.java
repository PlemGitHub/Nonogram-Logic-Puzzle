package visual;

import java.awt.Color;
import javax.swing.Action;
import javax.swing.JPanel;
import mech.ButtonActions;
import mech.Constants;
import visual.myComponents.MyButton;
import visual.myComponents.MyLabel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements Constants{
	
	private MainPanel mainPanel;
	
	private MyButton	importBtn 			= new MyButton(), 	// 0
						newRandomLevelBtn 	= new MyButton(), 	// 1
						resetBtn 			= new MyButton(), 	// 2
						deleteBtn 			= new MyButton(),	// 3
						scrennshotBtn		= new MyButton();	// 4
	private MyLabel	toggleFillLbl		= new MyLabel(),			// 5
					toggleDotLbl 		= new MyLabel(),			// 6
					toggleTempDotLbl 	= new MyLabel(),			// 7
					removeTempDotsLbl 	= new MyLabel();			// 8
	private MyButton[] buttonPanelButtons = {importBtn, newRandomLevelBtn, resetBtn, deleteBtn, scrennshotBtn};
	private MyLabel[] buttonPanelLabels = {toggleFillLbl, toggleDotLbl, toggleTempDotLbl, removeTempDotsLbl};
	private Action[] buttonActions = new ButtonActions(this).getActions();
	
	public ButtonPanel(MainPanel mainPanel){
		this.mainPanel = mainPanel;
		setLayout(null);
		setSize(BUTTON_PANEL_DIMENSION);
		setBackground(Color.ORANGE);
		
		addButtons();
	}

	private void addButtons() {
		for (int i = 0; i < buttonPanelButtons.length; i++) {
			MyButton btn = buttonPanelButtons[i];
			btn.setAction(buttonActions[i]);
			btn.setText(BUTTON_PANEL_STRINGS[i]);
			btn.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[i]);
			add(btn);
		}
		
		for (int i = 0; i < buttonPanelLabels.length; i++) {
			int btnGap = buttonPanelButtons.length;
			MyLabel lbl = buttonPanelLabels[i];
			lbl.setHeightK(0.9);
			lbl.setWidthK(0.9);
			lbl.setText(BUTTON_PANEL_STRINGS[i+btnGap]);
			lbl.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[i+btnGap]);
			add(lbl);
		}
		
		resetBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
	}
	
	public MainPanel getMainPanel(){
		return mainPanel;
	}
	
	public void enableResetButton(){
		resetBtn.setEnabled(true);
	}
	
	public void disableResetButton(){
		resetBtn.setEnabled(false);
	}
	
	public void enableDeleteButton(){
		deleteBtn.setEnabled(true);
	}
	
	public void disableDeleteButton(){
		deleteBtn.setEnabled(false);
	}
	
	public void enableNewRandomLevelButton(){
		newRandomLevelBtn.setEnabled(true);
	}
	
	public void disableNewRandomLevelButton(){
		newRandomLevelBtn.setEnabled(false);
	}
}
