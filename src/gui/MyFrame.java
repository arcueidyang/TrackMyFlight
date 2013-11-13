package gui;

import gui.canvas.PredictPanel;
import gui.menu.MyMenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MyFrame {

	public static final String APPLICATION_NAME = "WhetherMyFlightWillDelay";
	private JFrame myFrame;
	private JMenuBar myMenuBar;
	private PredictPanel myPanel;
	
	public MyFrame() {  	
		myFrame = new JFrame(APPLICATION_NAME);
		myPanel = new PredictPanel();
		myMenuBar = new MyMenuBar();
		myFrame.add(myPanel.getPanel());
		myFrame.setJMenuBar(myMenuBar);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(500, 400);
		myFrame.setResizable(false);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
	}
}
