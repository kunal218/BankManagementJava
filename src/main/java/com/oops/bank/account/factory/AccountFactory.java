package com.oops.bank.account.factory;

import com.oops.bank.account.Account;
import com.oops.bank.account.impl.FdAccount;
import com.oops.bank.account.impl.HomeLoanAccount;
import com.oops.bank.account.impl.SavingsAccount;

public class AccountFactory {

	/**
	 * This methods returns the object of particular type of account
	 * 
	 * @param account
	 * @return Accounts child's object
	 */
	public Account getAccount(String account) {

		if (account.equalsIgnoreCase("Savings"))
			return new SavingsAccount();
		else if (account.equalsIgnoreCase("Fd"))
			return new FdAccount();
		else if (account.equalsIgnoreCase("HomeLoan"))
			return new HomeLoanAccount();
		else
			return null;

	}
}
