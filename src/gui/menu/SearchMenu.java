package gui.menu;

import java.awt.event.KeyEvent;
import java.sql.Date;

import javax.swing.JMenu;

/**
 * 
 * @author Richard Yang
 *
 */

public class SearchMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2800279114346965808L;
	private JMenu myWeatherMenu;
	private JMenu myDelayMenu;
	private JMenu myFlightMenu;
	private JMenu myAirportMenu;
	
	public SearchMenu() {
	    super("Search");
	    createWeatherMenu();
	    createDelayMenu();
	    createFlightMenu();
	    createAirportMenu();
	}
	
	private void createWeatherMenu() {
	    myWeatherMenu = new WeatherMenu();
	    add(myWeatherMenu);
	}
	
	private void createDelayMenu() {
		myDelayMenu = new DelayMenu();
	    myDelayMenu.setMnemonic(KeyEvent.VK_D);
	    add(myDelayMenu);
	}
	
	private void createFlightMenu() {
		myFlightMenu = new FlightMenu();
		myFlightMenu.setMnemonic(KeyEvent.VK_F);
		add(myFlightMenu);
	}
	
	private void createAirportMenu() {
		myAirportMenu = new AirportMenu();
		myAirportMenu.setMnemonic(KeyEvent.VK_A);
		add(myAirportMenu);
	}
	
	public static Date parseDateString(String dateString) {
		String[] parsedDate = dateString.split("-");
		int year = Integer.parseInt(parsedDate[0]) - 1900;
		int month = Integer.parseInt(parsedDate[1]) - 1;
		int date = Integer.parseInt(parsedDate[2]);
		Date resultDate = new Date(year, month, date);
	    return resultDate;
	}
	
}
