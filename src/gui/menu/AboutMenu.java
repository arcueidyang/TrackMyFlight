package gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * 
 * @author Richard Yang
 *
 */
public class AboutMenu extends JMenu{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2624212327174472951L;
	private JMenuItem aboutItem;
	private JMenuItem contactItem;
	
	public AboutMenu() {
		super("Help");
		createAboutItem();
		createContactItem();
	}
	
	private void createAboutItem() {
		aboutItem = new JMenuItem("About");
	    aboutItem.setMnemonic(KeyEvent.VK_A);
	    ActionListener aboutListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(aboutItem,  "Author : Yang", null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
	    };
	    aboutItem.addActionListener(aboutListener);
	    add(aboutItem);
	}
	
	private void createContactItem() {
		contactItem = new JMenuItem("Contact");
		contactItem.setMnemonic(KeyEvent.VK_C);
		ActionListener contactListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(contactItem, "if you have any questions or advices to this application\n "+ 
                        "please send an e-mail to \n" + "arcueidyang@gmail.com", null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		contactItem.addActionListener(contactListener);
		add(contactItem);
	}
	
}
