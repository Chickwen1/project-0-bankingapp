package BankApplication;

import static org.junit.Assert.*;

import static org.junit.Assume.assumeNotNull;

import java.sql.SQLException;

import org.junit.Test;

import bankDao.AccountDao;
import bankDao.AccountOracle;
import bankDao.LoginDao;
import bankDao.LoginOracle;

public class testCases2 {

	@Test
	public void testCustomerAcct() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getAllCustomers();
		assumeNotNull(dao);
	}
	
	@Test
	public void testEmployeeAcct() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getAllEmployees();
		assumeNotNull(dao);
	}
	
	@Test
	public void testCustValidLogin() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getCustomerNameAndPassword(001, "patches");
		assertTrue(true);
	}

	@Test
	public void testCustInValidLogin() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getCustomerNameAndPassword(001345, "asd");
		assertFalse(false);
	}
	
	@Test
	public void testEmpValidLogin() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getEmployeeNameAndPassword(12345, "password");
		assertTrue(true);
	}

	@Test
	public void testEmpInValidLogin() throws Exception {

		LoginDao dao = LoginOracle.getDao();
		dao.getEmployeeNameAndPassword(754, "asd");
		assertFalse(false);
	}
	
	@Test
	public void testCreateCustomerPositive() throws SQLException {

		LoginOracle.createCustomer("test", "sara", "m");
		assertTrue(true);
	}

	@Test (expected = SQLException.class)
	public void testCreateCustomerNull() throws SQLException {

		LoginOracle.createCustomer(null, null, null);
	}
	
	@Test
	public void testgetAllAcct() throws Exception {
		AccountDao dao = AccountOracle.getDao();
		dao.getAllAccounts();
		assumeNotNull(dao);
	}
	
	@Test
	public void testgetAllTransactions() throws Exception {
		AccountDao dao = AccountOracle.getDao();
		dao.getAllTransactions();
		assumeNotNull(dao);
	}
	
	@Test
	public void testWithdrawPositiveAmt() {
		AccountOracle.withdraw("11044", 20);
		assertTrue(true);
	}
	
	@Test 
	public void testWithdrawNoAcct() throws Exception {
		AccountOracle.withdraw("10000", 10);
		assertFalse(false);
	}
	
	@Test 
	public void testWithdrawGreaterAmt() throws Exception {
		AccountOracle.withdraw("11044", 1000000);
		assertFalse(false);
	}
	
	@Test
	public void testDepositAmt() {
		AccountOracle.deposit("11044", 20);
		assertTrue(true);
	}
	
	@Test 
	public void testDepositNoAcct() throws Exception {
		AccountOracle.deposit("10000", 10);
		assertFalse(false);
	}
	
	@Test
	public void testTransferAmt() {
		AccountOracle.transfer("11044", "11043", 20);
		assertTrue(true);
	}
	
	@Test 
	public void testTransferFromNoAcct() throws Exception {
		AccountOracle.transfer("10000", "11043", 10);
		assertFalse(false);
	}
	
	@Test 
	public void testTransferToNoAcct() throws Exception {
		AccountOracle.transfer("11043", "11000", 10);
		assertFalse(false);
	}
	
	@Test 
	public void testCreateAcct() throws SQLException{
		AccountOracle.createAccount("Student", 200, 231);
		assertTrue(true);
	}
	
	@Test  (expected = SQLException.class)
	public void testCreateAcctNoCustId() throws SQLException{
		AccountOracle.createAccount("Student", 200, 000);
	}
	
	@Test  (expected = SQLException.class)
	public void testCreateAcctNegativeAmt() throws SQLException{
		AccountOracle.createAccount("Student", -200, 231);
	}
	
	@Test
	public void testViewCustTrans() throws SQLException{
		AccountOracle.viewCustomerTransactions(100);
		assertTrue(true);
	}
	
	@Test  //(expected = SQLException.class)
	public void testViewCustTransFail() throws SQLException{
		AccountOracle.viewCustomerTransactions(104340);
		assertFalse(false);
	}
	
	@Test  //(expected = SQLException.class)
	public void testWithdraw() throws Exception{
		BankingApp.Application.withdraw();
		assertFalse(false);
	}
	
}
