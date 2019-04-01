package com.oops.bank.account.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.mockrunner.mock.jdbc.MockResultSet;
import com.oops.bank.account.Account;
import com.oops.bank.exception.AccountTypeNullException;

@RunWith(MockitoJUnitRunner.class)
public class TestAccountDaoImpl {

	@InjectMocks
	private AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement preparedStatement;

	@Mock
	private ResultSet resultSet;
	private Account account;
	private int cif;
	private double interest;
	private int accountNumber;
	private String accountType;
	private Exception exception;
	private int i;

	@Before
	public void SetUp() throws Exception {

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	public void testDisplayAccountDataByCif() throws Exception {
		account = new Account();
		account.setCif(1001);
		account.setAccountNumber(4);
		account.setAccountType("fd");
		account.setAmount(23000);
		account.setBranchId(6);

		MockResultSet mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountList = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(account.getCif());
			list.add(account.getAccountNumber() + i);
			list.add(account.getAccountType());
			list.add(account.getAmount() - (i * 1000));
			list.add(account.getBranchId());

			accountList.add(list);
		}

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("account_type");
		mockResultSet.addColumn("balance");
		mockResultSet.addColumn("branch_id");

		for (ArrayList<Object> list : accountList) {
			mockResultSet.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt("cif")).thenReturn(account.getCif());
		when(resultSet.getInt("account_number")).thenReturn(account.getAccountNumber());
		when(resultSet.getString("account_type")).thenReturn(account.getAccountType());
		when(resultSet.getInt("balance")).thenReturn(account.getAmount());
		when(resultSet.getInt("branch_id")).thenReturn(account.getBranchId());
		System.out.println("******* Output : Display  accounts by cif  *************");
		accountDaoImpl.displayAccountData(account.getCif());

	}

	@Test
	public void testDisplayAccounts() throws Exception {
		account = new Account();
		account.setCif(1022);
		account.setAccountNumber(2);
		account.setAccountType("fd");
		account.setAmount(23000);
		account.setBranchId(6);

		MockResultSet mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountList = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(account.getCif() + i);
			list.add(account.getAccountNumber() + i);
			list.add(account.getAccountType());
			list.add(account.getAmount() + (i * 100));
			list.add(account.getBranchId());

			accountList.add(list);
		}

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("account_type");
		mockResultSet.addColumn("balance");
		mockResultSet.addColumn("branch_id");

		for (ArrayList<Object> list : accountList) {
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
		accountDaoImpl.displayAccountData();
	}

	@Test
	public void fetchROI() throws Exception {
		accountType = "staff";
		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.getInt("interest_rate")).thenReturn(6);
		int ROI = accountDaoImpl.fetchROI(accountType);
		assertEquals(6, ROI);

	}

	@Test
	public void fetchROINegative() throws Exception {
		accountType = null;
		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.getInt("interest_rate")).thenReturn(6);
		
		try {
			accountDaoImpl.fetchROI(accountType);
		} catch (AccountTypeNullException e) {
			exception=e;
			System.out.println(e.getMessage());
			
		}
		assertEquals(AccountTypeNullException.class, exception.getClass());

	}

	@Test
	public void testDisplayInterestDatabase() throws Exception  {
		this.accountNumber = 5;
		this.cif = 1067;
		this.interest = 6d;
		MockResultSet mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountList = new ArrayList<ArrayList<Object>>();

		ArrayList<Object> list = new ArrayList<Object>();
		list.add(cif);
		list.add(accountNumber);
		list.add(interest);

		accountList.add(list);

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("interest");

		for (ArrayList<Object> list1 : accountList) {
			mockResultSet.addRow(list1);
		}

		int tempCif = mockResultSet.findColumn("cif");

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getInt("cif")).thenReturn(tempCif);
		when(resultSet.getInt("account_number")).thenReturn(mockResultSet.findColumn("account_number"));
		when(resultSet.getDouble("interest")).thenReturn((double) mockResultSet.findColumn("interest"));
		System.out.println("******* Output : Display interest *************");
		accountDaoImpl.displayInterestDb(this.accountNumber);
	}

	@Test
	public void testDisplayDeactivatedAccounts() throws Exception {
		account = new Account();
		account.setCif(1088);
		account.setAccountNumber(2);
		account.setAccountType("savings");
		account.setAmount(23000);
		account.setBranchId(3);

		MockResultSet mockResultSet = new MockResultSet("mockResultSet");
		ArrayList<ArrayList<Object>> accountList = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(account.getCif() + i);
			list.add(account.getAccountNumber() + i);
			list.add(account.getAccountType());
			list.add(account.getAmount() + (i * 100));
			list.add(account.getBranchId());

			accountList.add(list);
		}

		mockResultSet.addColumn("cif");
		mockResultSet.addColumn("account_number");
		mockResultSet.addColumn("account_type");
		mockResultSet.addColumn("balance");
		mockResultSet.addColumn("branch_id");

		for (ArrayList<Object> list : accountList) {
			mockResultSet.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getInt("cif")).thenReturn(account.getCif());
		when(resultSet.getInt("account_number")).thenReturn(account.getAccountNumber());
		when(resultSet.getString("account_type")).thenReturn(account.getAccountType());
		when(resultSet.getInt("balance")).thenReturn(account.getAmount());
		when(resultSet.getInt("branch_id")).thenReturn(account.getBranchId());
		System.out.println("******* Output : Display All Deactivated accounts *************");
		accountDaoImpl.displayDeactivatedAccount();

	}

}
