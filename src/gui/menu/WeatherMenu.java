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

public class WeatherMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2242126025905878478L;
	private JMenuItem seeWeatherItem;
	private JMenuItem goodWeatherRateItem;
	private Console myConsole;
	
	private Predictor myPredictor;
	
	public WeatherMenu() {
		super("Weather");
		myPredictor = Predictor.getInstance();
		this.setMnemonic(KeyEvent.VK_W);
		createSeeWeatherItem();
		createGoodWeatherRateItem();
	    myConsole = Console.getInstance();
	}
	
	private void createSeeWeatherItem() {
		seeWeatherItem = new JMenuItem("See Weather");
	    seeWeatherItem.setMnemonic(KeyEvent.VK_S);
	    ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createSeeWeatherPopupDialog();
			}
	    };
	    seeWeatherItem.addActionListener(listener);
	    add(seeWeatherItem);
	}
	
	private void createGoodWeatherRateItem() {
	    goodWeatherRateItem = new JMenuItem("Good Weather Rate");
	    goodWeatherRateItem.setMnemonic(KeyEvent.VK_G);
	    ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    createGoodWeatherRatePopupDialog();
			}
	    };
	    goodWeatherRateItem.addActionListener(listener);
	    add(goodWeatherRateItem);
	}
  
	private void createSeeWeatherPopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("See Weather");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Airport");
		airportLabel.setBounds(20, 20, 100, 30);
		myPanel.add(airportLabel);
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField airportField = new JTextField();
		airportField.setBounds(110, 20, 150, 30);
		myPanel.add(airportField);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(110, 50, 150, 30);
		myPanel.add(dateField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(50, 210, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			   String airportCode = airportField.getText();
			   String dateString = dateField.getText();
			   Date date = SearchMenu.parseDateString(dateString);
			   int[] weather = myPredictor.getWeatherInfo(airportCode, date);
			   myConsole.append("the weather around " + airportCode + " is \r\n");
			   for(int i = 0; i < weather.length; i++) {
				   myConsole.append(weather[i] == 0 ? "Good Weather " : "Bad Weather ");
				   myConsole.append("\n");
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
	
	private void createGoodWeatherRatePopupDialog() {
		final JDialog dialog = new JDialog();
        dialog.setTitle("See Weather");
		
		JPanel myPanel = new JPanel();

		myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		myPanel.setLayout(null);
		
		JLabel airportLabel = new JLabel("Airport");
		airportLabel.setBounds(20, 20, 100, 30);
		myPanel.add(airportLabel);
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setBounds(20, 50, 100, 30);
		myPanel.add(dateLabel);
		
		final JTextField airportField = new JTextField();
		airportField.setBounds(110, 20, 150, 30);
		myPanel.add(airportField);
		
		final JTextField dateField = new JTextField();
		dateField.setBounds(110, 50, 150, 30);
		myPanel.add(dateField);
		
		
		JButton confirmButton = new JButton("Confirm");
		
		confirmButton.setBounds(50, 210, 90, 30);
		ActionListener registerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			   String airportCode = airportField.getText();
			   String dateStr = dateField.getText();
			   Date date = SearchMenu.parseDateString(dateStr);
			   double goodWeatherRate = myPredictor.getGoodWeatherRate(airportCode, date);
			   myConsole.append("The Good Weather Rate of " + airportCode + " is " + Double.toString(goodWeatherRate) + "\n");
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


