package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import exception.DatabaseInitialException;

/**
 * 
 * @author Richard Yang
 *
 */

public class DBInitializer {
	
	private WeatherPoints myHisWeathers;
	private Delays myDelays;
	private DBConnector myConnector;
	
	
	
	
	public DBInitializer(String hisWeatherPath, String delayPath) {
		myHisWeathers = new WeatherPoints(hisWeatherPath);
		myDelays = new Delays(delayPath);
	    myConnector = new DBConnector();
	    try {
			myConnector.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTables() {
		createHistoryWeatherTable();
		createDelayTable();
		createFlightInfoTable();
		createUserInfoTable();
	}
	
	private void createHistoryWeatherTable(){
		try {
			myConnector.makeUpdate("DROP TABLE weatherData ;");
			String insertString = createHistoricalWeatherInitString();		
			myConnector.makeUpdate(insertString);
		} catch (DatabaseInitialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String createHistoricalWeatherInitString() {
		int arraySize = myHisWeathers.arraySize();
		StringBuffer insertString = new StringBuffer();
		insertString.append("CREATE TABLE weatherData (sampleDate date NOT NULL, " +
				                                      "airportCode varchar(5) NOT NULL, ");
		for(int i = 1; i < arraySize; i++) {
			insertString.append("weather" + i + " integer, ");
		}
		insertString.append("weather" + arraySize + " integer, ");
		insertString.append("PRIMARY KEY(sampleDate, airportCode));");
		return insertString.toString();
	}
	
	public void createDelayTable() {
		try {
			myConnector.makeUpdate("DROP TABLE historicalDelay;");		
			myConnector.makeUpdate("CREATE TABLE historicalDelay(sampleDate date NOT NULL," +
			                                                     "flightCode varchar(10) NOT NULL," +
					                                             "delay float NOT NULL," +
					                                             "PRIMARY KEY (sampleDate, flightCode));");
		} catch (DatabaseInitialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createFlightInfoTable() {
		try {
			myConnector.makeUpdate("DROP TABLE flightInfo;");
			myConnector.makeUpdate("CREATE TABLE flightInfo (flightNumber varchar(10) NOT NULL PRIMARY KEY," +
				                                           	"depAirport varchar(5) NOT NULL, " + 
					                                        "desAirport varchar(5) NOT NULL);");
			
		} catch (DatabaseInitialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void createUserInfoTable() {
		try{
			myConnector.makeUpdate("DROP TABLE userInfo;");
			myConnector.makeUpdate("CREATE TABLE userInfo (username varchar(30) NOT NULL PRIMARY KEY," +
                                                          "password varchar(100) NOT NULL);");

		}catch (DatabaseInitialException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initTables() {
		try {
			initHistoricalWeatherTable();
			initHistoricalDelayTable();
			initFlightInfoTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initHistoricalWeatherTable() throws SQLException {
		String stmtString = prepareStmtForWeather();
		Date myDate = new Date(Predictor.START_YEAR, Predictor.START_MONTH, Predictor.START_DAY);
		
		PreparedStatement ps = myConnector.prepareStatement(stmtString);
		
		int size = myHisWeathers.size();
		System.out.println(size);
		int weatherPointSize = myHisWeathers.arraySize();
		for(int i = 0; i < size; i++) {
			Integer[] points = myHisWeathers.get(i);
		  	ps.setDate(1, (Date)myDate.clone());
		  	ps.setString(2, "RDU");
		  	for(int j = 0; j < weatherPointSize; j++) {
		  		ps.setInt(j + 3, points[j]);
		  	}
		  	ps.executeUpdate();
		  	dateSelfIncrease(myDate);
		}
	}
	
	private void dateSelfIncrease(Date myDate) {
		int year = myDate.getYear();
		int month = myDate.getMonth();
		int date = myDate.getDate();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		int daysInMonth = cal.getActualMaximum(Calendar.DATE);
		
		if(date == daysInMonth) {
			if(month == 11) {
				year = year + 1;
				month = 0;
				date = 1;
			}else {
				month ++;
				date = 1;
			}
		}else {
			date ++;
		}
		myDate.setDate(date);
		myDate.setMonth(month);
		myDate.setYear(year);
	}
	
	private String prepareStmtForWeather() {
		StringBuffer str = new StringBuffer();
		str.append("INSERT INTO weatherData  VALUES(");
	    //for sampleDate
		str.append("?,");
		//for flightCode
		str.append("?,");
		//for all weather points
		for(int i = 1; i < myHisWeathers.arraySize(); i++) {
	    	str.append("?,");
	    }
	    str.append("?);");
		
		System.out.println(str);
		return str.toString();
	}
	
	private void initHistoricalDelayTable() throws SQLException {
		String stmtString = "INSERT INTO historicalDelay VALUES(?,?,?);";
	    System.out.println(stmtString);
	    int size = myDelays.size();
	    System.out.println(size);
	    
	    Date myDate = new Date(Predictor.START_YEAR, Predictor.START_MONTH, Predictor.START_DAY);
	    
	    PreparedStatement ps = myConnector.prepareStatement(stmtString);
	    
	    for(int i = 0; i < size; i++) {
	    	double delay = myDelays.get(i);
			ps.setDate(1, (Date)myDate.clone());
			ps.setString(2, "UA001");
			ps.setFloat(3, (float)delay);
			ps.executeUpdate();
			dateSelfIncrease(myDate);
	    }
	}
	
	
	private void initFlightInfoTable() throws SQLException {
		String stmtString = "INSERT INTO flightInfo VALUES (?, ?, ?);";
		PreparedStatement ps = myConnector.prepareStatement(stmtString);
	    //may need a resource bundle for future extensions
		ps.setString(1, "UA001");
	    ps.setString(2, "RDU");
	    ps.setString(3, "JFK");
	    ps.executeUpdate();
	}
	
//	public static void main(String[] args) {
//	    DBInitializer dbi = new DBInitializer("UA001.dat", "delay.txt");
//	    dbi.createTables();
//	    dbi.initTables();
//	}
}
