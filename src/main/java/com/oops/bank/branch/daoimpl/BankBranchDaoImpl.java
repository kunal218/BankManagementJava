package com.oops.bank.branch.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.oops.bank.branch.BankBranch;
import com.oops.bank.branch.dao.BankBranchDao;
import com.oops.bank.singleton.Singleton;

/**
 * @author GS-2022
 *
 */
/**
 * @author GS-2022
 *
 */

public class BankBranchDaoImpl implements BankBranchDao {
	private PreparedStatement preparedStatement;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private Scanner sc;
	private String sql, sql1;
	private int numberOfRows, tempBranchId1, tempBranchId;

	public BankBranchDaoImpl(Scanner sc) {
		connection = Singleton.getInstance();
		this.sc = sc;
	}

	/**
	 * This method sets branch attributes like branch id,branch name etc
	 */
	public void createBranch(BankBranch branch) {

		System.out.println(" Enter Branch Name : ");
		branch.setBranchName(sc.next());
		System.out.println("Enter Address ");
		branch.setBranchAddress(sc.next());
		System.out.println("Enter Manager");
		branch.setManager(sc.next());
		branch.setBranchId(1);

	}

	/**
	 * Used to insert branch details into database
	 * 
	 * @param b
	 */
	public void insertData(BankBranch b) {
		sql1 = "select count(*) from branchinfo";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql1);
			while (resultSet.next()) {
				numberOfRows = resultSet.getInt(1);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		sql1 = "select branch_id from branchinfo order by branch_id desc limit 1";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql1);
			while (resultSet.next()) {
				tempBranchId1 = resultSet.getInt("branch_id");
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		if (numberOfRows == 0) {
			sql = "insert into branchinfo values(?,?,?)";
			try {
				preparedStatement = connection.prepareStatement(sql);

				b.setBranchId(1);
				preparedStatement.setInt(1, b.getBranchId());
				preparedStatement.setString(2, b.getBranchName());
				preparedStatement.setString(3, b.getBranchAddress());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			sql = "insert into branchinfo(branch_id,branch_name,branch_address) values(?,?,?)";
			try {
				preparedStatement = connection.prepareStatement(sql);

				b.setBranchId(++tempBranchId1);
				preparedStatement.setInt(1, b.getBranchId());
				preparedStatement.setString(2, b.getBranchName());
				preparedStatement.setString(3, b.getBranchAddress());

				int check = preparedStatement.executeUpdate();

				if (check > 0)
					System.out.println("Inserted in database");
				else
					System.out.println("error");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		String sql1 = "select branch_id from branchinfo where branch_name=?";
		try {
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, b.getBranchName());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tempBranchId = resultSet.getInt("branch_id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "insert into branchmanager(branch_id,manager) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, tempBranchId);
			preparedStatement.setString(2, b.getManager());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Display all accounts opened in specified branch
	 * 
	 * @param branch_id
	 */
	public void displayAllAccounts(int branchid) {

		sql = "select * from account where branch_id=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, branchid);
			preparedStatement.setString(2, "true");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.wasNull()) {
				System.out.println("No data");

			} else {
				while (resultSet.next()) {
					System.out.println(
							"\ncif: " + resultSet.getInt("cif") + "\nAccount No: " + resultSet.getInt("account_number")
									+ "\nAccount Type: " + resultSet.getString("account_type") + "\nBalance: "
									+ resultSet.getInt("balance") + "\nBranch ID: " + resultSet.getInt("branch_id"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Display all loans taken from specified branch
	 * 
	 * @param branchid
	 * @param l
	 */
	public void displayAllLoans(int branchid, String accounttype) {

		sql = "select * from account where account_type=? AND branch_id=? AND isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accounttype);
			preparedStatement.setInt(2, branchid);
			preparedStatement.setString(3, "true");
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
	 * This function deletes specified branch and in turn deletes all account
	 * associated with it.
	 * 
	 * @param branchId
	 */
	public void deleteBranch(int branchId) {
		sql = "update branchinfo set isexist=? where branch_id=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, branchId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sql1 = "update branchmanager set isexist=? where branch_id=?";
		try {
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, branchId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		String sql2 = "update account set isexist=? where branch_id=?";
		try {
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, "false");
			preparedStatement.setInt(2, branchId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Used to retrieve branch id for specified branch
	 * 
	 * @param branch
	 * @return branch id
	 */
	public int retrieveBranchId(String branch) {

		sql = "select branch_id from branchinfo where branch_name=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, branch);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				tempBranchId = resultSet.getInt("branch_id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return tempBranchId;
	}

	/**
	 * Display all branches
	 */
	public void displayBranches() {

		sql = "select branchinfo.branch_id,branch_name,branch_address,manager from branchinfo inner join branchmanager on branchinfo.branch_id = branchmanager.branch_id where branchinfo.isexist=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "true");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Branch Id :" + resultSet.getInt("branch_id") + " Name:  "
						+ resultSet.getString("branch_name") + " Branch Address : "
						+ resultSet.getString("branch_address") + " Manager " + resultSet.getString("manager"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This methods updates branchid of accounts if branch is deleted i.e move
	 * accounts to another branch
	 * 
	 * @param oldBranchId
	 * @param newBranchId
	 */
	public void moveAccounts(int oldBranchId, int newBranchId) {

		sql = "update account set branch_id=? where branch_id=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, newBranchId);
			preparedStatement.setInt(2, oldBranchId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close prepared statement
	 */
	public void closePreparedStatementBranch() {

		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is validation method for checking if the branch name is correct or not
	 * 
	 * @param branchName
	 * @return
	 */
	public boolean isCorrectName(String branchName) {
		sql = "select branch_name from branchinfo where branch_name=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, branchName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String s = resultSet.getString("branch_name");
				if (s.equals(branchName)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Display all customers related to specified branch id
	 * 
	 * @param branchId
	 */
	public void displayAllCustomer(int branchId) {

		sql = "select * from customer where cif IN (select cif from account where branch_id=?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, branchId);
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

	/**
	 * Close connection object
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
