package com.oops.bank.account.impl;

import com.oops.bank.account.Account;
import com.oops.bank.account.dao.AccountDao;
import com.oops.bank.account.daoimpl.AccountDaoImpl;
import com.oops.bank.account.intrface.IAccountInterest;
import com.oops.bank.exception.AccountTypeNullException;

/**
 * Concrete class for Loan which implements all the methods of Loan Interface
 * and Account(Abstract) Class
 * 
 * @author GS-2022
 *
 */
public class HomeLoanAccount extends Account implements IAccountInterest {

	private double homeROI;
	AccountDao accountDao = new AccountDaoImpl();

	public int calculateInterest(Account account) {
		try {
			homeROI = accountDao.fetchROI(account.getAccountType());
		} catch (AccountTypeNullException e) {
			e.printStackTrace();
		}
		if(homeROI == 0) {
			return 0;
		}
		if (account.getCustomerType().equalsIgnoreCase("staff"))
			homeROI = homeROI + 1;
		else if (account.getCustomerType().equalsIgnoreCase("srcitizen"))
			homeROI = homeROI + 2;
		else if (account.getCustomerType().equalsIgnoreCase("staffsrcitizen"))
			homeROI = homeROI + 3;

		int interest = (int) ((account.getAmount() * homeROI) / 100);

		return interest;
	}

}
