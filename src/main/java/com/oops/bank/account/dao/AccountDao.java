package com.oops.bank.account.dao;

import com.oops.bank.account.Account;
import com.oops.bank.exception.AccountTypeNullException;

public interface AccountDao {
	public Account createAccount(Account account);

	public void insertData(Account ac);

	public void setInterestDatabase(int cif, double interest);

	public void debitDatabase(int amt, int accno);

	public void creditDatabase(int amt, int accno);

	public void deleteAccount(int accno);

	public int fetchROI(String accountType) throws AccountTypeNullException;

	public void activateAccount(int accountNumber);

	public void displayAccountData(int cif);

	public void displayAccountData();

	public void displayInterestDb(int accno);

	public void displayDeactivatedAccount();

	public void closePreparedStatementAccount();

	public void closeConnection();
}
