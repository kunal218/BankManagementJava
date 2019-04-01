package com.oops.bank.account.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.oops.bank.account.Account;
import com.oops.bank.account.dao.AccountDao;
import com.oops.bank.exception.AccountTypeNullException;
import com.oops.bank.singleton.Singleton;

/**
 * @author GS-2022
 *
 */

public class AccountDaoImpl implements AccountDao {

	private PreparedStatement preparedStatement;
	private Connection connection;
	private ResultSet resultSet;
	private Scanner sc;
	private String sql, sql1;
	private int balance,ROI;

	public AccountDaoImpl() {

		connection = Singleton.getInstance();

	}

	public AccountDaoImpl(Scanner sc) {
		connection = Singleton.getInstance();
		this.sc = sc;
	}

	/**
	 * This method is used to set cif,amount,year for account
	 * 
	 * @param account
	 * @return Account Object
	 */

	public Account createAccount(Account account) {

		System.out.println("Enter cif ");
		account.setCif(sc.nextInt());
		System.out.println("Enter Amount ");
		account.setAmount(sc.nextInt());

		return account;

	}

	/**
	 * Used to insert account details of particular customer into account table
	 * 
	 * @param ac
	 */
	public void insertData(Account account) {

		try {
			preparedStatement = connection
					.prepareStatement("insert into account(cif,account_type,balance,branch_id) values(?,?,?,?)");

			preparedStatement.setInt(1, account.getCif());

			preparedStatement.setString(2, account.getAccountType());
			preparedStatement.setInt(3, account.getAmount());

			preparedStatement.setInt(4, account.getBranchId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method sets interest value in database for specified account
	 * 
	 * @param cif
	 * @param interest
	 */
	public void setInterestDatabase(int cif, double interest) {
		sql = "insert into interestlist(cif,interest) values(?,?)";
		try {

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, cif);
			preparedStatement.setDouble(2, interest);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Used to debit money from specified account
	 * 
	 * @param account_number
	 * @param amount
	 */
	public void debitDatabase(int amountValue, int accountNumber) {
		String get = "select balance from account where account_number=? AND isexist=?";
		try {

			preparedStatement = connection.prepareStatement(get);
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				balance = resultSet.getInt("balance");
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		sql = "update account set balance=? where account_number=? AND isexist=?";
		try {
			if (balance >= amountValue) {
				int finalBalance = balance - amountValue;
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, finalBalance);
				preparedStatement.setInt(2, accountNumber);
				preparedStatement.setString(3, "true");
				preparedStatement.executeUpdate();
			} else
				System.out.println("Insufficient Balance");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Used to credit money for specified account
	 * 
	 * @param account_number
	 * @param amount
	 */

	public void creditDatabase(int amountValue, int account_number) {
		String getBalance = "select balance from account where account_number=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(getBalance);
			preparedStatement.setInt(1, account_number);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				balance = resultSet.getInt("balance");
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		sql = "update account set balance=? where account_number=? AND isexist=? ";
		try {
			int temp = balance + amountValue;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, temp);
			preparedStatement.setInt(2, account_number);
			preparedStatement.setString(3, "true");
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method deletes specified account
	 * 
	 * @param account_number
	 */
	public void deleteAccount(int accountNumber) {

		sql = "update account set isexist=? where account_number=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sql1 = "update interestlist set isexist=? where account_number=?";

		try {
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Display specified account information
	 * 
	 * @param cif
	 */
	public void displayAccountData(int cif) {

		sql = "select * from account where cif=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cif);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println(
						"\ncif: " + resultSet.getInt("cif") + "\nAccount No: " + resultSet.getInt("account_number")
								+ "\nAccount Type: " + resultSet.getString("account_type") + "\nBalance: "
								+ resultSet.getInt("balance") + "\nBranch ID: " + resultSet.getInt("branch_id"));

			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * This methods displays all the accounts for bank
	 */
	public void displayAccountData() {

		sql = "select * from account where isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "true");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(
						"\ncif: " + resultSet.getInt("cif") + "\nAccount No: " + resultSet.getInt("account_number")
								+ "\nAccount Type: " + resultSet.getString("account_type") + "\nBalance: "
								+ resultSet.getInt("balance") + "\nBranch ID: " + resultSet.getInt("branch_id"));
			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * Used to display interest for particular account
	 * 
	 * @param account_number
	 */
	public void displayInterestDb(int accountNumber) {

		sql = "select cif,account_number,interest from interestlist where account_number=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("cif : " + resultSet.getInt("cif") + " \nAccount No : "
						+ resultSet.getInt("account_number") + " \nInterest: " + resultSet.getDouble("interest"));
			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * used to fetch interest rate from interest table for specified account type
	 * like savings etc
	 * 
	 * @param accountType
	 * @return
	 * @throws AccountTypeNullException 
	 */
	public int fetchROI(String accountType) throws AccountTypeNullException {
		
		if (accountType == null)
			throw new AccountTypeNullException("Account Type can not be null");
		
		
		sql = "select interest_rate from interest where account_type=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accountType);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ROI = resultSet.getInt("interest_rate");
				return ROI;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			
		}
		return 0;
		
	}

	/**
	 * close the prepared statement
	 */
	public void closePreparedStatementAccount()  {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks the account is deactivated or not and if deactivated ,
	 * activate the account by setting isexist='true'
	 * 
	 * @param accountNumber
	 */
	public void activateAccount(int accountNumber) {
		String isexist = "";
		sql = "select isexist from account where account_number = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				isexist = resultSet.getString("isexist");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isexist.equalsIgnoreCase("false")) {
			sql = "update account set isexist=? where account_number=?";

			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, "true");
				preparedStatement.setInt(2, accountNumber);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else
			System.out.println("Account is already activated");

	}

	/**
	 * Display all the deactivated accounts from account table
	 */
	public void displayDeactivatedAccount() {
		sql = "select * from account where isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(
						"\ncif: " + resultSet.getInt("cif") + "\nAccount No: " + resultSet.getInt("account_number")
								+ "\nAccount Type: " + resultSet.getString("account_type") + "\nBalance: "
								+ resultSet.getInt("balance") + "\nBranch ID: " + resultSet.getInt("branch_id"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Close the connection object (non-Javadoc)
	 * 
	 * @see com.oops.bank.account.dao.AccountDao#closeConnection()
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
