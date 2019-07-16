package bankDao;

import java.util.List;

import bankModels.Customer;
import bankModels.Employee;

public interface LoginDao {
	List<Employee> getAllEmployees() throws Exception;
	List<Customer> getAllCustomers() throws Exception;
	boolean getCustomerNameAndPassword(int u, String p) ;
	boolean getEmployeeNameAndPassword(int u, String p);
}
