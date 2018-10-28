package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Account;

public class AccountDao {
	private final ConnectDB connectDB;
	
	public AccountDao() {
		connectDB = new ConnectDB();
	}
	
	public Account getAccount(String userName, String password) throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		
		String query = "SELECT * FROM TaiKhoan WHERE TenTaiKhoan = ? AND MatKhau = ?";
		ResultSet resultSet = connectDB.executeQuery(query, new Object[] { userName, password });
		
		Account account = null;
		if(resultSet.next()) {
			String role = resultSet.getString("VaiTro");
			account = new Account(userName, password, role);
		}
		resultSet.close();
		return account;
	}
	
	
	public boolean addAccount(String userName, String password, String role) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		
		String query = "INSERT INTO TaiKhoan VALUES(?,?,?)";
		int result = connectDB.executeUpdate(query, new Object[] { userName, password, role });
		return result > 0;
	}
}
