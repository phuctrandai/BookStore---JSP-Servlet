package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Account;
import bean.Customer;

public class CustomerDao {
	private final ConnectDB connectDB;

	public CustomerDao() {
		connectDB = new ConnectDB();
	}

	/**
	 * Lấy khách hàng theo tên tài khoản đăng nhập
	 * 
	 * @param accountName Tên tài khoản đăng nhập
	 * @return Customer
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Customer getCustomer(String accountName) throws SQLException, ClassNotFoundException {
		connectDB.Connect();

		String query = "SELECT KhachHang.*, MatKhau, VaiTro FROM KhachHang JOIN TaiKhoan ON KhachHang.TenTaiKhoan = TaiKhoan.TenTaiKhoan\r\n"
				+ "WHERE TaiKhoan.TenTaiKhoan = ?";
		ResultSet resultSet = connectDB.executeQuery(query, new Object[] { accountName });

		Customer customer = null;
		if (resultSet.next()) {
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
		connectDB.Disconnect();
		return customer;
	}

	/**
	 * Tạo tài khoản khách hàng
	 * 
	 * @param name        Tên
	 * @param address     Địa chỉ
	 * @param phoneNumber Số điện thoại
	 * @param email       Email
	 * @param account     Tài khoản
	 * @return boolean
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean addCustomer(String name, String address, String phoneNumber, String email, Account account)
			throws SQLException, ClassNotFoundException {
		connectDB.Connect();

		String query = "INSERT INTO [QLSach].[dbo].[KhachHang](HoVaTen, DiaChi, SoDienThoai, Email, TenTaiKhoan) VALUES(?,?,?,?,?)";

		int result = connectDB.executeUpdate(query,
				new Object[] { name, address, phoneNumber, email, account.getUserName() });
		connectDB.Disconnect();
		return result > 0;
	}
}
