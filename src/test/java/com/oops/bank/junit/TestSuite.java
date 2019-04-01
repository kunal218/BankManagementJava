package com.oops.bank.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.oops.bank.account.impl.fd.TestFdAccount;
import com.oops.bank.account.impl.homeloan.TestHomeLoanAccount;
import com.oops.bank.account.impl.savings.TestSavingsAccount;

/**
 * This class is used to run many test cases together
 * 
 * @author GS-2022
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TestSavingsAccount.class, TestFdAccount.class, TestHomeLoanAccount.class })
public class TestSuite {

}
