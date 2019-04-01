package com.oops.bank.account.impl;

import com.oops.bank.account.Account;
import com.oops.bank.account.dao.AccountDao;
import com.oops.bank.account.daoimpl.AccountDaoImpl;
import com.oops.bank.account.intrface.IAccountInterest;
import com.oops.bank.exception.AccountTypeNullException;

/**
 * Concrete class for creating savings account
 * 
 * @author GS-2022
 *
 */
public class SavingsAccount extends Account implements IAccountInterest {

	private double savingsROI;

	AccountDao accountDao = new AccountDaoImpl();

	public int calculateInterest(Account account) {
		try {
			savingsROI = accountDao.fetchROI(account.getAccountType());
		} catch (AccountTypeNullException e) {
			e.printStackTrace();
		}
		
		if(savingsROI == 0)
			return 0;
		
		if (account.getCustomerType().equalsIgnoreCase("staff"))
			savingsROI = savingsROI + 1;
		else if (account.getCustomerType().equalsIgnoreCase("srcitizen"))
			savingsROI = savingsROI + 2;
		else if (account.getCustomerType().equalsIgnoreCase("staffsrcitizen"))
			savingsROI = savingsROI + 3;

		int simpleInterest = (int) ((account.getAmount() * savingsROI) / 100);

		return simpleInterest;

	}

}
