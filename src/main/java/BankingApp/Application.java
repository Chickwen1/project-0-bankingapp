package BankingApp;

import java.util.Scanner;
import bankDao.*;

public class Application {

	private static Scanner sc = new Scanner(System.in);
	static int newAcctNum = 10000;

	public static void main(String[] args) throws Exception {

		// Determine who is logging in
		System.out.println("Are you a customer or super user? (Select a number) ");
		System.out.println("1. Customer ");
		System.out.println("2. Super User");

		int userLog = 0;
		try {
			userLog = Integer.parseInt(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			main(null);
		}
		if (userLog < 0 || userLog > 2) {
			System.out.println("Please select one of the options.");
			main(null);
		}
		selectUser(userLog);
	}

	/**
	 * @param userLog
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param userLog
	 * @return
	 * @throws Exception
	 */
	public static int selectUser(int userLog) throws Exception {
		switch (userLog) {
		case 1:
			loginCustomer();
			break;
		case 2:
			loginSuperUser();
			break;
		default:
			System.out.println("Not a valid option");
		}
		return 0;
	}

	public static void loginCustomer() throws Exception {
		System.out.println("Welcome Valued Customer!");
		System.out.println("Please enter username");

		int numUserId = 0;
		try {
			numUserId = Integer.valueOf(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Needs to be numbers.");
			loginCustomer();
		}
		System.out.println("Please enter password");
		String password = sc.next();

		// LoginOracle.getDao().getCustomerNameAndPassword(userId, password);
		if (LoginOracle.getDao().getCustomerNameAndPassword(numUserId, password) == true)
			customerMenu();
		else
			System.out.println("The username/password is incorrect.");
		main(null);
	}

	public static int loginSuperUser() throws Exception {
		System.out.println("Welcome Valued Employee!");
		System.out.println("Please enter username");

		int numEmpId = 0;
		try {
			numEmpId = Integer.valueOf(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid user id");
			loginSuperUser();
		}
		System.out.println("Please enter password");
		String password = sc.next();

		// LoginOracle.getDao().getEmployeeNameAndPassword(userId, password);

		if (LoginOracle.getDao().getEmployeeNameAndPassword(numEmpId, password) == true)
			superUserMenu();
		else
			System.out.println("The username/password is incorrect.");
		main(null);

		return numEmpId;
	}

	public static void customerMenu() throws Exception {
		System.out.println("Welcome back! What would you like to do today? (Select a number)");
		System.out.println("1. Open Account");
		System.out.println("2. Close Account");
		System.out.println("3. Withdrawal from Account");
		System.out.println("4. Deposit to Account");
		System.out.println("5. Transfer to/from Account");
		System.out.println("6. View accounts");
		System.out.println("7. View transactions");
		System.out.println("8. Log Out");

		int selectOption = 0;
		try {
			selectOption = Integer.parseInt(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			customerMenu();
		}

		if (selectOption < 0 || selectOption > 8) {
			System.out.println("Please select one of the options.");
			customerMenu();
		}
		executeChoice(selectOption);
	}

	public static void executeChoice(int selectOption) throws Exception {
		switch (selectOption) {
		case 1:
			openAccount();
			break;
		case 2:
			closeAccount();
			break;
		case 3:
			withdraw();
			break;
		case 4:
			deposit();
			break;
		case 5:
			transfer();
			break;
		case 6:
			AccountOracle.viewCustomerAccount(LoginOracle.currentUser);
			customerMenu();
			break;
		case 7:
			AccountOracle.viewCustomerTransactions(LoginOracle.currentUser);
			customerMenu();
			break;
		case 8:
			main(null);
			break;
		default:
			System.out.println("Not a valid option");
			customerMenu();
		}
	}

	public static void transfer() throws Exception {
		System.out.println("What account do you want to transfer from?");
		String fromAcctId = sc.next();
		String transType1 = "transfer from";

		System.out.println("What account do you want to transfer to?");
		String toAcctId = sc.next();
		String transType2 = "transfer to";

		System.out.println("How much do you want to transfer?");

		double transferAmt = 0;

		try {
			transferAmt = Double.parseDouble(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			transfer();
		}
		if (transferAmt < 0) {
			System.out.println("This is not a valid amount.");
			transfer();
		}

		if (AccountOracle.transfer(fromAcctId, toAcctId, transferAmt) == true) {
			AccountOracle.createNewTransaction(transferAmt, transType1, fromAcctId);
			AccountOracle.createNewTransaction(transferAmt, transType2, toAcctId);
		} else
			System.out.println("The transfer failed. Please try again.");

		customerMenu();

	}

	public static void deposit() throws Exception {
		System.out.println("What account do you want to deposit into?");
		String acctId = sc.next();

		System.out.println("How much do you want to deposit?");
		double depositAmt = 0;

		try {
			depositAmt = Double.parseDouble(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			deposit();
		}

		if (depositAmt < 0) {
			System.out.println("This is not a valid amount.");
			deposit();
		}
		String transType = "Deposit";

		if (AccountOracle.deposit(acctId, depositAmt) == true)
			AccountOracle.createNewTransaction(depositAmt, transType, acctId);
		else
			System.out.println("The deposit failed. Please try again.");

		customerMenu();
	}

	public static void withdraw() throws Exception {
		System.out.println("What account do you want to withdraw from?");
		String acctId = sc.next();

		System.out.println("How much do you want to withdraw?");

		double withdrawAmt = 0;
		try {
			withdrawAmt = Double.parseDouble(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			withdraw();
		}

		if (withdrawAmt < 0) {
			System.out.println("This is not a valid amount.");
			withdraw();
		}
		String transType = "withdrawal";

		if (AccountOracle.withdraw(acctId, withdrawAmt) == true)
			AccountOracle.createNewTransaction(withdrawAmt, transType, acctId);
		else
			System.out.println("The withdraw failed. Please try again.");

		customerMenu();
	}

	public static void openAccount() throws Exception {
		System.out.println("Would kind of account would you like to open?");
		String acctType = sc.next();

		System.out.println("How much would you like to deposit into new account");
		double depositAmt = 0;
		try {
			depositAmt = Double.valueOf(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			customerMenu();
		}
		if (depositAmt < 0) {
			System.out.println("You need more than 0 to start an account.");
			openAccount();
		}

		if(AccountOracle.createAccount(acctType, depositAmt, LoginOracle.currentUser) == true)
				{
			String transactionType = "Opening Account";
			AccountOracle.createNewTransaction(depositAmt, transactionType, AccountOracle.currentAcctId);
				}
		else
			System.out.println("Could not create an account.");
		
		customerMenu();
	}

	public static void closeAccount() throws Exception {
		System.out.println("What account would you like to delete?");
		String deleteAcct = sc.next();

		if (AccountOracle.deleteAccount(deleteAcct) == true)
			customerMenu();
		else
			System.out.println("That account doesn't exist.");
		customerMenu();
	}

	public static SuperUser superUserMenu() throws Exception {

		System.out.println("Welcome back! What would you like to do today? (Select a number)");

		System.out.println("1. Create New Customer");
		System.out.println("2. View All Accounts");
		System.out.println("3. View All Transactions");
		System.out.println("4. View All Customers");
		System.out.println("5. View all Employees");
		System.out.println("6. Log Out");

		int selectOption = 0;
		try {
			selectOption = Integer.valueOf(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
			superUserMenu();
		}

		if (selectOption < 0 || selectOption > 6) {
			System.out.println("Please select one of the options.");
			superUserMenu();
		}
		return superUserChoice(selectOption);
	}

	public static SuperUser superUserChoice(int selectOption) throws Exception {
		switch (selectOption) {
		case 1:
			createCustomer();
			break;
		case 2:
			AccountOracle.getDao().getAllAccounts();
			break;
		case 3:
			AccountOracle.getDao().getAllTransactions();
			break;
		case 4:
			LoginOracle.getDao().getAllCustomers();
			break;
		case 5:
			LoginOracle.getDao().getAllEmployees();
			break;
		case 6:
			main(null);
			break;
		default:
			System.out.println("Not a valid option");
		}
		return superUserMenu();
	}

	public static void createCustomer() throws Exception {

		System.out.println("What is the customer's first name?");
		String firstName = sc.next();

		System.out.println("What is the " + firstName + "'s last name?");
		String lastName = sc.next();

		System.out.println("What does " + firstName + " " + lastName + " want as their password");
		String custPassword = sc.next();

		LoginOracle.createCustomer(custPassword, lastName, firstName);

		System.out.println("What kind of account is the customer opening?");
		String accountType = sc.next();

		System.out.println("How much is " + firstName + " " + lastName + " adding to their " + accountType + "?");

		double initialDeposit = 0;
		try {
			initialDeposit = Double.valueOf(sc.next());
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number");
		}

		if (initialDeposit < 0) {
			System.out.println("You need more than 0 to start an account.");
			createCustomer();
		}

		AccountOracle.createAccount(accountType, initialDeposit, LoginOracle.currentUser);
		String transactionType = "Opening Account";
		AccountOracle.createNewTransaction(initialDeposit, transactionType, AccountOracle.currentAcctId);

		superUserMenu();
	}

	public static void viewAllAccount() throws Exception {
		AccountOracle.getDao().getAllAccounts();
		superUserMenu();
	}

	public static void viewTransactions() throws Exception {
		AccountOracle.getDao().getAllTransactions();
		superUserMenu();
	}
}
