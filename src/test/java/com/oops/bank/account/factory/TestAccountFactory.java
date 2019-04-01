package com.oops.bank.account.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oops.bank.account.Account;
import com.oops.bank.account.impl.FdAccount;
import com.oops.bank.account.impl.HomeLoanAccount;
import com.oops.bank.account.impl.SavingsAccount;

public class TestAccountFactory {

	AccountFactory accountFactory = new AccountFactory();
	Account account = new Account();

	@Test
	public void testAccoutFactorySavings() {
		String accountType = "savings";
		account = accountFactory.getAccount(accountType);
		assertEquals(SavingsAccount.class, account.getClass());

	}

	@Test
	public void testAccoutFactoryFd() {
		String accountType = "Fd";
		account = accountFactory.getAccount(accountType);
		assertEquals(FdAccount.class, account.getClass());

	}

	@Test
	public void testAccoutFactoryHomeLoan() {
		String accountType = "HomeLoan";
		account = accountFactory.getAccount(accountType);
		assertEquals(HomeLoanAccount.class, account.getClass());

	}

}
