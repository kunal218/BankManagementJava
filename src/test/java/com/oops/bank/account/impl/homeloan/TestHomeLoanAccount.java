package com.oops.bank.account.impl.homeloan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oops.bank.account.Account;
import com.oops.bank.account.impl.HomeLoanAccount;
import com.oops.bank.exception.AccountTypeNullException;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oops.bank.account.daoimpl.AccountDaoImpl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * This class contains test methods for testing interest calculated for homeloan
 * account
 * 
 * @author GS-2022
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestHomeLoanAccount {
	@InjectMocks
	HomeLoanAccount homeLoan = new HomeLoanAccount();
	@Mock
	AccountDaoImpl accountDao = new AccountDaoImpl();

	Account account = new Account();

	@Test
	public void testCalculateInterestHomeLoanAccount1() throws AccountTypeNullException {
		account.setAccountType("homeloan");
		account.setCustomerType("srcitizen");
		account.setAmount(1000);
		account.setInterest(10);

		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = homeLoan.calculateInterest(account);
		assertEquals(120, interest);
	}

	@Test
	public void testCalculateInterestHomeLoanAccount2() throws AccountTypeNullException {
		account.setAccountType("homeloan");
		account.setCustomerType("staff");
		account.setAmount(3000);
		account.setInterest(10);

		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = homeLoan.calculateInterest(account);
		assertEquals(330, interest);
	}

	@Test
	public void testCalculateInterestHomeLoanAccount3() throws AccountTypeNullException {
		account.setAccountType("homeloan");
		account.setCustomerType("staffsrcitizen");
		account.setAmount(10000);
		account.setInterest(10);

		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = homeLoan.calculateInterest(account);
		assertEquals(1300, interest);

	}
}
