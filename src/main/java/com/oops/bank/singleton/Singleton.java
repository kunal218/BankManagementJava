package com.oops.bank.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is the singleton class which returns only one instance of connection
 * 
 * @author GS-2022
 *
 */
public class Singleton {

	static Connection con = null;

	private Singleton() {

	}

	public synchronized static Connection getInstance() {
		if (con == null) {
			con = getConnection();
		}

		return con;
	}

	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
		return con;
	}

}
