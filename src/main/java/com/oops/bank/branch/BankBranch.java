package com.oops.bank.branch;

/**
 * This is the branch class which is used to create branch
 * 
 * @author GS-2022
 *
 */
public class BankBranch {

	private int branchId;
	private String branchName;
	private String branchAddress;
	private String manager;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
