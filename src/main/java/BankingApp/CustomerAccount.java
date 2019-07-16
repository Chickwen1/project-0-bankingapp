package BankingApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

 public class CustomerAccount {
	static List<CustomerAccount> customerAcct2 = new ArrayList<CustomerAccount>();
	//static Map<Integer, List<CustomerAccount>> custAcctMap = new HashMap<Integer, List<CustomerAccount>>();
	static int accountKey = 100;

	private String AccountHolder;
	private String accountType;
	private int accountNumber;
	private double accountTotal;

	public CustomerAccount() {
		super();
	}

	public CustomerAccount(String accountHolder, String accountType, int accountNumber, double accountTotal) {
		super();
		AccountHolder = accountHolder;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.accountTotal = accountTotal;
	}

	public String getAccountHolder() {
		return AccountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		AccountHolder = accountHolder;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountTotal() {
		return accountTotal;
	}

	public void setAccountTotal(double accountTotal) {
		this.accountTotal = accountTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AccountHolder == null) ? 0 : AccountHolder.hashCode());
		result = prime * result + accountNumber;
		long temp;
		temp = Double.doubleToLongBits(accountTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAccount other = (CustomerAccount) obj;
		if (AccountHolder == null) {
			if (other.AccountHolder != null)
				return false;
		} else if (!AccountHolder.equals(other.AccountHolder))
			return false;
		if (accountNumber != other.accountNumber)
			return false;
		if (Double.doubleToLongBits(accountTotal) != Double.doubleToLongBits(other.accountTotal))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerAccount [AccountHolder=" + AccountHolder + ", accountType=" + accountType + ", accountNumber="
				+ accountNumber + ", accountTotal=" + accountTotal + "]";
	}

	public static List<CustomerAccount> openAccount(CustomerAccount a) {
		customerAcct2.add(a);
		//custAcctMap.put(a.accountNumber, customerAcct2);
		//accountKey = accountKey + 1;
		//System.out.println(custAcctMap);
		return customerAcct2;
	}

//	public static Map<Integer, List<CustomerAccount>> closeAccount(int a) {
//		if (custAcctMap.containsKey(a)) {
//			custAcctMap.remove(a, customerAcct2);
//			//customerAcct2.remove(a);
//			System.out.println(custAcctMap);
//				}
//		return custAcctMap;
//	}
	public static List<CustomerAccount> closeAccount(int a) {
		List<CustomerAccount> removeItem = new ArrayList<CustomerAccount>();
		for (CustomerAccount customerAccount : customerAcct2) {
			if(customerAccount.accountNumber == a) {
				System.out.println("You have closed your account " + customerAccount);
				removeItem.add(customerAccount);
				customerAcct2.remove(customerAccount);
			}
		}
		customerAcct2.removeAll(removeItem);
		return customerAcct2;
	}

	
//	public static Map<Integer, List<CustomerAccount>> viewAccount (Customer c) {
//		for (Map.Entry entry : custAcctMap.entrySet())
//		{
//			custAcctMap.get(entry);
//			if(custAcctMap.entrySet().contains(entry))
//		    System.out.println(custAcctMap);
//		}
//
//		return custAcctMap;
//		
//	}
	public static void viewAccount(Customer c) {
		if (customerAcct2.isEmpty())
			System.out.println("There are currently no accounts.");

		for (CustomerAccount customerAccount : customerAcct2) {
			if (customerAccount.AccountHolder == c.getUsername()) {
				System.out.println(customerAccount);
			}
		}
	}

//	public static Map<Integer, List<CustomerAccount>> viewAllAccount() {
//		if (customerAcct2.isEmpty())
//			System.out.println("You have no accounts.");
//		for (Map.Entry entry : custAcctMap.entrySet())
//		{
//		    System.out.println(custAcctMap);
//		}
//		return custAcctMap;
//	}
}
