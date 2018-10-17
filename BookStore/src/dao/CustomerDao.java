package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Account;
import bean.Customer;

public class CustomerDao {
	/***
	 * Fields
	 */
	private final ConnectDB connectDB;
	
	/**
	 * Contructors
	 */
	public CustomerDao() {
		connectDB = new ConnectDB();
	}
	
	public Customer GetCustomer(String accountName) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		
		PreparedStatement pStatement;
		ResultSet resultSet;
		
		String query = "SELECT KhachHang.*, MatKhau, VaiTro FROM KhachHang JOIN TaiKhoan ON KhachHang.TenTaiKhoan = TaiKhoan.TenTaiKhoan\r\n" + 
				"WHERE TaiKhoan.TenTaiKhoan = ?";
		pStatement = connectDB.connection.prepareStatement(query);
		pStatement.setString(1, accountName);
		resultSet = pStatement.executeQuery();
		
		Customer customer = null;
		if(resultSet.next()) {
			int id = resultSet.getInt("MaKhachHang");
			String name = resultSet.getString("HoVaTen");
			String address = resultSet.getString("DiaChi");
			String phoneNumber = resultSet.getString("SoDienThoai");
			String email = resultSet.getString("Email");
			String userName = resultSet.getString("TenTaiKhoan");
			String password = resultSet.getString("MatKhau");
			String role = resultSet.getString("VaiTro");
			Account account = new Account(userName, password, role);
			
			customer = new Customer(id, name, address, phoneNumber, email, account);
		}
		resultSet.close();
		return customer;
	}
	
	public boolean AddCustomer(String name, String address, String phoneNumber, String email, Account account) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		
		String query = "INSERT INTO [QLSach].[dbo].[KhachHang](HoVaTen, DiaChi, SoDienThoai, Email, TenTaiKhoan) VALUES(?,?,?,?,?)";
		PreparedStatement pStatement = connectDB.connection.prepareStatement(query);
		pStatement.setString(1, name);
		pStatement.setString(2, address);
		pStatement.setString(3, phoneNumber);
		pStatement.setString(4, email);
		pStatement.setString(5, account.getUserName());
		
		int result = pStatement.executeUpdate();
		return result > 0;
	}
}
