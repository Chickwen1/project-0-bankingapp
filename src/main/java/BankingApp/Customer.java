package BankingApp;

public class Customer {

	private String username;
	private String password;
	private CustomerAccount account;
	//private Account account;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String username, String password, CustomerAccount account) {
		super();
		this.username = username;
		this.password = password;
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

	public CustomerAccount getAccount() {
		return account;
	}

	public void setAccount(CustomerAccount account) {
		this.account = account;
	}

	public double withdrawal(double amount)
	{
		return 0;
	}
	
	public double deposit(double amount)
	{
		return 0;
	}
	
	public double transfer(double amount)
	{
		return 0;
	}
	
	
	
	
}
