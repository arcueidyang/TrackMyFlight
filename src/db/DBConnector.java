package db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exception.DatabaseInitialException;

/**
 * 
 * @author Richard Yang
 *
 */

public class DBConnector {

	public static final String SERVER_URL = "jdbc:postgresql://dukedb-yy96.cloudapp.net:5432/";
	
	private static final String DEFAULT_USERNAME = "azureuser";
	
	private static final String DEFAULT_PASSWORD = "woshidage42152";
	
	private static final String DEFAULT_DB_NAME = "flightdelay";
	
	static {
		try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
            System.exit(1);
        }
	}
	
	private String myDBName;
	
	private Connection myConnection;
	
	private Statement myStatement;
	
	private Properties myProps;
	
	public DBConnector(String dbName, String userName, String password) {
		myDBName = dbName;
		myConnection = null;
		myProps = new Properties();
		setUserName(userName);
		setUserPassword(password);
	}
	
	public DBConnector(String userName, String password) {
		this(DEFAULT_DB_NAME, userName, password);
	}
	
	public DBConnector() {
		this(DEFAULT_USERNAME, DEFAULT_PASSWORD);
	}
	
	public void setUserName(String userName) {
		myProps.setProperty("user", userName);
	}
	
	public void setUserPassword(String password) {
		myProps.setProperty("password", password);
	}
	
	public void clearUserInfo() {
		myProps.clear();
	}
	
	public void connect() throws SQLException {
		String URL = SERVER_URL + myDBName;
		myConnection = DriverManager.getConnection(URL, myProps);
		myStatement = myConnection.createStatement();
	    System.out.println("Connect to database successfully");
	}
	
	public Connection getConnection() {
		return myConnection;
	}
	
	public int makeUpdate(String command) throws DatabaseInitialException, SQLException {
		if(myConnection == null) throw new DatabaseInitialException("Uninitialized Connection to database");
		return myStatement.executeUpdate(command);
	}
	
	public ResultSet makeQuery(String query) throws DatabaseInitialException, SQLException{
		if(myConnection == null) throw new DatabaseInitialException("Uninitialized Connection to database");
		return myStatement.executeQuery(query);
	}
	
	public PreparedStatement prepareStatement(String statement) throws SQLException {
		return myConnection.prepareStatement(statement);
		
	}
	
	public static void main(String[] args) {
		DBConnector dbc = new DBConnector();
		try {
			dbc.connect();
			//dbc.makeQuery("SELECT * FROM employee");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
