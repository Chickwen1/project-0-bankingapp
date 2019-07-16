package bankDao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.util.ConnectionUtil;

import bankModels.Customer;
import bankModels.Employee;

public class LoginOracle implements LoginDao {


	private static LoginOracle loginOracle;
    private static final Logger log = LogManager.getLogger(LoginOracle.class);
    public static int currentUser;
	
	private LoginOracle() {
		
	}
	
	public static LoginDao getDao() {
		if (loginOracle == null) {
			loginOracle = new LoginOracle();
		}
		return loginOracle;
	}
	public List<Employee> getAllEmployees() throws Exception {
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			System.out.println(":(");
			
		}
		List<Employee> listOfEmployees = null;

		try {
			String sql = "select * from BANKEMPLOYEES";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			listOfEmployees = new LinkedList<Employee>();

			while (rs.next()) {
				listOfEmployees.add(new Employee(rs.getString("employeeid"), rs.getString("emppassword"), rs.getString("lastname"), rs.getString("firstname")));
			}
			
		} catch (SQLException e) {
			throw new Exception ("Bad things happened...");
		}
		for (Employee employee : listOfEmployees) {
			System.out.println(employee);
		}
		return listOfEmployees;

		//log.traceExit(Optional.empty());
		//return Optional.empty();
		//return null;
	}

	public List<Customer> getAllCustomers() throws Exception {
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			System.out.println(":(");
			
		}
		List<Customer> listOfCustomers = null;

		try {
			String sql = "select c.CUSTOMERID, c.CUSTPASSWORD, c.FIRSTNAME, c.LASTNAME from BANKCUSTOMERS c";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			listOfCustomers = new LinkedList<Customer>();

			while (rs.next()) {
				listOfCustomers.add(new Customer(rs.getInt("CUSTOMERID"), rs.getString("CUSTPASSWORD"), rs.getString("LASTNAME"), rs.getString("FIRSTNAME")));
			}
			
		} catch (SQLException e) {
			throw new Exception ("Bad things happened...");
		}
		for (Customer customer : listOfCustomers) {
			System.out.println(customer);
		}
		return listOfCustomers;
	}
	
	public boolean getCustomerNameAndPassword(int user, String pass)  {
		Connection con = ConnectionUtil.getConnection();
		
	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM BANKCUSTOMERS WHERE customerid = ?");
	        ps.setInt(1,(user));
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        		if(rs.getString("custpassword").equals(pass))
	        		{
	        			currentUser = user;
	        			return true;
	        		}
	        }
	        
	    } catch (SQLException ex) {
	    	//throw new Exception ("Bad things happened...");
	    }
		return false;
	}

	public boolean getEmployeeNameAndPassword(int user, String pass) {
		Connection con = ConnectionUtil.getConnection();
		
	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM BANKEMPLOYEES WHERE employeeid = ?");
	        ps.setInt(1, user);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        	if(rs.getString("emppassword").equals(pass))
	        		return true;
	        }
	        
	    } catch (SQLException ex) {
	    }
		return false;
	}

//	public boolean createNewCustomer(String custPass, String lname, String fname) throws Exception {
//		Connection con = ConnectionUtil.getConnection();
//		
//	    try {
//	        PreparedStatement ps = con.prepareStatement("INSERT INTO BANKCUSTOMERS (custpassword, lastname, firstname) VALUES (?, ?, ?)");
//	        ps.setString(1, custPass);
//	        ps.setString(2, lname);
//	        ps.setString(3, fname);
//	        int add = ps.executeUpdate();
//	        
//	        if (add == 1)
//	        {
//	        	System.out.println(add + " row has been added into customers.");
//	        	return true;
//	        }
//	        
//	    } catch (SQLException ex) {
//	    	throw new Exception ("Bad things happened...");
//	    }
//	    return false;
//	}
	
	public static void createCustomer(String custPass, String lname, String fname) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "call create_customer(?, ?, ?, ?)";
		CallableStatement ps = con.prepareCall(sql);
		ps.setString(1, custPass);
		ps.setString(2, lname);
		ps.setString(3, fname);
		ps.registerOutParameter(4, Types.INTEGER);
		ps.execute();
		int id = ps.getInt(4);
		currentUser = id;
		System.out.println("A new customer has been created with the following customer id: "+ id);
	}

}



