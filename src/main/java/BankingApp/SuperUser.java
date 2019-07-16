package BankingApp;

import java.util.Comparator;

public class SuperUser implements Comparator<CustomerAccount> {

	private String username;
	private String password;
	private Customer customer;
	private CustomerAccount account;
	
	
	public SuperUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuperUser(String username, String password, Customer customer, CustomerAccount account) {
		super();
		this.username = username;
		this.password = password;
		this.customer = customer;
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerAccount getAccount() {
		return account;
	}

	public void setAccount(CustomerAccount account) {
		this.account = account;
	}

	public CustomerAccount[] openAccount(CustomerAccount a) {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerAccount[] closeAccount(CustomerAccount a) {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerAccount[] viewAccount(Customer c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int compare(CustomerAccount o1, CustomerAccount o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
