package com.oops.bank.customer.dao;

import com.oops.bank.customer.Customer;

public interface CustomerDao {
	public void createCustomer(Customer customer);

	public void insertData(Customer c);

	public String fetchCustomerTypeByCif(int cif);

	public void displayCustomerData();

	public void displayCustomerData(int cif);

	public void displayDeactivatedCustomers();

	public boolean isCustomerExists(int cif);

	public void deleteCustomer(int cif);

	public void closePreparedStatementCustomer();

	public void activateCustomer(int cif);

	public void closeConnection();
}
