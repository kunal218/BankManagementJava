package com.oops.bank.main;

import java.util.Scanner;

import com.oops.bank.account.Account;
import com.oops.bank.account.dao.AccountDao;
import com.oops.bank.account.daoimpl.AccountDaoImpl;
import com.oops.bank.account.factory.AccountFactory;
import com.oops.bank.account.intrface.IAccountInterest;
import com.oops.bank.branch.BankBranch;
import com.oops.bank.branch.dao.BankBranchDao;
import com.oops.bank.branch.daoimpl.BankBranchDaoImpl;
import com.oops.bank.customer.Customer;
import com.oops.bank.customer.dao.CustomerDao;
import com.oops.bank.customer.daoimpl.CustomerDaoImpl;

public class BankSystem {

	public static void main(String[] args) {

		String branch;
		int cif, accountNumber, amount;
		String accountType;
		String choice;
		Scanner sc = new Scanner(System.in);
		Account account, account1;

		AccountFactory accountFactory = new AccountFactory();
		BankBranch bankBranch = new BankBranch();

		IAccountInterest iAccountInterest;

		int interest;

		int menuChoice;

		while (true) {
			System.out.println("\n1.Create Customer\n2.Display all Customer\n3.Create Account\n4.Display all Account"
					+ "\n5.Display Interest\n6.Display Customer by cif\n7.Display Account by Cif\n8.Credit Amount"
					+ "\n9.Debit Amount \n10.Create branch \n11.Display all account by branch \n12.Display all loans by branch \n13.Display all branches \n14.Delete customer \n15.Delete account \n16.Delete Branch \n"
					+ "17.Display all customers by branch \n18.Activate Customer \n19.Activate Account "
					+ "\n20.Display Deactivated Customers \n21.Display deactivated accounts \n22.Exit");
			menuChoice = sc.nextInt();
			CustomerDao customerDao = new CustomerDaoImpl(sc);
			BankBranchDao bankBranchDao = new BankBranchDaoImpl(sc);
			AccountDao accountDao = new AccountDaoImpl(sc);

			switch (menuChoice) {
			case 1:
				Customer c1 = new Customer();
				customerDao.createCustomer(c1);
				break;
			case 2:
				customerDao.displayCustomerData();
				break;
			case 3:
				bankBranchDao.displayBranches();
				System.out.println("Enter Branch ");
				branch = sc.next();
				if (bankBranchDao.isCorrectName(branch)) {
					System.out.println("Enter type of account 1.savings 2.fd  3.homeloan");
					int accountOption=sc.nextInt();
					if(accountOption == 1)
						accountType="savings";
					else if(accountOption == 2)
						accountType = "fd";
					else if(accountOption == 3)
						accountType="homeloan";
					else {
						System.out.println("wrong choice");
						break;
						}
					if (accountType.equalsIgnoreCase("savings") || accountType.equalsIgnoreCase("fd")
							|| accountType.equalsIgnoreCase("homeloan")) {
						account = accountFactory.getAccount(accountType);
						iAccountInterest = (IAccountInterest) account;
						account.setAccountType(accountType);

						account1 = accountDao.createAccount(account);
						if (customerDao.isCustomerExists(account1.getCif())) {
							account1.setCustomerType(customerDao.fetchCustomerTypeByCif(account1.getCif()));

							account1.setBranchId(bankBranchDao.retrieveBranchId(branch));

							interest = iAccountInterest.calculateInterest(account1);
							account1.setInterest(interest);

							accountDao.setInterestDatabase(account1.getCif(), interest);
							accountDao.insertData(account1);
						}
					} else
						System.out.println("Customer does not exist...Enter valid cif");
				} else
					System.out.println("Wrong Branch Name");
				break;

			case 4:
				accountDao.displayAccountData();
				break;

			case 5:
				System.out.println("Enter account Number");
				accountNumber = sc.nextInt();
				accountDao.displayInterestDb(accountNumber);

				break;

			case 6:
				System.out.println("Enter the cif");
				cif = sc.nextInt();
				customerDao.displayCustomerData(cif);
				break;

			case 7:
				System.out.println("Enter the cif");
				cif = sc.nextInt();
				accountDao.displayAccountData(cif);
				break;
			case 8:
				System.out.println("Enter the account number");
				accountNumber = sc.nextInt();
				System.out.println("Enter the amount to be credited");
				amount = sc.nextInt();
				accountDao.creditDatabase(amount, accountNumber);

				break;
			case 9:
				System.out.println("Enter the account number");
				accountNumber = sc.nextInt();
				System.out.println("Enter the amount to be debited");
				amount = sc.nextInt();
				accountDao.debitDatabase(amount, accountNumber);
				break;
			case 10:
				bankBranch = new BankBranch();
				bankBranchDao.createBranch(bankBranch);
				System.out.println("Branch Created");
				bankBranchDao.insertData(bankBranch);

				break;
			case 11:
				System.out.println("enter branch id");
				int branchid = sc.nextInt();
				bankBranchDao.displayAllAccounts(branchid);
				break;

			case 12:
				System.out.println("enter branch id");
				branchid = sc.nextInt();
				System.out.println("which type of loan");
				String loan = sc.next();
				bankBranchDao.displayAllLoans(branchid, loan);

				break;
			case 13:
				bankBranchDao.displayBranches();
				break;

			case 14:
				System.out.println("Enter the cif");
				cif = sc.nextInt();
				customerDao.deleteCustomer(cif);
				System.out.println("customer deleted");
				break;
			case 15:
				System.out.println("Enter account number");
				accountNumber = sc.nextInt();
				accountDao.deleteAccount(accountNumber);
				System.out.println("account deleted");
				break;
			case 16:
				bankBranchDao.displayBranches();
				System.out.println("enter the branch id you want to delete");
				branchid = sc.nextInt();
				System.out.println("Do you want to move all accounts to another branch");
				choice = sc.next();
				if (choice.equalsIgnoreCase("Yes")) {
					System.out.println("Enter the id of branch where you want to move your accounts");
					int id = sc.nextInt();
					bankBranchDao.moveAccounts(branchid, id);
					System.out.println("Successfully moved");
				} else
					bankBranchDao.deleteBranch(branchid);
				break;
			case 17:
				System.out.println("enter the branch id");
				branchid = sc.nextInt();
				bankBranchDao.displayAllCustomer(branchid);
				break;
			case 18:
				System.out.println("Enter the cif you want to activate");
				cif = sc.nextInt();

				customerDao.activateCustomer(cif);

				break;
			case 19:
				System.out.println("Enter the account number");
				accountNumber = sc.nextInt();
				accountDao.activateAccount(accountNumber);
				break;
			case 20:
				customerDao.displayDeactivatedCustomers();
				break;
			case 21:
				accountDao.displayDeactivatedAccount();
				break;
			case 22:
				sc.close();
				accountDao.closePreparedStatementAccount();
				customerDao.closePreparedStatementCustomer();
				bankBranchDao.closePreparedStatementBranch();
				accountDao.closeConnection();
				customerDao.closeConnection();
				bankBranchDao.closeConnection();
				System.exit(0);
				break;
			default:
				System.out.println("Wrong choice");

			}

		}

	}
}
