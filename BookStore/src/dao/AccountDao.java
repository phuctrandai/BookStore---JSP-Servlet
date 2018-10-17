package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Account;

public class AccountDao {
	/***
	 * Fields
	 */
	private final ConnectDB connectDB;
	
	/**
	 * Contructors
	 */
	public AccountDao() {
		connectDB = new ConnectDB();
	}
	
	public Account GetAccount(String userName, String password) throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		
		String query = "SELECT * FROM TaiKhoan WHERE TenTaiKhoan = ? AND MatKhau = ?";
		PreparedStatement pStatement = connectDB.connection.prepareStatement(query);
		pStatement.setString(1, userName);
		pStatement.setString(2, password);
		
		ResultSet resultSet = pStatement.executeQuery();
		
		Account account = null;
		if(resultSet.next()) {
			String role = resultSet.getString("VaiTro");
			account = new Account(userName, password, role);
		}
		resultSet.close();
		return account;
	}
	
	
	public boolean AddAccount(String userName, String password, String role) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		
		String query = "INSERT INTO TaiKhoan VALUES(?,?,?)";
		PreparedStatement pStatement = connectDB.connection.prepareStatement(query);
		pStatement.setString(1, userName);
		pStatement.setString(2, password);
		pStatement.setString(3, role);
		
		int result = pStatement.executeUpdate();
		return result > 0;
	}
}
