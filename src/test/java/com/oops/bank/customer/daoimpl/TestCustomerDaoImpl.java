package com.oops.bank.customer.daoimpl;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.*;

import com.mockrunner.mock.jdbc.MockResultSet;
import com.oops.bank.customer.Customer;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerDaoImpl {

	Scanner sc;

	@InjectMocks
	CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl(sc);

	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement preparedStatement;
	@Mock
	private ResultSet resultSet;
	private String customerType;
	private Exception exception;
	private int cif;
	private int i;

	@Before
	public void SetUp() throws Exception {

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	public void testFetchCustTypeByCif() throws Exception {

		Customer customer = new Customer();
		customer.setCustomerType("staff");
		customer.setCif(1010);
		when(resultSet.getString("customer_type")).thenReturn(customer.getCustomerType());

		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		customerType = customerDaoImpl.fetchCustomerTypeByCif(customer.getCif());
		assertEquals("staff", customerType);

	}

	@Test
	public void testFetchCustTypeByCifNegative() throws Exception {
		this.cif = 0;
		Customer customer = new Customer();
		customer.setCustomerType("srcitizen");

		when(resultSet.getString("customer_type")).thenReturn(customer.getCustomerType());

		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		try {
			customerType = customerDaoImpl.fetchCustomerTypeByCif(cif);
		} catch (Exception e) {
			exception = e;
			System.out.println(e.getMessage());
		}
		assertEquals(IllegalArgumentException.class, exception.getClass());

	}

	@Test
	public void testisCustomerExists() throws Exception {
		Customer customer=new Customer();
		customer.setCif(1011);
		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		boolean testTrue = customerDaoImpl.isCustomerExists(customer.getCif());
		assertEquals(true, testTrue);

	}

	@Test
	public void testisCustomerExistsNegative() throws Exception {
		this.cif = -1;
		when(resultSet.next()).thenReturn(true);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		try {
			customerDaoImpl.isCustomerExists(cif);
		} catch (Exception e) {
			exception = e;
			System.out.println(e.getMessage());
		}

		assertEquals(IllegalArgumentException.class, exception.getClass());

	}

	@Test
	public void testDisplayCustomerData() throws Exception {
		Customer customer = new Customer();
		customer.setCif(1009);
		customer.setCustomerName("kunal");
		customer.setCustomerType("staff");
		customer.setAadhaarNumber("ad1234");
		customer.setPanNumber("pan321");

		MockResultSet mockresultset = new MockResultSet("mockresultset");
		ArrayList<ArrayList<Object>> customerlist = new ArrayList<ArrayList<Object>>();

		for (i = 1; i < 4; i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(customer.getCif() + i);
			list.add(customer.getCustomerName() + i);
			list.add(customer.getCustomerType());
			list.add(customer.getPanNumber() + i);
			list.add(customer.getAadhaarNumber() + i);
			customerlist.add(list);

		}
		mockresultset.addColumn("cif");
		mockresultset.addColumn("customer_name");
		mockresultset.addColumn("customer_type");
		mockresultset.addColumn("pan_number");
		mockresultset.addColumn("aadhaar_number");

		for (ArrayList<Object> list : customerlist) {
			mockresultset.addRow(list);
		}

		when(preparedStatement.executeQuery()).thenReturn(mockresultset);
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getInt("cif")).thenReturn(customer.getCif());
		when(resultSet.getString("customer_name")).thenReturn(customer.getCustomerName());
		when(resultSet.getString("customer_type")).thenReturn(customer.getCustomerType());
		when(resultSet.getString("pan_number")).thenReturn(customer.getPanNumber());
		when(resultSet.getString("aadhaar_number")).thenReturn(customer.getAadhaarNumber());
		System.out.println("******* Output : Display All customers *************");
		customerDaoImpl.displayCustomerData();

	}

	@Test
	public void testDisplayCustomerByCif() throws Exception {
		Customer customer = new Customer();
		customer.setCif(1011);
		customer.setCustomerName("rahukl");
		customer.setCustomerType("staffsrcitizen");
		customer.setAadhaarNumber("ad12345");
		customer.setPanNumber("pan32111");

		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getInt("cif")).thenReturn(customer.getCif());
		when(resultSet.getString("customer_name")).thenReturn(customer.getCustomerName());
		when(resultSet.getString("customer_type")).thenReturn(customer.getCustomerType());
		when(resultSet.getString("pan_number")).thenReturn(customer.getPanNumber());
		when(resultSet.getString("aadhaar_number")).thenReturn(customer.getAadhaarNumber());
		System.out.println("******* Output : Display  customer by cif *************");
		customerDaoImpl.displayCustomerData(customer.getCif());

	}

	@Test
	public void testdisplayDeactivatedCustomers() throws Exception {
		Customer customer = new Customer();
		customer.setCif(1028);
		customer.setCustomerName("sterling");
		customer.setCustomerType("staff");
		customer.setAadhaarNumber("ad12345");
		customer.setPanNumber("pan32111");

		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getInt("cif")).thenReturn(customer.getCif());
		when(resultSet.getString("customer_name")).thenReturn(customer.getCustomerName());
		when(resultSet.getString("customer_type")).thenReturn(customer.getCustomerType());
		when(resultSet.getString("pan_number")).thenReturn(customer.getPanNumber());
		when(resultSet.getString("aadhaar_number")).thenReturn(customer.getAadhaarNumber());
		System.out.println("******* Output : Display All deactivated customers *************");
		customerDaoImpl.displayDeactivatedCustomers();
	}
}
