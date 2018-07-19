package mech;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
public interface Constants {

	String HORIZONTAL_CELL = "Horizontal cell";
	String VERTICAL_CELL = "Vertical cell";
	String FIELD_CELL = "Field cell";
	
	Color FILLED_COLOR = Color.DARK_GRAY;
	Color CROSSED_COLOR = Color.LIGHT_GRAY;
	int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	Dimension WINDOW = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	
	int BUTTON_WIDTH = WINDOW_HEIGHT/5;
	int BUTTON_HEIGHT = BUTTON_WIDTH /4;
	
	int FIELD_X_CENTER = BUTTON_WIDTH + (WINDOW_WIDTH-BUTTON_WIDTH)/2;
	
	Dimension BUTTON_DIMENSION = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	Dimension LABEL_DIMENSION = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT*2);
	Dimension BUTTON_PANEL_DIMENSION = new Dimension(BUTTON_WIDTH, WINDOW_HEIGHT);
	
	Point[] BUTTON_PANEL_ELEMENTS_POSITION = {
								new Point(0,0),
								new Point(0, BUTTON_HEIGHT),
								new Point(0, BUTTON_HEIGHT*2),
								new Point(0, BUTTON_HEIGHT*3),
								new Point(0, BUTTON_HEIGHT*4),
								new Point(0, BUTTON_HEIGHT*5),
								new Point(0, BUTTON_HEIGHT*7),
								new Point(0, BUTTON_HEIGHT*9),
								new Point(0, BUTTON_HEIGHT*11)
								};
	
	String[] BUTTON_PANEL_STRINGS = {	"Import puzzles",
										"Open random puzzle", 
										"Reset puzzle", 
										"Delete puzzle",
										"Do screenshot",
										"<html><center>LMB<br>toggle the fill</center></html>",
										"<html><center>RMB<br>toggle empty dot</center></html>",
										"<html><center>Shift+LMB<br>toggle temporary dot</center></html>",
										"<html><center>Ctrl+LMB<br>delete temp dots</center></html>"};
}
