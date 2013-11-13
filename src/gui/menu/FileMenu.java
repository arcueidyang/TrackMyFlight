package gui.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.Predictor;

/**
 * 
 * @author Richard Yang
 *
 */
public class FileMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 401309302645382484L;
	
	
	private JMenuItem loginItem;
	private JMenuItem registerItem;
	private JMenuItem logoutItem;
	private Predictor myPredictor;
	
	public FileMenu() {
		super("File");
		myPredictor = Predictor.getInstance();
		createLoginItem();
		createRegisterItem();
	    createLogoutItem();
	}
	
	private void createLoginItem() {
		loginItem = new JMenuItem("Log in");
		loginItem.setMnemonic(KeyEvent.VK_I);
		
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createPopupDialog(false);
			}
		};
		loginItem.addActionListener(listener);
		add(loginItem);
	}
	
	private void createRegisterItem() {
		registerItem = new JMenuItem("Register");
		registerItem.setMnemonic(KeyEvent.VK_R);
		
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createPopupDialog(true);
			}
		};
		registerItem.addActionListener(listener);
		add(registerItem);
	}
	
	private void createLogoutItem() {
		logoutItem = new JMenuItem("Log out");
		logoutItem.setMnemonic(KeyEvent.VK_O);
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPredictor.setAuthorized();
			}
		};
		logoutItem.addActionListener(listener);
		add(logoutItem);
	}
	
	
	private void createPopupDialog(final boolean isRegister) {
		final JDialog dialog = new JDialog();
		if(isRegister) {
			dialog.setTitle("Register");	
		}else {
			dialog.setTitle("Log in");
		}
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(20, 20, 100, 30);
		myPanel.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(20, 50, 100, 30);
		myPanel.add(passwordLabel);
		
		final JTextField usernameField = new JTextField();
		usernameField.setBounds(110, 20, 150, 30);
		myPanel.add(usernameField);
		
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(110, 50, 150, 30);
		myPanel.add(passwordField);
		
		
		JButton registerButton = new JButton();
		if(isRegister) {
			registerButton.setText("Register");	
		}else {
			registerButton.setText("Log in");
		}
		registerButton.setBounds(50, 210, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(isRegister) {
					if(myPredictor.register(username, password)) {
						JOptionPane.showConfirmDialog(dialog,  "register success",
								null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
						dialog.dispose();
					}else {
						JOptionPane.showConfirmDialog(dialog,  "register failed, please try another username",
					 			null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
					    dialog.dispose();
					}	
				}else {
					if(myPredictor.checkUserAuth(username, password)) {
						JOptionPane.showConfirmDialog(dialog,  "log in success",
								null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
						myPredictor.setAuthorized();
						dialog.dispose();
					}else {
						JOptionPane.showConfirmDialog(dialog,  "log in failed, please check your password",
								null, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
					    dialog.dispose();
					}
				}
				
			}
			
		};
		registerButton.addActionListener(registerListener);
		myPanel.add(registerButton);
		
		
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(170, 210, 90, 30);
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
			
		};
		cancelButton.addActionListener(cancelListener);
		myPanel.add(cancelButton);
		
		dialog.setContentPane(myPanel);
	    dialog.setSize(new Dimension(300,300));
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		
		dialog.setVisible(true);
	}
	
}
