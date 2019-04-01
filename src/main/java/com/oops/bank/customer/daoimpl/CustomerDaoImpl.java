package com.oops.bank.customer.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.oops.bank.customer.Customer;
import com.oops.bank.customer.dao.CustomerDao;
import com.oops.bank.singleton.Singleton;

public class CustomerDaoImpl implements CustomerDao {
	private PreparedStatement preparedStatement;
	private Statement statement;
	private ResultSet resultSet;
	private Scanner sc;
	private int tempCif, numberOfRows;
	private String sql, sql1;
	int choice;

	private Connection connection;

	public CustomerDaoImpl(Scanner sc) {
		connection = Singleton.getInstance();
		this.sc = sc;
	}

	/**
	 * method is used to create customer by setting attributes of customer
	 */
	public void createCustomer(Customer customer) {

		System.out.println("Enter Name:  ");

		customer.setCustomerName(sc.next());
		System.out.println("Customer type");
		System.out.println("\n[1.staff, 2.srcitizen, 3. staffsrcitizen] :");
		choice = sc.nextInt();
		if (choice == 1) {
			customer.setCustomerType("staff");
		} else if (choice == 2) {
			customer.setCustomerType("srcitizen");
		} else if (choice == 3) {
			customer.setCustomerType("staffsrcitizen");
		}

		System.out.println("Pan card :");
		customer.setPanNumber(sc.next());
		System.out.println("Aadhar no :");
		customer.setAadhaarNumber(sc.next());

		insertData(customer);
	}

	/**
	 * Used to insert customer information into customer table
	 * 
	 * @param customer
	 */
	public void insertData(Customer customer) {
		sql1 = "select count(*) from customer";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql1);
			while (resultSet.next()) {
				numberOfRows = resultSet.getInt(1);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		String sql = "select cif from customer order by cif desc limit 1";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				tempCif = resultSet.getInt("cif");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "insert into customer(cif,customer_name,customer_type,aadhaar_number,pan_number) values(?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);

			if (numberOfRows == 0) {
				preparedStatement.setInt(1, 1001);
				preparedStatement.setString(2, customer.getCustomerName());
				preparedStatement.setString(3, customer.getCustomerType());
				preparedStatement.setString(4, customer.getAadhaarNumber());
				preparedStatement.setString(5, customer.getPanNumber());

				preparedStatement.executeUpdate();

			} else {
				++tempCif;
				preparedStatement.setInt(1, tempCif);
				preparedStatement.setString(2, customer.getCustomerName());
				preparedStatement.setString(3, customer.getCustomerType());
				preparedStatement.setString(4, customer.getAadhaarNumber());
				preparedStatement.setString(5, customer.getPanNumber());

				preparedStatement.executeUpdate();

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Used to retrieve customer type from database using cif
	 * 
	 * @param cif
	 * @return Customer Type
	 */
	public String fetchCustomerTypeByCif(int cif) {
		if (cif < 1000)
			throw new IllegalArgumentException("cif is invalid");
		String custtype = "";
		sql = "select customer_type from customer where cif=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cif);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				custtype = resultSet.getString("customer_type");
			}
			return custtype;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public boolean isCustomerExists(int cif) {
		if (cif < 1000)
			throw new IllegalArgumentException("cif is invalid");
		sql = "select cif from customer where cif=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cif);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Display all customer information like cif , Name, customer type etc
	 */
	public void displayCustomerData() {

		sql = "select * from customer where isexist=? ";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "true");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				System.out.println("\ncif: " + resultSet.getInt("cif") + "\nName: "
						+ resultSet.getString("customer_name") + "\nCustomer Type: "
						+ resultSet.getString("customer_type") + "\nPanno: " + resultSet.getString("pan_number")
						+ "\nAdharno: " + resultSet.getString("aadhaar_number"));

			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * Display customer information for specified cif
	 * 
	 * @param cif
	 */
	public void displayCustomerData(int cif) {

		sql = "select * from customer where cif=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cif);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				System.out.println("\ncif: " + resultSet.getInt("cif") + "\nName: "
						+ resultSet.getString("customer_name") + "\nCustomer Type: "
						+ resultSet.getString("customer_type") + "\nPanno: " + resultSet.getString("pan_number")
						+ "\nAdharno: " + resultSet.getString("aadhaar_number"));
			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method deletes particular customer and also deletes all the account
	 * associated with him.
	 * 
	 * @param cif
	 */
	public void deleteCustomer(int cif) {
		sql = "update customer set isexist=? where cif=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, cif);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sql = "update account set isexist=? where cif=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, cif);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		String sql = "update interestlist set isexist=? where cif=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, cif);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Close prepared statement
	 */
	public void closePreparedStatementCustomer() {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Activate the deleted customer
	 * 
	 * @param cif
	 */
	public void activateCustomer(int cif) {

		String isexist = "";
		sql = "select isexist from customer where cif=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cif);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				isexist = resultSet.getString("isexist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isexist.equalsIgnoreCase("false")) {
			sql = "update customer set isexist=? where cif=?";
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, "true");
				preparedStatement.setInt(2, cif);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("customer is already activated");
		}

	}

	/**
	 * Displays all customer who are deleted
	 */
	public void displayDeactivatedCustomers() {
		sql = "select * from customer where isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("\ncif: " + resultSet.getInt("cif") + "\nName: "
						+ resultSet.getString("customer_name") + "\nCustomer Type: "
						+ resultSet.getString("customer_type") + "\nPanno: " + resultSet.getString("pan_number")
						+ "\nAdharno: " + resultSet.getString("aadhaar_number"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * close the connection (non-Javadoc)
	 * 
	 * @see com.oops.bank.customer.dao.CustomerDao#closeConnection()
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
