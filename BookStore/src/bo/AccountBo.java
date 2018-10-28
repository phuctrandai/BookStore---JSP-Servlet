package bo;

import java.sql.SQLException;

import bean.Account;
import dao.AccountDao;

public class AccountBo {
	
	private AccountDao accountDao;
	
	public AccountBo() {
		accountDao = new AccountDao();
	}
	
	public Account getAccount(String userName, String password) throws ClassNotFoundException, SQLException {
		return accountDao.getAccount(userName, password);
	}
	
	public boolean addAccount(String userName, String password, String role) throws ClassNotFoundException, SQLException {
		return accountDao.addAccount(userName, password, role);
	}
}
