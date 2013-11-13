package gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import db.Predictor;

/**
 * 
 * @author Richard Yang
 *
 */
public class MyMenuBar extends JMenuBar{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389051229821499750L;
	private JMenu fileMenu;
	private JMenu searchMenu;
	private JMenu helpMenu;
	
	
	public MyMenuBar() {
		super();
		createFileMenu();
		createSearchMenu();
		createHelpMenu();
	}
	
	private void createFileMenu() {
		fileMenu = new FileMenu();
		fileMenu.setMnemonic(KeyEvent.VK_F);
		add(fileMenu);
	}
	
	private void createSearchMenu() {
		searchMenu = new SearchMenu();
		searchMenu.setMnemonic(KeyEvent.VK_S);
		add(searchMenu);
	}
	
	private void createHelpMenu() {
		helpMenu = new AboutMenu();
		helpMenu.setMnemonic(KeyEvent.VK_H);
		add(helpMenu);
	}
	
}
