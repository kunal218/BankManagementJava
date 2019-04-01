package com.oops.bank.account.impl.savings;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oops.bank.account.Account;
import com.oops.bank.account.daoimpl.AccountDaoImpl;
import com.oops.bank.account.impl.SavingsAccount;
import com.oops.bank.exception.AccountTypeNullException;

import static org.mockito.Mockito.*;

/**
 * This class contains test methods for testing interest calculated for savings
 * account
 * 
 * @author GS-2022
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSavingsAccount {

	@InjectMocks
	SavingsAccount savingsAccount = new SavingsAccount();

	@Mock
	AccountDaoImpl accountDao = new AccountDaoImpl();

	Account account = new Account();

	@Test
	public void testCalculateInterestSavings1() throws AccountTypeNullException {
		account.setAccountType("savings");
		account.setCustomerType("srcitizen");
		account.setAmount(1000);
		account.setInterest(4);

		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = savingsAccount.calculateInterest(account);
		assertEquals(60, interest);
	}

	@Test
	public void testCalculateInterestSavings2() throws AccountTypeNullException {
		account.setAccountType("savings");
		account.setCustomerType("staff");
		account.setAmount(3000);
		account.setInterest(4);
		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());
		int interest = savingsAccount.calculateInterest(account);
		assertEquals(150, interest);
	}

	@Test
	public void testCalculateInterestSavings3() throws AccountTypeNullException {
		account.setAccountType("savings");
		account.setCustomerType("staffsrcitizen");
		account.setAmount(10000);
		account.setInterest(4);

		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());
		int interest = savingsAccount.calculateInterest(account);
		assertEquals(700, interest);
	}

}
