package com.oops.bank.branch.daoimpl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.oops.bank.account.Account;
import com.oops.bank.branch.BankBranch;
import com.oops.bank.customer.Customer;

@RunWith(MockitoJUnitRunner.class)
public class TestBankBranchDaoImpl {

	Scanner sc;
	@InjectMocks
	private BankBranchDaoImpl bankBranchDaoImpl = new BankBranchDaoImpl(sc);
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement preparedStatement;

	@Mock
	private ResultSet resultSet;

	private Account account;
	private BankBranch branch;

	private int branchid;
	private String branch_name;

	private int i;
	private MockResultSet mockResultSet;

	@Before
	public void SetUp() throws Exception {

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	public void testDisplayAllAccounts() throws Exception {
		account = new Account();
		account.setCif(1055);
		account.setAccountNumber(2);
		account.setAccountType("savings");
		account.setAmount(2300);
		account.setBranchId(2);

		mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountlist = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(account.getCif() + i);
			list.add(account.getAccountNumber() + i);
			list.add(account.getAccountType());
			list.add(account.getAmount() + (i * 100));
			list.add(account.getBranchId());

			accountlist.add(list);
		}

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("account_type");
		mockResultSet.addColumn("balance");
		mockResultSet.addColumn("branch_id");

		for (ArrayList<Object> list : accountlist) {
			mockResultSet.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt("cif")).thenReturn(account.getCif());
		when(resultSet.getInt("account_number")).thenReturn(account.getAccountNumber());
		when(resultSet.getString("account_type")).thenReturn(account.getAccountType());
		when(resultSet.getInt("balance")).thenReturn(account.getAmount());
		when(resultSet.getInt("branch_id")).thenReturn(account.getBranchId());
		System.out.println("******* Output : Display All accounts *************");
		bankBranchDaoImpl.displayAllAccounts(2);

	}

	@Test
	public void testDisplayAllLoans() throws Exception {
		account = new Account();
		account.setCif(1045);
		account.setAccountNumber(12);
		account.setAccountType("homeloan");
		account.setAmount(2300);
		account.setBranchId(6);

		mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountlist = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(account.getCif() + i);
			list.add(account.getAccountNumber() + i);
			list.add(account.getAccountType());
			list.add(account.getAmount() + (i * 100));
			list.add(account.getBranchId());

			accountlist.add(list);
		}

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("account_type");
		mockResultSet.addColumn("balance");
		mockResultSet.addColumn("branch_id");

		for (ArrayList<Object> list : accountlist) {
			mockResultSet.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt("cif")).thenReturn(account.getCif());
		when(resultSet.getInt("account_number")).thenReturn(account.getAccountNumber());
		when(resultSet.getString("account_type")).thenReturn(account.getAccountType());
		when(resultSet.getInt("balance")).thenReturn(account.getAmount());
		when(resultSet.getInt("branch_id")).thenReturn(account.getBranchId());
		System.out.println("******* Output : Display All Loans *************");
		bankBranchDaoImpl.displayAllLoans(account.getBranchId(), account.getAccountType());
		;
	}

	@Test
	public void testDisplayBranches() throws Exception {
		branch = new BankBranch();
		branch.setBranchId(1);
		branch.setBranchName("kothrud");
		branch.setBranchAddress("Pune");
		branch.setManager("Mandar");
		mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> branchlist = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(branch.getBranchId() + i);
			list.add(branch.getBranchName() + i);
			list.add(branch.getBranchAddress() + i);
			list.add(branch.getManager() + i);

			branchlist.add(list);
		}
		mockResultSet.addColumn("branch_id");
		mockResultSet.addColumn("branch_name");
		mockResultSet.addColumn("branch_address");
		mockResultSet.addColumn("manager");
		for (ArrayList<Object> list : branchlist) {
			mockResultSet.addRow(list);
		}
		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt("branch_id")).thenReturn(branch.getBranchId());
		when(resultSet.getString("branch_name")).thenReturn(branch.getBranchName());
		when(resultSet.getString("branch_address")).thenReturn(branch.getBranchAddress());
		when(resultSet.getString("manager")).thenReturn(branch.getManager());
		System.out.println("******* Output : Display All Branches *************");
		bankBranchDaoImpl.displayBranches();
	}

	@Test
	public void testRetrieveBranchId() throws Exception {

		this.branchid = 4;
		this.branch_name = "kothrud";
		when(resultSet.getInt("branch_id")).thenReturn(this.branchid);

		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		int id = bankBranchDaoImpl.retrieveBranchId(this.branch_name);
		assertEquals(this.branchid, id);
	}

	@Test
	public void testDisplayAllCustomers() throws Exception {
		this.branchid = 4;
		Customer c = new Customer();
		c.setCif(1009);
		c.setCustomerName("customer1");
		c.setCustomerType("staff");
		c.setAadhaarNumber("77886");
		c.setPanNumber("pan32");

		MockResultSet mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> customerlist = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(c.getCif() + i);
			list.add(c.getCustomerName() + i);
			list.add(c.getCustomerType());
			list.add(c.getPanNumber() + i);
			list.add(c.getAadhaarNumber() + i);
			customerlist.add(list);

		}
		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("customer_name");
		mockResultSet.addColumn("customer_type");
		mockResultSet.addColumn("pan_number");
		mockResultSet.addColumn("aadhaar_number");

		for (ArrayList<Object> list : customerlist) {
			mockResultSet.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getInt("cif")).thenReturn(c.getCif());
		when(resultSet.getString("customer_name")).thenReturn(c.getCustomerName());
		when(resultSet.getString("customer_type")).thenReturn(c.getCustomerType());
		when(resultSet.getString("pan_number")).thenReturn(c.getPanNumber());
		when(resultSet.getString("aadhaar_number")).thenReturn(c.getAadhaarNumber());
		System.out.println("******* Output : Display All Customers *************");
		bankBranchDaoImpl.displayAllCustomer(this.branchid);
	}

	@Test
	public void testIsCorrectName() throws Exception {
		this.branch_name = "surat";

		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getString("branch_name")).thenReturn(this.branch_name);

		boolean testTrue = bankBranchDaoImpl.isCorrectName(this.branch_name);
		assertEquals(true, testTrue);
	}

}
