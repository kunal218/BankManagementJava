package com.oops.bank.account.impl;

import com.oops.bank.account.Account;
import com.oops.bank.account.dao.AccountDao;
import com.oops.bank.account.daoimpl.AccountDaoImpl;
import com.oops.bank.account.intrface.IAccountInterest;
import com.oops.bank.exception.AccountTypeNullException;

/**
 * @author GS-2022 This is the concrete class which provides implementation of
 *         calculateInterest method
 */
public class FdAccount extends Account implements IAccountInterest {

	private double FdROI;

	AccountDao accountDao = new AccountDaoImpl();

	public int calculateInterest(Account account) {
		try {
			FdROI = accountDao.fetchROI(account.getAccountType());
		} catch (AccountTypeNullException e) {
			e.printStackTrace();
		}
		if(FdROI == 0)
		return 0;
			
		if (account.getCustomerType().equalsIgnoreCase("staff"))
			FdROI = FdROI + 1;
		else if (account.getCustomerType().equalsIgnoreCase("srcitizen"))
			FdROI = FdROI + 2;
		else if (account.getCustomerType().equalsIgnoreCase("staffsrcitizen"))
			FdROI = FdROI + 3;

		int interest = (int) ((account.getAmount() * FdROI) / 100);

		return interest;
	}

}
