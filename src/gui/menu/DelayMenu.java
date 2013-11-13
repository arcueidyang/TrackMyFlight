package gui.menu;

import gui.canvas.Console;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.Predictor;

public class DelayMenu extends JMenu {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2015618166911174842L;
	
	private JMenuItem byFlightItem;
	private JMenuItem byAirportItem;
	private Predictor myPredictor;
	private Console myConsole;
	
	public DelayMenu() {
		super("Delay");
		this.setMnemonic(KeyEvent.VK_D);
		myPredictor = Predictor.getInstance();
		myConsole = Console.getInstance();
		createByFlightItem();
		createByAirportItem();
	}
	
	private void createByFlightItem() {
		byFlightItem = new JMenuItem("ByFlight");
		byFlightItem.setMnemonic(KeyEvent.VK_F);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createByFlightPopupDialog();
			}
			
		};
		byFlightItem.addActionListener(listener);
		add(byFlightItem);
	}
	
	private void createByAirportItem() {
		byAirportItem = new JMenuItem("ByAirport");
		byAirportItem.setMnemonic(KeyEvent.VK_A);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createByAirportPopupDialog();
			}			
		};
		byAirportItem.addActionListener(listener);
		add(byAirportItem);
		
	}
	
	private void createByFlightPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Delay of Flight");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Flight Number");
		airportLabel.setBounds(20, 20, 100, 30);
		myPanel.add(airportLabel);
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField flightField = new JTextField();
		flightField.setBounds(110, 20, 150, 30);
		myPanel.add(flightField);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(110, 50, 150, 30);
		myPanel.add(dateField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(50, 210, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			   String flightNumber = flightField.getText();
			   String dateStr = dateField.getText();
			   Date date = SearchMenu.parseDateString(dateStr);
			   double delay = myPredictor.getDelayInfo(flightNumber, date);
			   if(delay < 0) {
				   myConsole.append("Sorry, cannot find any delay information for flight " + flightNumber + "\n");
			   }else {
				   myConsole.append("The delay of " + flightNumber + " is " + Double.toString(delay) + "\n");   
			   }
			   dialog.dispose();
			}
			
		};
		confirmButton.addActionListener(registerListener);
		myPanel.add(confirmButton);
		
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

	
	private void createByAirportPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("Delay of Flight");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Flight Number");
		airportLabel.setBounds(20, 20, 100, 30);
		myPanel.add(airportLabel);
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField flightField = new JTextField();
		flightField.setBounds(110, 20, 150, 30);
		myPanel.add(flightField);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(110, 50, 150, 30);
		myPanel.add(dateField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(50, 210, 90, 30);
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
