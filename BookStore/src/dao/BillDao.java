package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.Bill;
import bean.Item;

public class BillDao {
	private Bill bill;

	public void setCart(Bill bill) {
		this.bill = bill;
	}

	public BillDao() {
		bill = new Bill();
	}

	public BillDao(Bill bill) {
		this.bill = bill;
	}

	/**
	 * Lưu hóa đơn
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void save() throws ClassNotFoundException, SQLException {

		java.sql.Date ngayMua = new java.sql.Date(bill.getDOP().getTime());
		boolean daThanhToan = bill.isPaid();
		int maKhachHang = bill.getCustomerId();

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 'FALSE'";
		ResultSet result = connectDB.executeQuery(query, new Object[] { maKhachHang });

		if (!result.next()) {
			query = "INSERT INTO HoaDon(MaKhachHang, NgayMua, DaThanhToan) VALUES(?,?,?)";
			connectDB.executeUpdate(query, new Object[] { maKhachHang, ngayMua, daThanhToan });
			result.close();
		}
		saveDetail(bill);

		connectDB.Disconnect();
	}

	/**
	 * Lưu chi tiết hóa đơn
	 * 
	 * @param bill Hóa đơn
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void saveDetail(Bill bill) throws ClassNotFoundException, SQLException {
		HashMap<String, Item> items = bill.getItems();

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "EXECUTE CapNhatChiTietHoaDon @pMaKhachHang = ?, @pMaSach = ?, @pSoLuongMua = ?";
		for (Map.Entry<String, Item> i : items.entrySet()) {
			connectDB.executeUpdate(query,
					new Object[] { bill.getCustomerId(), i.getValue().getBook().getId(), i.getValue().getQuantity() });
		}
	}

	/**
	 * Lấy mã hóa đơn theo mã khách hàng
	 * 
	 * @param customerId Mã khách hàng
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getIdByCustomerId(int customerId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT MaHoaDon FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 'FALSE'";
		int result = (int) connectDB.executeScalar(query, new Object[] { customerId });

		return result;
	}

	/**
	 * Lấy danh sách hóa đơn theo mã khách hàng
	 * 
	 * @param customerId Mã khách hàng
	 * @return ArrayList<Bill>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<Bill> getListByCustomerId(int customerId) throws ClassNotFoundException, SQLException {
		ArrayList<Bill> list = new ArrayList<>();

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ?";
		ResultSet result = connectDB.executeQuery(query, new Object[] { customerId });
		while (result.next()) {
			Bill bill = new Bill();
			bill.setId(result.getInt("MaHoaDon"));
			bill.setDOP(result.getDate("NgayMua"));
			bill.setPaid(result.getBoolean("DaThanhToan"));
			bill.setCustomerId(customerId);
			list.add(bill);
		}
		return list;
	}

	/**
	 * Thanh toán
	 * 
	 * @return boolean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean pay() throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "UPDATE HoaDon SET DaThanhToan = 1, NgayMua = ? WHERE MaKhachHang = ?";
		int result = connectDB.executeUpdate(query, new Object[] { bill.getDOP(), bill.getCustomerId() });
		connectDB.Disconnect();
		return result > 0;
	}
}
