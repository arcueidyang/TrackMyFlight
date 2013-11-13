package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Richard Yang
 *
 */

public class DBInfoExtractor {

	private DBConnector myConnector;
	
	
	public DBInfoExtractor() {
		
		myConnector = new DBConnector();
		try {
			myConnector.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean searchFlight(String flightNumber) {
		boolean ans = false;
		try{
			PreparedStatement ps = myConnector.prepareStatement("SELECT * " +
                                                                "FROM historicalDelay " +
                                                                "WHERE flightcode = ?;"); 
            ps.setString(1, flightNumber); 
            ResultSet rs = ps.executeQuery();
            ans = rs.next();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ans;
	}
	
	
	
	public void generateData(Date myDate, String flightNumber) {
		String depAirport = null;
		String desAirport = null;
		
		try {
			PreparedStatement flightInfoStmt = myConnector.prepareStatement("SELECT * FROM flightInfo " +
                                                                            "WHERE flightnumber = ?;");
            flightInfoStmt.setString(1, flightNumber);
            ResultSet flightInfoRS = flightInfoStmt.executeQuery();
            flightInfoRS.next();
            depAirport = flightInfoRS.getString(2);
            desAirport = flightInfoRS.getString(3);	
			generateHistoricalData(myDate, depAirport, desAirport, flightNumber);
			generateRealtimeData(myDate, depAirport);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateHistoricalData(Date myDate, String depAirport, String desAirport, String flightNumber) throws SQLException, IOException {
		FileWriter myWriter = null;
		myWriter = new FileWriter("./buffer/historicalData.txt");
		
        //this is the correct version
//        PreparedStatement weatherInfoStmt = myConnector.prepareStatement("SELECT * " +
//        		                                                           "FROM historicalWeather as h1, weatherData as h2 " +
//        		                                                           "WHERE h1.airportCode = ? AND h2.airportCode = ? AND h1.sampleDate = h2.sampleDate");
        //this is the test version
        
        PreparedStatement weatherInfoStmt = myConnector.prepareStatement("SELECT * " +
        		                                                         "FROM historicalDelay NATURAL INNER JOIN weatherData " +
        		                                                         "WHERE airportCode = ? AND flightCode = ? " +
        		                                                         "AND sampleDate < ? ;");
        		                                                         
        weatherInfoStmt.setString(1, depAirport);
        //weatherInfoStmt.setString(2, desAirport);
        weatherInfoStmt.setString(2, flightNumber);
        weatherInfoStmt.setDate(3, myDate);
        ResultSet weatherResult = weatherInfoStmt.executeQuery();
        System.out.println("get query result");
        while(weatherResult.next()) {
        	Date date = weatherResult.getDate(1);
        	System.out.print(date.toString() + " ");
        	String flightCode = weatherResult.getString(2);
        	System.out.print(flightCode + " ");
        	double delay = weatherResult.getFloat(3);
        	myWriter.write(Double.toString(delay) + " ");
        	String airportCode = weatherResult.getString(4);
        	System.out.print(airportCode + " ");
        	for(int i = 1; i <= 156; i++) {
        		myWriter.write(Integer.toString(i) + ":");
			    myWriter.write(weatherResult.getInt(i+4) + " ");
        	}
        	myWriter.write("\r\n");
        	System.out.println();
        }
        myWriter.close();
        
	}
	
	private void generateRealtimeData(Date myDate, String airportCode) throws SQLException, IOException {
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter("./buffer/realtimeData.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PreparedStatement realtimeStmt = myConnector.prepareStatement("SELECT * FROM weatherData " +
				                                                      "WHERE sampleDate = ? AND airportcode = ? ;");
	
	    realtimeStmt.setDate(1, myDate);
	    realtimeStmt.setString(2, airportCode);
	    
	    ResultSet realtimeRS = realtimeStmt.executeQuery();
	    
	    while(realtimeRS.next()) {
	    	for(int i = 1; i <= 156; i++) {
				myWriter.write(Integer.toString(i) + ":");
				myWriter.write(realtimeRS.getInt(i+2) + " ");
		    }	
	    }
	    myWriter.close();
	}
	
	public void updateInformation(String flightNumber, double delay) throws FileNotFoundException, SQLException {
		Scanner s = new Scanner(new File("./buffer/realtimeData.txt"));
		String str = null;
		List<Integer> weatherPoints = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) - 1900;
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		Date currentDate = new Date(year, month, date);
		
		while(s.hasNext()) {
			str = s.next();
			if(str.contains(":")) {
				String[] splited = str.split(":");
				weatherPoints.add(Integer.parseInt(splited[1]));
			}
		}
		
	    PreparedStatement delayStmt = myConnector.prepareStatement("INSERT INTO historicalDelay " +
	    		                                                   "VALUES(?, ?, ?) ;");	
		delayStmt.setDate(1, currentDate);
		delayStmt.setString(2, flightNumber);
		delayStmt.setFloat(3, (float)delay);
		delayStmt.executeUpdate();
		
		s.close();
	}
	
	
	
	public int[] getWeatherInfo(String airportCode, Date date) throws SQLException {
		int[] result = new int[156];
		PreparedStatement weatherStmt = myConnector.prepareStatement("SELECT * FROM weatherData " +
				                                                     "WHERE airportCode = ? AND sampleDate = ? ;");
		weatherStmt.setString(1, airportCode);
        weatherStmt.setDate(2, date);
		ResultSet rs = weatherStmt.executeQuery();
		while(rs.next()) {
			System.out.println("Find next");
			for(int i = 1; i <= 156; i++) {
				result[i - 1] = rs.getInt(i + 2);
			}	
		}
		
		return result;
	}
	
	public double getGoodWeatherRate(String airportCode, Date date) throws SQLException {
		int[] result = getWeatherInfo(airportCode, date);
		int goodWeatherCount = 0;
		for(int i = 0; i < result.length; i++) {
			goodWeatherCount += result[i];
		}
		return (double)goodWeatherCount/result.length;
	}
	
	public double getDelay(String flightNumber, Date date) throws SQLException {
		PreparedStatement delayStatement = myConnector.prepareStatement("SELECT delay FROM historicalDelay " +
				                                                        "WHERE flightCode = ? AND sampleDate = ? ;");
		delayStatement.setString(1, flightNumber);
		delayStatement.setDate(2, date);
		ResultSet rs = delayStatement.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);	
		}
		return -1;
	}
	
	public double getAverageDelayByFlight(String flightNumber) throws SQLException {
		PreparedStatement stmt = myConnector.prepareStatement("SELECT AVG(delay) FROM historicalDelay " +
			 	                                              "WHERE flightCode = ? ;");
		stmt.setString(1, flightNumber);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return -1;
	}
	
	public double getAverageDelayByAirport(String airportCode, Date date) throws SQLException {		
		double totalDelay = 0;
		int flightCount = 0;
		List<String> flightNumber = getFlightFromDepart(airportCode);
		for(String str : flightNumber) {
			double delay = getDelay(str, date);
		    if(delay > 0) {
		    	totalDelay += delay;
		    	flightCount ++;
		    }
		}
		return totalDelay/flightCount;
	}
	
	public String getAirport(String flightNumber, boolean isDestination) throws SQLException {
	    PreparedStatement airportStatement = myConnector.prepareStatement("SELECT * FROM flightInfo " +
	    		                                                          "WHERE flightNumber = ? ;");	
	    airportStatement.setString(1, flightNumber);
	    ResultSet rs = airportStatement.executeQuery();
	    if(!rs.next()) {
	    	return null;
	    }
	    if(isDestination) {
	    	return rs.getString(3);
	    }else {
	    	return rs.getString(2);
	    }
	}
	
	public List<String> getFlightFromDepart(String airportCode) throws SQLException {
		List<String> flights = new ArrayList<String>();
		PreparedStatement stmt = myConnector.prepareStatement("SELECT flightNumber FROM flightInfo " + 
	                                                          "WHERE depAirport = ? ;");
		stmt.setString(1, airportCode);
	    ResultSet rs = stmt.executeQuery();
	    while(rs.next()) {
	        flights.add(rs.getString(1));	
	    }
	    return flights;
	}
	
	public List<String> getFlightFromDestination(String airportCode) throws SQLException {
		List<String> flights = new ArrayList<String>();
		PreparedStatement stmt = myConnector.prepareStatement("SELECT flightNumber FROM flightInfo " + 
	                                                          "WHERE desAirport = ? ;");
		stmt.setString(1, airportCode);
	    ResultSet rs = stmt.executeQuery();
	    while(rs.next()) {
	        flights.add(rs.getString(1));	
	    }
	    return flights;
	}
	
	public boolean register(String username, String password) {
	    String query = "SELECT * FROM userInfo " +
	    		       "WHERE username = ? ;";
        try {
        	PreparedStatement psCheck = myConnector.prepareStatement(query);
    		psCheck.setString(1, username);
        	ResultSet queryRS = psCheck.executeQuery();
    	    if(queryRS.next()) {
    	    	return false;
    	    }
        }catch(SQLException e) {
        	e.printStackTrace();
        }    
	    
		String insert = "INSERT INTO userInfo VALUES ( ?, ?);";
	    PreparedStatement psInsert = null;
		try {
			psInsert = myConnector.prepareStatement(insert);
			psInsert.setString(1, username);
		    psInsert.setString(2, password);
		    psInsert.executeUpdate();
		    return true;
		} catch (SQLException e) {
			return false;		
		}
	}
	
	public boolean checkUserAuth(String username, String password) {
		String query = "SELECT password FROM userInfo " +
 		               "WHERE username = ? ;";
       try {
 	        PreparedStatement psCheck = myConnector.prepareStatement(query);
		    psCheck.setString(1, username);
 	        ResultSet queryRS = psCheck.executeQuery();
	        if(!queryRS.next()) {
	        	return false;
	        }
	        String correctPassword = queryRS.getString(1);
	        System.out.println(correctPassword);
	        if(correctPassword.equals(password)) {
	        	return true;
	        }
	        
       }catch(SQLException e) {
 	       e.printStackTrace();
       }    
       return false;
	}
}
