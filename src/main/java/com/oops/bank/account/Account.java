package com.oops.bank.account;

/**
 * @author GS-2022 This is the abstract class which has calculateInterest method
 *         which will be overridden by its child classes.
 */
public class Account {

	private int cif;
	private int accountNumber;
	private String accountType;
	private int amount;

	private double interest;
	private String customerType;
	private int branchId;

	public int getCif() {
		return cif;
	}

	public void setCif(int cif) {
		if (cif > 0)
			this.cif = cif;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		if (accountNumber > 0)
			this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		if (accountType != null)
			this.accountType = accountType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount > 0)
			this.amount = amount;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		if (interest > 0)
			this.interest = interest;
	}

	public String getCustomerType() {
		return customerType;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		if (branchId > 0)
			this.branchId = branchId;
	}

	public void setCustomerType(String customerType) {
		if (customerType != null)
			this.customerType = customerType;
	}

}
