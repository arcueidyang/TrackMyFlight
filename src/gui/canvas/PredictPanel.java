package gui.canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import db.Predictor;

/**
 * 
 * @author Richard Yang
 *
 */

public class PredictPanel {

	
	private JPanel myPanel;
	private JPanel myButtonPanel;
	private JButton myInquiryButton;
	private JButton myPredictButton;
	private JButton myUpdateButton;
	private JButton myClearButton;
	
	private Console myConsole;
    private Predictor myPredictor;
    
    private Pattern spacePattern;
	
    public PredictPanel() {
    	myPredictor = Predictor.getInstance();
    	myPanel = new JPanel();
    	myPanel.setLayout(new BorderLayout());
        createButtonPanel();
    	
    	createConsole();
    	spacePattern = Pattern.compile("\\s+");
    }
    
    private void createButtonPanel() {
    	myButtonPanel = new JPanel();
    	myButtonPanel.setSize(300,100);
    	createInquiryButton();
    	createPredictButton();
    	createUpdateButton();
    	createClearButton();
    	myPanel.add(myButtonPanel, BorderLayout.SOUTH);
    }
    
    private void createConsole() {
    	myConsole = Console.getInstance();
    	myConsole.setEditable(false);
    	myConsole.setLineWrap(true);
    	myConsole.setVisible(true);
    	
    	JScrollPane scrollPane = new JScrollPane(myConsole);
    	 
    	myPanel.add(scrollPane);
       	   
    }
    
