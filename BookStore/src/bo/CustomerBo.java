package bo;

import java.sql.SQLException;

import bean.Account;
import bean.Customer;
import dao.CustomerDao;

public class CustomerBo {
	private CustomerDao customerDao;
	private AccountBo accountBo;
	
	public CustomerBo() {
		customerDao = new CustomerDao();
		accountBo = new AccountBo();
	}
	
	public Customer GetCustomer(String accountName) throws SQLException, ClassNotFoundException {
		return customerDao.GetCustomer(accountName);
	}
	
	public boolean AddCustomer(String name, String address, String phoneNumber, String email, String userName, String password, String role) throws ClassNotFoundException, SQLException {
		boolean result = false;
		
		Account account = new Account(userName, password, role);
		result = accountBo.AddAccount(userName, password, role);
		
		if(result) {
			result = customerDao.AddCustomer(name, address, phoneNumber, email, account);
		}
		
		return result;
	}
}
