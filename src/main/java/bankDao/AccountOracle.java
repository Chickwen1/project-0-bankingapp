package bankDao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import bankModels.Account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.example.util.ConnectionUtil;

import BankingApp.Transactions;

public class AccountOracle implements AccountDao {

	private static AccountOracle accountOracle;
	private static final Logger log = LogManager.getLogger(AccountOracle.class);
	public static String currentAcctId = null;

	private AccountOracle() {

	}

	public static AccountDao getDao() {
		if (accountOracle == null) {
			accountOracle = new AccountOracle();
		}
		return accountOracle;
	}

	public List<Account> getAllAccounts() throws Exception {
		// log.traceEntry();

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			System.out.println(":(");

		}
		List<Account> listOfAccounts = null;

		try {
			String sql = "select * from ACCOUNTS";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			listOfAccounts = new LinkedList<Account>();

			while (rs.next()) {
				listOfAccounts.add(new Account(rs.getString("accountid"), rs.getString("accounttype"),
						rs.getString("accountnumber"), rs.getDouble("accounttotal"), rs.getInt("customerid")));
			}
			rs.close();
    		con.close();
    		ps.close();

		} catch (SQLException e) {
			con.close();
		}
		for (Account account : listOfAccounts) {
			System.out.println(account);
		}
		return listOfAccounts;

		// log.traceExit(Optional.empty());
		// return Optional.empty();
		// return null;
	}

	public List<Transactions> getAllTransactions() throws Exception {
		// log.traceEntry();

		Connection con = ConnectionUtil.getConnection();

		List<Transactions> listOfTransactions = null;

		try {
			String sql = "select * from transaction";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			listOfTransactions = new LinkedList<Transactions>();

			while (rs.next()) {
				listOfTransactions.add(new Transactions(rs.getInt("transactionID"), rs.getDouble("transactionamount"),
						rs.getString("transactiontype"), rs.getString("accountid")));
			}
			rs.close();
    		con.close();
    		ps.close();

		} catch (SQLException e) {
			con.close();
		}
		for (Transactions transactions : listOfTransactions) {
			System.out.println(transactions);
		}
		return listOfTransactions;

		// log.traceExit(Optional.empty());
		// return Optional.empty();
		// return null;
	}

	public static boolean withdraw(String acctId, double withdrawAmt) {
		Connection con = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
			ps.setString(1, acctId);
			ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					if (rs.getString("accountid").equals(acctId)) {
						double newAcctTotal = rs.getDouble("accounttotal") - withdrawAmt;
						ps = con.prepareStatement("UPDATE ACCOUNTS SET ACCOUNTTOTAL = ? WHERE ACCOUNTID = ?");
						ps.setDouble(1, newAcctTotal);
						ps.setString(2, acctId);
						ps.executeUpdate();
						System.out.println("You have withdrawn " + withdrawAmt + " from your account.");
						return true;
					} 
				}

		} catch (SQLException ex) {
			// throw new Exception("Bad things happened...");
		}
		return false;
		
	}

	public static boolean deposit(String acctId, double depositAmt) {
		Connection con = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
			ps.setString(1, acctId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("accountid").equals(acctId)) {
					double newAcctTotal = depositAmt + rs.getDouble("accounttotal");
					ps = con.prepareStatement("UPDATE ACCOUNTS SET ACCOUNTTOTAL = ? WHERE ACCOUNTID = ?");
					ps.setDouble(1, newAcctTotal);
					ps.setString(2, acctId);
					ps.executeUpdate();
					System.out.println("You have deposited " + depositAmt + " into your account.");
					return true;
				} 
			}

		} catch (SQLException ex) {
			//throw new Exception("Bad things happened...");
		}
		return false;
	}

	public static boolean transfer(String fromAcctId, String toAcctId, double transferAmt){
		Connection con = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
			ps.setString(1, fromAcctId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("accountid").equals(fromAcctId)) {
					double newAcctTotal = rs.getDouble("accounttotal") - transferAmt;
					ps = con.prepareStatement("UPDATE ACCOUNTS SET ACCOUNTTOTAL = ? WHERE ACCOUNTID = ?");
					ps.setDouble(1, newAcctTotal);
					ps.setString(2, fromAcctId);
					ps.executeUpdate();
					// System.out.println("You have withdrawn " + withdrawAmt + " from your
					// account.");
				} else
					System.out.println("This account does not exist.");
			}

			PreparedStatement ps2 = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
			ps2.setString(1, toAcctId);
			ResultSet rs2 = ps2.executeQuery();

			while (rs2.next()) {
				if (rs2.getString("accountid").equals(toAcctId)) {
					double newAcctTotal = rs2.getDouble("accounttotal") + transferAmt;
					ps2 = con.prepareStatement("UPDATE ACCOUNTS SET ACCOUNTTOTAL = ? WHERE ACCOUNTID = ?");
					ps2.setDouble(1, newAcctTotal);
					ps2.setString(2, toAcctId);
					ps2.executeUpdate();
					System.out
							.println("You have transferred " + transferAmt + " from " + fromAcctId + " to " + toAcctId);
					return true;
				} else
					System.out.println("This account does not exist.");
			}

		} catch (SQLException ex) {
			//throw new Exception("Bad things happened...");
		}
		return false;
	}