    private void createInquiryButton() {
    	myInquiryButton = new JButton("Inquiry");
    	myInquiryButton.setMnemonic(KeyEvent.VK_I);

    	
    	ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getPopupInquiryDialog();
			}
    	};
    	myInquiryButton.addActionListener(listener);
        myButtonPanel.add(myInquiryButton);
    }
    
    private void createPredictButton() {
    	myPredictButton = new JButton("Predict");
    	myPredictButton.setMnemonic(KeyEvent.VK_P);
    	ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getPopupPredictDialog();
			}
    		
    	};
    	myPredictButton.addActionListener(listener);
    	myButtonPanel.add(myPredictButton);
    }
    
    private void createClearButton() {
    	myClearButton = new JButton("Clean");
    	myClearButton.setMnemonic(KeyEvent.VK_C);
    	ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myConsole.setText("");
				
			}
    	};
    	myClearButton.addActionListener(listener);
    	myButtonPanel.add(myClearButton);
    }
    
    private void createUpdateButton() {
    	myUpdateButton = new JButton("Update");
    	myUpdateButton.setMnemonic(KeyEvent.VK_U);
    	ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getPopupUpdateDialog();
				
			}
    	};
    	myUpdateButton.addActionListener(listener);
    	myButtonPanel.add(myUpdateButton, BorderLayout.WEST);
    }
    
    public JPanel getPanel() {
    	return myPanel;
    }
    
	private JDialog getPopupInquiryDialog() {
		final JDialog dialog = new JDialog();			
		
		JButton confirmButton = new JButton("Confirm");
		JButton cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton, BorderLayout.EAST);
		buttonPanel.add(cancelButton, BorderLayout.WEST);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		
		final JTextArea textArea = addPopupComponent(dialog);		
		
		ActionListener confirmListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(myPredictor.isAuthorized()) {
					String flightNumber = textArea.getText();
					
					boolean isIncluded = myPredictor.containsFlight(flightNumber);
					if(isIncluded) {
						myConsole.append("--" + flightNumber + "-- is supported by our system\n");
					}else {
						myConsole.append("Sorry, --" + flightNumber + "-- is not supported by our system\n");
					}	
				}else {
					myConsole.append("Please log in first\n");
				}
						
			    dialog.dispose();
			}
		};

		confirmButton.addActionListener(confirmListener);

		ActionListener cancelListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}	
		};
		
		cancelButton.addActionListener(cancelListener);
		
	    return dialog;
	}
	
	private JDialog getPopupPredictDialog() {
		final JDialog dialog = new JDialog();			
		
		JButton confirmButton = new JButton("Confirm");
		JButton cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton, BorderLayout.EAST);
		buttonPanel.add(cancelButton, BorderLayout.WEST);
		
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		
		final JTextArea textArea = addPopupComponent(dialog);
		
		ActionListener confirmListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			    
				if(myPredictor.isAuthorized()) {
					String flightNumber = textArea.getText();
				    double delay = myPredictor.predictDelay(flightNumber);
				    dialog.dispose();
				    myConsole.append("the delay of --" + flightNumber + "-- today will be " + delay);
				}else {
					myConsole.append("Please log in first\n");
				}
			}
		};

		confirmButton.addActionListener(confirmListener);

		ActionListener cancelListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}	
		};
		
		cancelButton.addActionListener(cancelListener);
		
	    return dialog;
	}
	
	private JDialog getPopupUpdateDialog() {
		final JDialog dialog = new JDialog();			
		
		JButton confirmButton = new JButton("Confirm");
		JButton cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(confirmButton, BorderLayout.EAST);
		buttonPanel.add(cancelButton, BorderLayout.WEST);
		
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		
		final JTextArea textArea = addPopupComponent(dialog);
		
		ActionListener confirmListener = new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent arg0) {
			   if(myPredictor.isAuthorized()) {
				   String delayStr = textArea.getText();
				   int response = JOptionPane.showOptionDialog(dialog, "The delay you entered is" + delayStr + ", do you want to upload this value?",
						    "Yes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				   if(response == 0) {
                        myConsole.append("upload delay " + delayStr + "\n");
                        String info = textArea.getText();
                        String[] splited = spacePattern.split(info);
                        String flightNumber = splited[0];
				        double delay = Double.parseDouble(splited[1]);        
				        myPredictor.updateDelay(flightNumber, delay);
				   }else if(response == 1) {
				        myConsole.append("give up upload\n");
				   }	
				
			   }else {
				   myConsole.append("Please log in first\n");
			   }
	            
	           dialog.dispose();
			}
		};

		confirmButton.addActionListener(confirmListener);

		ActionListener cancelListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}	
		};
		
		cancelButton.addActionListener(cancelListener);
		
	    return dialog;
	}
	
	private JTextArea addPopupComponent(JDialog myDialog) {
		JLabel label = new JLabel("Please enter your flight number", JLabel.CENTER);
		label.setFont(new Font("Dialog",1,15));
		myDialog.add(label, BorderLayout.NORTH);
	
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		final JPopupMenu menu = PredictPanel.createPopupMenu(textArea);
		
		MouseListener mouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3) {
					menu.setLocation(arg0.getXOnScreen(), arg0.getYOnScreen());
					menu.setVisible(true);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			    //do nothing
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				//do nothing	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				//do nothing
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//do nothing
			}
		};
		
		textArea.addMouseListener(mouseListener);
		
		myDialog.add(scrollPane, BorderLayout.CENTER);
		
		myDialog.setSize(new Dimension(300,300));
		myDialog.setResizable(false);
		myDialog.setLocationRelativeTo(null);
		
		myDialog.setVisible(true);
        
		return textArea;
	}
	
	public static JPopupMenu createPopupMenu(final JTextArea textArea) {
		final JPopupMenu rootMenu = new JPopupMenu("Right click menu"); 
	    JMenuItem pasteItem = new JMenuItem("Paste");
	    pasteItem.setMnemonic(KeyEvent.VK_P);
	    ActionListener pasteListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				Transferable tran = null;
				
				tran = clipboard.getContents(null);
				
				String content = null;
				
				if(tran != null && tran.isDataFlavorSupported(DataFlavor.stringFlavor)){
				    try {
					     content = (String)tran.getTransferData(DataFlavor.stringFlavor);
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				textArea.append(content);
				rootMenu.setVisible(false);
			}
	    	
	    };
	    pasteItem.addActionListener(pasteListener);
	    rootMenu.add(pasteItem);
	    
	    JMenuItem clearItem = new JMenuItem("Clear");
	    clearItem.setMnemonic(KeyEvent.VK_C);
	    ActionListener clearListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
	    };
	    clearItem.addActionListener(clearListener);
	    rootMenu.add(clearItem);
	    
	    return rootMenu;
	}
	
}
