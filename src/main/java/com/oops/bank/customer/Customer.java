package com.oops.bank.customer;

/**
 * This is the class which is used to create customer
 * 
 * @author GS-2022
 *
 */
public class Customer {

	private int cif;
	private String customerName;
	private String aadhaarNumber;
	private String customerType;
	private String panNumber;

	

	public Customer() {
		cif = 0;
		customerName = "";
		aadhaarNumber = "";
		customerType = "";
		panNumber = "";

	}

	

	public int getCif() {
		return cif;
	}

	public void setCif(int cif) {
		if (cif > 0)
			this.cif = cif;
		else
			System.out.println("sorry cif cant be negative");
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		if (customerName != null)
			this.customerName = customerName;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		if (aadhaarNumber != null)
			this.aadhaarNumber = aadhaarNumber;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		if (customerType != null)
			this.customerType = customerType;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		if (panNumber != null)
			this.panNumber = panNumber;
	}

}
