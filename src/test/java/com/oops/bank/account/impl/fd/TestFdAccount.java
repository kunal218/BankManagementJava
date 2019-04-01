package com.oops.bank.account.impl.fd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.oops.bank.account.Account;
import com.oops.bank.account.impl.FdAccount;
import com.oops.bank.exception.AccountTypeNullException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oops.bank.account.daoimpl.AccountDaoImpl;

/**
 * This class contains test methods for testing interest calculated for fd
 * account
 * 
 * @author GS-2022
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class TestFdAccount {

	@InjectMocks
	FdAccount fdAccount = new FdAccount();

	@Mock
	AccountDaoImpl accountDao = new AccountDaoImpl();

	Account account = new Account();

	@Test
	public void testCalculateInterestFdAccount1() throws AccountTypeNullException {
		account.setAccountType("fd");
		account.setCustomerType("staff");
		account.setAmount(1000);
		account.setInterest(6);
		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = fdAccount.calculateInterest(account);
		assertEquals(70, interest);
	}

	@Test
	public void testCalculateInterestFdAccount2() throws AccountTypeNullException {
		account.setAccountType("fd");
		account.setCustomerType("srcitizen");
		account.setAmount(2000);
		account.setInterest(6);
		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = fdAccount.calculateInterest(account);
		assertEquals(160, interest);
	}

	@Test
	public void testCalculateInterestFdAccount3() throws AccountTypeNullException {
		account.setAccountType("fd");
		account.setCustomerType("staffsrcitizen");
		account.setAmount(10000);
		account.setInterest(6);
		when(accountDao.fetchROI(anyString())).thenReturn((int) account.getInterest());

		int interest = fdAccount.calculateInterest(account);
		assertEquals(900, interest);
	}

}
