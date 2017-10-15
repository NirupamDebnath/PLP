package com.capgemini.hotelmanagement.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.hotelmanagement.exception.HotelException;

import oracle.jdbc.pool.OracleDataSource;

public class DBConnection {
	private static Connection connection = null;
	private static DBConnection instance = null;
	private static Properties properties = null;
	private static OracleDataSource dataSource = null;
	Logger logger=Logger.getRootLogger();

	/************************************************************************************************
	 * - Author : CAPGEMINI 
	 * - @throws DonorException 
	 * - Private Constructor 
	 * - Desc:Loads the jdbc.properties file and Driver Class and gets the Connection
	 * - Creation Date : 18/11/2016 
	 ***********************************************************************************************/
	private DBConnection() throws HotelException {
		PropertyConfigurator.configure("resources//log4j.properties");
		try {
			properties = loadProperties();
			dataSource = prepareDataSource();
		} 
		catch (IOException e) {
			logger.error("Could not read the database details from properties file !!");
			throw new HotelException(" Could not read the database details from properties file ");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			throw new HotelException(e.getMessage());
		}

	}

	/*****************************************************************
	 * - Author : CAPGEMINI
	 * - Method Name : getInstance()
	 * - Return Type : DBConnection instance 
	 * - Throws : DonorException 
	 * - Description : Singleton and Thread safe class
	 * - Creation Date : 18/11/2016 
	 *******************************************************************/

	public static DBConnection getInstance() throws HotelException {
		synchronized (DBConnection.class) {
			if (instance == null) {
				instance = new DBConnection();
			}
		}
		return instance;
	}

	/*****************************************************************
	 * - Author : CAPGEMINI
	 * - Method Name : getConnection()
	 * - Return Type : DBConnection instance 
	 * - Throws : DonorException  
	 * - Description : Returns Connection object
	 * - Creation Date : 18/11/2016
	 *******************************************************************/
	public Connection getConnection() throws HotelException {
		PropertyConfigurator.configure("resources//log4j.properties");
		try {
			connection = dataSource.getConnection();

		} 
		catch (SQLException e) {
			logger.error("Database Connection Failed !!");
			throw new HotelException("Database Connection problem");
		}
		return connection;
	}

	/*****************************************************************
	 * - Author : CAPGEMINI
	 * - Method Name : loadProperties()
	 * - Return Type : Properties object
	 * - Description : Returns Properties object
	 * - Creation Date : 18/11/2016
	 *******************************************************************/

	private Properties loadProperties() throws IOException {

		if (properties == null) {
			Properties newProperties = new Properties();
			String fileName = "resources/jdbc.properties";

			InputStream inputStream = new FileInputStream(fileName);
			newProperties.load(inputStream);

			inputStream.close();

			return newProperties;
		}
		else {
			return properties;
		}
	}

	/*****************************************************************
	 * - Author : CAPGEMINI
	 * - Method Name : prepareDataSource()
	 * - Return Type : OracleDataSource object
	 * - Description : Returns OracleDataSource object
	 * - Creation Date : 18/11/2016
	 *******************************************************************/

	private OracleDataSource prepareDataSource() throws SQLException {

		if (dataSource == null) {
			if (properties != null) {
				String ConnectionURL = properties.getProperty("dburl");
				String username = properties.getProperty("username");
				String password = properties.getProperty("password");

				dataSource = new OracleDataSource();

				dataSource.setURL(ConnectionURL);
				dataSource.setUser(username);
				dataSource.setPassword(password);
			}
		}
		return dataSource;
	}
}
