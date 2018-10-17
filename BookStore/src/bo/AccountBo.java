package bo;

import java.sql.SQLException;

import bean.Account;
import dao.AccountDao;

public class AccountBo {
	
	private AccountDao accountDao;
	
	public AccountBo() {
		accountDao = new AccountDao();
	}
	
	public Account GetAccount(String userName, String password) throws ClassNotFoundException, SQLException {
		return accountDao.GetAccount(userName, password);
	}
	
	public boolean AddAccount(String userName, String password, String role) throws ClassNotFoundException, SQLException {
		return accountDao.AddAccount(userName, password, role);
	}
}
