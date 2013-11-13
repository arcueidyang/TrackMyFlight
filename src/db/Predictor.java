package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import mlui.InputVectorLearning;
import exception.WrongPredictionFormatException;

/**
 * 
 * @author Richard Yang
 *
 */

public class Predictor {
	
	public static final int START_YEAR = 112;
	public static final int START_MONTH = 10;
	public static final int START_DAY = 9;
	

	private static final String BUFFER_FOLDER_PATH = "./buffer/";
	private static final String RESULT_FOLDER_PATH = "./result/";
	
	private static Predictor myPredictor = new Predictor();
	
	private DBInfoExtractor myDBInfo;
	private InputVectorLearning myLearning;
	
	private boolean authorized;
	
	private Predictor() {
		authorized = true;
		myDBInfo = new DBInfoExtractor();
		myLearning = new InputVectorLearning();
	}
	
	public static Predictor getInstance() {
		return myPredictor;
	}
	
	public boolean containsFlight(String flightNumber) {
		return myDBInfo.searchFlight(flightNumber);
	}
	
	public double predictDelay(String flightNumber) {
		Calendar myCalendar = Calendar.getInstance();

		int year = myCalendar.get(Calendar.YEAR) - 1900;
		
		int month = myCalendar.get(Calendar.MONTH);
		int date = myCalendar.get(Calendar.DATE);

		Date myDate = new Date(year, month, date);
		cleanFiles();
		myDBInfo.generateData(myDate, flightNumber);
	    try {
			myLearning.train();
			myLearning.predict();
		    
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    double predictionResult = -1;
		try {
			predictionResult = extractPredictionResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WrongPredictionFormatException e) {
			
			e.printStackTrace();
		}
		
	    return predictionResult;
	}
	
	public void updateDelay(String flightNumber, double delay) {
		try {
			myDBInfo.updateInformation(flightNumber, delay);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int[] getWeatherInfo(String airportCode, Date date) {
		try {
			return myDBInfo.getWeatherInfo(airportCode, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public double getGoodWeatherRate(String airportCode, Date date) {
		try {
			return myDBInfo.getGoodWeatherRate(airportCode, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public double getDelayInfo(String flightNumber, Date date){
		try {
			return myDBInfo.getDelay(flightNumber, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getDepartAirport(String flightNumber) {
		try {
			return myDBInfo.getAirport(flightNumber, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDestinationAirport(String flightNumber) {
		try {
			return myDBInfo.getAirport(flightNumber, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getFlightByDepart(String airportCode) {
		try{
			return myDBInfo.getFlightFromDepart(airportCode);
		}catch(SQLException e) {
			return null;
		}
	}
	
	public List<String> getFlightByDestination(String airportCode) {
		try{
			return myDBInfo.getFlightFromDestination(airportCode);
		}catch(SQLException e) {
			return null;
		}
	}
	
	public double getAverageDelayByFlight(String flightNumber) {
	    try{
	    	return myDBInfo.getAverageDelayByFlight(flightNumber);
	    }catch(SQLException e) {
		    return -1;
	    }	
	}
	
	public double getAverageDelayByAirport(String airportCode, Date date) {
		try{
			return myDBInfo.getAverageDelayByAirport(airportCode, date);
		}catch(SQLException e) {
			return -1;		
		}
	}
	
	
	public boolean checkUserAuth(String username, String password) {
		return myDBInfo.checkUserAuth(username, password);
	}
	
	public boolean register(String username, String password) {
		return myDBInfo.register(username, password);
	}
	
	public void setAuthorized() {
		authorized = true;
	}
	
	public void setUnAuthorized() {
		authorized = false;
	}
	
	public boolean isAuthorized() {
		return authorized;
	}
	
	private double extractPredictionResult() throws FileNotFoundException, WrongPredictionFormatException {
	   File resultFile = new File(RESULT_FOLDER_PATH + "result.txt");
	   Scanner myScanner = new Scanner(resultFile);
	   String result = myScanner.next();
	   if(myScanner.hasNext()) {
		   myScanner.close();
		   throw new WrongPredictionFormatException();
	   }
	   myScanner.close();
	   
	   return Double.parseDouble(result);
	}
	
	private void cleanFiles() {
	    File historicalWeatherFile = new File(BUFFER_FOLDER_PATH + "historicalData.txt");
	    File realtimeWeatherFile = new File(BUFFER_FOLDER_PATH + "realtimeData");
	    File modelFile = new File(BUFFER_FOLDER_PATH + "model.txt");
	    File resultFile = new File(RESULT_FOLDER_PATH + "result.txt");
	    if(historicalWeatherFile.exists() && historicalWeatherFile.isFile()) {
	    	historicalWeatherFile.delete();
	    }
	    if(realtimeWeatherFile.exists() && realtimeWeatherFile.isFile()) {
	    	realtimeWeatherFile.delete();
	    }
	    if(modelFile.exists() && modelFile.isFile()) {
	    	modelFile.delete();
	    }
	    if(resultFile.exists() && resultFile.isFile()) {
	    	resultFile.delete();
	    }
	}
	
	public static long dateToMillisecond(int year, int month, int date) {
		long milliSeconds = 0;
		for(int i = 1970; i <year; i++) {
			if(i % 100 == 0) {
				if(i % 400 == 0) {
					milliSeconds += 366 * 24 * 3600;
				}
			}
		}
		return milliSeconds;
	}
}
