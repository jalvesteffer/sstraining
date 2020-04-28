/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jalveste
 *
 */
public class ConnectionUtil {

	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream("resources/config/lms.properties")) {
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			// register JDBC driver
			Class.forName(prop.getProperty("DB_DRIVER"));

			// create a connection
			conn = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("DB_USERNAME"),
					prop.getProperty("DB_PASSWORD"));
			conn.setAutoCommit(Boolean.FALSE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
}
