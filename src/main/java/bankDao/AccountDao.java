package bankDao;

import java.util.List;

import BankingApp.Transactions;
import bankModels.Account;


public interface AccountDao {
	List<Account> getAllAccounts() throws Exception;
	List<Transactions> getAllTransactions() throws Exception;
}
