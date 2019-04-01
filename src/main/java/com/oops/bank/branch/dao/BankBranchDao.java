package com.oops.bank.branch.dao;

import com.oops.bank.branch.BankBranch;

public interface BankBranchDao {
	public void createBranch(BankBranch branch);

	public void insertData(BankBranch b);

	public void displayAllAccounts(int branchid);

	public void displayAllLoans(int branchid, String l);

	public int retrieveBranchId(String branch);

	public void displayBranches();

	public boolean isCorrectName(String branchName);

	public void deleteBranch(int branchId);

	public void closePreparedStatementBranch();

	public void moveAccounts(int oldBranchId, int newBranchId);

	public void displayAllCustomer(int branchId);

	public void closeConnection();
}
