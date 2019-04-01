package com.oops.bank.account.intrface;

import com.oops.bank.account.Account;

public interface IAccountInterest {

	/**
	 * This Method calculates interest based upon type of account and type of
	 * customer
	 * 
	 * @param Account Object
	 * @return interest
	 * 
	 */
	int calculateInterest(Account account);
}