//	public boolean createNewAccount(String accountType, double initialDeposit, int customerId) throws Exception {
//		Connection con = ConnectionUtil.getConnection();
//
//		try {
//			PreparedStatement ps = con.prepareStatement("INSERT INTO ACCOUNTS (accounttype, accounttotal]) VALUES (?, ?)");
//
//			ps.setString(1, accountType);
//			ps.setDouble(2, initialDeposit);
//			int add = ps.executeUpdate();
//
//			if (add == 1) {
//				System.out.println(add + " row has been added into accounts");
//				return true;
//			}
//
//		} catch (SQLException ex) {
//			throw new Exception("Bad things happened...");
//		}
//		return false;
//	}

	public static boolean createAccount(String accountType, double initialDeposit, int customerId) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "call create_account(?, ?, ?, ?)";
		CallableStatement ps = con.prepareCall(sql);
		ps.setString(1, accountType);
		ps.setDouble(2, initialDeposit);
		ps.setInt(3, customerId);
		ps.registerOutParameter(4, Types.VARCHAR);
		ps.execute();
		String id = ps.getString(4);
		currentAcctId = id;
		System.out.println("Your new account number is " + id);
		return true;
	}

	public static boolean createNewTransaction(double transactionAmt, String transactionType, String acctId)
	{
		Connection con = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO TRANSACTION (transactionid, transactionamount, transactiontype, accountid) VALUES (null, ?, ?, ?)");
			ps.setDouble(1, transactionAmt);
			ps.setString(2, transactionType);
			ps.setString(3, acctId);
			int add = ps.executeUpdate();

			if (add == 1) {
				System.out.println(add + " row has been added into transactions.");
				return true;
			}

		} catch (SQLException ex) {
			
		}
		return false;
	}

	public static boolean deleteAccount(String accountId) {
		Connection con = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
			ps.setString(1, accountId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("accountid").equals(accountId)) {
					ps = con.prepareStatement("DELETE FROM ACCOUNTS WHERE accountid = ? ");
					ps.setString(1, accountId);
					ps.executeUpdate();
					System.out.println("Account " + accountId + " has been closed.");
					return true;
				}
			}

		} catch (SQLException ex) {
		}
		return false;
	}
	
	public static List<Account> viewCustomerAccount(int custId) {
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			System.out.println(":(");

		}
		List<Account> listOfCustomerAcct = null;

		try {
			String sql = "select * FROM ACCOUNTS a INNER JOIN BANKCUSTOMERS c ON c.CUSTOMERID = a.CUSTOMERID WHERE c.CUSTOMERID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();

			listOfCustomerAcct = new LinkedList<Account>();

			while (rs.next()) {
				listOfCustomerAcct.add(new Account(rs.getString("accountid"), rs.getString("accounttype"),
						rs.getString("accountnumber"), rs.getDouble("accounttotal"), rs.getInt("customerid")));
			}

		} catch (SQLException e) {
		}
		for (Account accounts : listOfCustomerAcct) {
			System.out.println(accounts);
		}
		return listOfCustomerAcct;

		// log.traceExit(Optional.empty());
		// return Optional.empty();
		// return null;
	}
	
	public static List<Transactions> viewCustomerTransactions(int custId) {
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			System.out.println(":(");

		}
		List<Transactions> listOfCustomerTrans = null;

		try {
			String sql = "select c.CUSTOMERID, a.ACCOUNTID, t.TRANSACTIONID, t.TRANSACTIONTYPE, t.TRANSACTIONAMOUNT FROM BANKCUSTOMERS c INNER JOIN ACCOUNTS a ON c.CUSTOMERID = a.CUSTOMERID INNER JOIN TRANSACTION t ON a.ACCOUNTID = t.ACCOUNTID WHERE c.CUSTOMERID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();

			listOfCustomerTrans = new LinkedList<Transactions>();

			while (rs.next()) {
				listOfCustomerTrans.add(new Transactions(rs.getInt("TRANSACTIONID"), rs.getDouble("TRANSACTIONAMOUNT"),
						rs.getString("TRANSACTIONTYPE"), rs.getString("ACCOUNTID")));
			}

		} catch (SQLException e) {
		}
		for (Transactions transactions : listOfCustomerTrans) {
			System.out.println(transactions);
		}
		return listOfCustomerTrans;

		// log.traceExit(Optional.empty());
		// return Optional.empty();
		// return null;
	}
}
