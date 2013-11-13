package gui.menu;

import gui.canvas.Console;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.Predictor;

public class AirportMenu extends JMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4088287906074125158L;

	private JMenuItem asDepartItem;
	private JMenuItem asDestinationItem;
	private JMenuItem averageDelayItem;
	private Console myConsole;
	private Predictor myPredictor;
	
	public AirportMenu() {
	    super("Airport");
	    myConsole = Console.getInstance();
	    myPredictor = Predictor.getInstance();
	    createAsDepartItem();
	    createAsDestinationItem();
	    createAverageDelayItem();
	}
	
	private void createAsDepartItem() {
		asDepartItem = new JMenuItem("As Depart");
		asDepartItem.setMnemonic(KeyEvent.VK_P);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createDepartPopupDialog();
			}
			
		};
		asDepartItem.addActionListener(listener);
		add(asDepartItem);
	}
	
	private void createAsDestinationItem() {
		asDestinationItem = new JMenuItem("As Destination");
	    asDestinationItem.setMnemonic(KeyEvent.VK_S);
	    ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createDestinationPopupDialog();
			}
	    	
	    };
	    asDestinationItem.addActionListener(listener);
	    add(asDestinationItem);
	}
	
	private void createAverageDelayItem() {
		averageDelayItem = new JMenuItem("Average Delay");
	    averageDelayItem.setMnemonic(KeyEvent.VK_A);
	    ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createAverageDelayPopupDialog();
			}
	    	
	    };
	    averageDelayItem.addActionListener(listener);
	    add(averageDelayItem);
	}
	
	private void createDepartPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Flight Depart from this Airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Airport Code");
		airportLabel.setBounds(20, 50, 100, 30);
		myPanel.add(airportLabel);
		
		final JTextField airportField = new JTextField();
		airportField.setBounds(180, 50, 150, 30);
		myPanel.add(airportField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String airportStr = airportField.getText();
				List<String> flights = myPredictor.getFlightByDepart(airportStr);
				if(flights == null) {
					myConsole.append("Cannot find any flight depart from " + airportStr + "\n");
				}else {
				    myConsole.append("Flights depart from " + airportStr + " are : \n");
				    for(String str: flights) {
				    	myConsole.append(str + "\n");
				    }
				}
			    dialog.dispose();
			}
			
		};
		confirmButton.addActionListener(registerListener);
		myPanel.add(confirmButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(230, 110, 90, 30);
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
			
		};
		cancelButton.addActionListener(cancelListener);
		myPanel.add(cancelButton);
		
		dialog.setContentPane(myPanel);
	    dialog.setSize(new Dimension(400,200));
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);		
		dialog.setVisible(true);
	}
	
	private void createDestinationPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Flight Launch at this airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Airport Code");
		airportLabel.setBounds(20, 50, 100, 30);
		myPanel.add(airportLabel);
		
		final JTextField airportField = new JTextField();
		airportField.setBounds(180, 50, 150, 30);
		myPanel.add(airportField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String airportStr = airportField.getText();
				List<String> flights = myPredictor.getFlightByDestination(airportStr);
				if(flights == null) {
					myConsole.append("Cannot find any flight destinated at " + airportStr + "\n");
				}else {
				    myConsole.append("Flights destinated at " + airportStr + " are : \n");
				    for(String str: flights) {
				    	myConsole.append(str + "\n");
				    }
				}
			    dialog.dispose();
			}
			
		};
		confirmButton.addActionListener(registerListener);
		myPanel.add(confirmButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(230, 110, 90, 30);
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
			
		};
		cancelButton.addActionListener(cancelListener);
		myPanel.add(cancelButton);
		
		dialog.setContentPane(myPanel);
	    dialog.setSize(new Dimension(400,200));
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);		
		dialog.setVisible(true);
	}
	
	private void createAverageDelayPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Flight Launch at this airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Airport Code");
		airportLabel.setBounds(20, 50, 100, 30);
		myPanel.add(airportLabel);
		
		final JTextField airportField = new JTextField();
		airportField.setBounds(180, 50, 150, 30);
		myPanel.add(airportField);
		
		JLabel dateLabel = new JLabel("Airport Code");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(180, 50, 150, 30);
		myPanel.add(dateField);
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		        //TODO
				dialog.dispose();
			}
			
		};
		confirmButton.addActionListener(registerListener);
		myPanel.add(confirmButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(230, 110, 90, 30);
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
			
		};
		cancelButton.addActionListener(cancelListener);
		myPanel.add(cancelButton);
		
		dialog.setContentPane(myPanel);
	    dialog.setSize(new Dimension(400,200));
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);		
		dialog.setVisible(true);
	}
}
