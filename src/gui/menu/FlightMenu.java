package gui.menu;

import gui.canvas.Console;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.Predictor;

public class FlightMenu extends JMenu {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2091164712728554036L;
	
	
	private JMenuItem departAirportItem;
	private JMenuItem destinationAirportItem;
	private JMenuItem averageDelayItem;
	
	private Predictor myPredictor;
	private Console myConsole;
	
	
	public FlightMenu() {
		super("Flight Info");
		setMnemonic(KeyEvent.VK_F);
		myPredictor = Predictor.getInstance();
		myConsole = Console.getInstance();
		createDepartAirportItem();
		createDestinationAirportItem();
		createAverageDelayItem();
	}
	
	private void createDepartAirportItem() {
		departAirportItem = new JMenuItem("Depart Airport");
		departAirportItem.setMnemonic(KeyEvent.VK_P);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createDepartAirportPopupDialog();
			}
			
		};
		departAirportItem.addActionListener(listener);
		add(departAirportItem);
	}
	
	private void createDestinationAirportItem() {
		destinationAirportItem = new JMenuItem("Destionation Airport");
		destinationAirportItem.setMnemonic(KeyEvent.VK_S);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createDestinationPopupDialog();
			}
			
		};
		destinationAirportItem.addActionListener(listener);
		add(destinationAirportItem);
	}
	
	private void createAverageDelayItem() {
		averageDelayItem = new JMenuItem("Average Delay");
		averageDelayItem.setMnemonic(KeyEvent.VK_A);
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    createAverageDelayPopupDialog();	
			}
		};
		averageDelayItem.addActionListener(listener);
		add(averageDelayItem);
		
	}
	
	private void createDepartAirportPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Departure Airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel flightLabel = new JLabel("Flight Number");
		flightLabel.setBounds(20, 50, 100, 30);
		myPanel.add(flightLabel);
		
		final JTextField flightField = new JTextField();
		flightField.setBounds(180, 50, 150, 30);
		myPanel.add(flightField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			   String flightNumber = flightField.getText();
			   String departAirport = myPredictor.getDepartAirport(flightNumber);
			   if(departAirport == null) {
				   myConsole.append("Cannot find any information in database on flight " + flightNumber);
			   }else {
				  myConsole.append("The depart airport of " + flightNumber + " is " + departAirport + "\n");    
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
        dialog.setTitle("Destination Airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel flightLabel = new JLabel("Flight Number");
		flightLabel.setBounds(20, 50, 100, 30);
		myPanel.add(flightLabel);
		
		final JTextField flightField = new JTextField();
		flightField.setBounds(180, 50, 150, 30);
		myPanel.add(flightField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String flightNumber = flightField.getText();
				String departAirport = myPredictor.getDestinationAirport(flightNumber);
				if(departAirport == null) {
				    myConsole.append("Cannot find any information in database on flight " + flightNumber);
				}else {
				    myConsole.append("The destination airport of " + flightNumber + " is " + departAirport + "\n");    
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
        dialog.setTitle("Destination Airport");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel flightLabel = new JLabel("Flight Number");
		flightLabel.setBounds(20, 50, 100, 30);
		myPanel.add(flightLabel);
		
		JLabel dateLabel = new JLabel("Flight Number");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField flightField = new JTextField();
		flightField.setBounds(180, 50, 150, 30);
		myPanel.add(flightField);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(180, 50, 150, 30);
		myPanel.add(dateField);
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(80, 110, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String flightNumber = flightField.getText();
				double averageDelay = myPredictor.getAverageDelayByFlight(flightNumber);
				if(averageDelay < 0) {
				    myConsole.append("Cannot find any information in database on flight " + flightNumber);
				}else {
				    myConsole.append("The average delay of " + flightNumber + " is " + Double.toString(averageDelay) + "\n");    
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
}
