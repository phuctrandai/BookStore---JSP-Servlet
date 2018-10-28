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
	
	public Customer getCustomer(String accountName) throws SQLException, ClassNotFoundException {
		return customerDao.getCustomer(accountName);
	}
	
	public boolean addCustomer(String name, String address, String phoneNumber, String email, String userName, String password, String role) throws ClassNotFoundException, SQLException {
		boolean result = false;
		
		Account account = new Account(userName, password, role);
		result = accountBo.addAccount(userName, password, role);
		
		if(result) {
			result = customerDao.addCustomer(name, address, phoneNumber, email, account);
		}
		
		return result;
	}
}
