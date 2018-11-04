package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bean.Bill;
import bean.Book;
import bean.Item;

public class BillDao {
	
	public BillDao() {	}

//============ SELECT =================
	/*
	 * Lấy hóa đơn chưa thanh toán
	 */
	public Bill getBill(int customerId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		Bill bill = new Bill();
		
		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 0";
		ResultSet result = connectDB.executeQuery(query, new Object[] { customerId });
		if (result.next()) {
			bill.setId(result.getInt("MaHoaDon"));
			bill.setDOP(result.getDate("NgayMua"));
			bill.setPaid(result.getBoolean("DaThanhToan"));
			bill.setCustomerId(customerId);
			bill.setItems(getDetail(bill.getId()));
		}
		connectDB.Disconnect();
		return bill;
	}
	
	/**
	 * Lấy danh sách hóa đơn đã thanh toán
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

		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ? ORDER BY DaThanhToan";
		ResultSet result = connectDB.executeQuery(query, new Object[] { customerId });
		while (result.next()) {
			Bill bill = new Bill();
			bill.setId(result.getInt("MaHoaDon"));
			bill.setDOP(result.getDate("NgayMua"));
			bill.setPaid(result.getBoolean("DaThanhToan"));
			bill.setCustomerId(customerId);
			bill.setItems(getDetail(bill.getId()));
			list.add(bill);
		}
		
		connectDB.Disconnect();
		return list;
	}
	
	/**
	 * Lấy thông tin chi tiết hóa đơn
	 * @param billId Mã hóa đơn
	 * @return HashMap<String, Item> 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Item> getDetail(int billId) throws ClassNotFoundException, SQLException {
		HashMap<String, Item> list = new HashMap<>();
		
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		String query = "SELECT ChiTietHoaDon.SoLuongMua, Sach.TenSach, Sach.TacGia, Sach.MaSach, Sach.Anh, Sach.Gia, Sach.SoTap"
				+ " FROM ChiTietHoaDon JOIN Sach ON ChiTietHoaDon.MaSach = Sach.MaSach WHERE MaHoaDon = ?";
		ResultSet resultSet = connectDB.executeQuery(query, new Object[] { billId });
		while(resultSet.next()) {
			Book book = new Book();
			book.setId(resultSet.getString("MaSach"));
			book.setName(resultSet.getString("TenSach"));
			book.setAuthor(resultSet.getString("TacGia"));
			book.setImage(resultSet.getString("Anh"));
			book.setChapNumber(resultSet.getInt("SoTap"));
			book.setPrice(resultSet.getLong("Gia"));
			
			Item item = new Item(book, resultSet.getInt("SoLuongMua"));
			list.put(book.getId(), item);
		}
		connectDB.Disconnect();
		return list;
	}

//============ INSERT =================
	public void insert(String bookId, int quantity, int customerId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 'FALSE'";
		ResultSet result = connectDB.executeQuery(query, new Object[] { customerId });

		if (!result.next()) {
			java.sql.Date ngayMua = new java.sql.Date(new Date().getTime());
			query = "INSERT INTO HoaDon(MaKhachHang, NgayMua, DaThanhToan) VALUES(?,?,?)";
			connectDB.executeUpdate(query, new Object[] { customerId, ngayMua, false });
			result.close();
		}
		
		query = "EXECUTE CapNhatChiTietHoaDon @pMaKhachHang = ?, @pMaSach = ?, @pSoLuongMua = ?";
		connectDB.executeUpdate(query, new Object[] { customerId, bookId, quantity });
		
		connectDB.Disconnect();
	}

	/**
	 * Lưu hóa đơn
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void save(int customerId, HashMap<String, Item> items) throws ClassNotFoundException, SQLException {

		java.sql.Date ngayMua = new java.sql.Date(new Date().getTime());

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 'FALSE'";
		ResultSet result = connectDB.executeQuery(query, new Object[] { customerId });

		if (!result.next()) {
			query = "INSERT INTO HoaDon(MaKhachHang, NgayMua, DaThanhToan) VALUES(?,?,?)";
			connectDB.executeUpdate(query, new Object[] { customerId, ngayMua, false });
			result.close();
		}
		// Luu chi tiet hoa don
		for (Map.Entry<String, Item> i : items.entrySet()) {
			insert(i.getKey(), i.getValue().getQuantity(), customerId);
		}
		connectDB.Disconnect();
	}
	
//============ UPDATE =================
	public void updateQuantity(String bookId, int quantity, int customerId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		String query = "UPDATE ChiTietHoaDon SET SoLuongMua = ? FROM ChiTietHoaDon INNER JOIN HoaDon ON ChiTietHoaDon.MaHoaDon = HoaDon.MaHoaDon\r\n" + 
				"WHERE (ChiTietHoaDon.MaSach = ?) AND (HoaDon.MaKhachHang = ?)";
		connectDB.executeUpdate(query, new Object[] { quantity, bookId, customerId});
		
		connectDB.Disconnect();
	}
	
	/**
	 * Thanh toán
	 * 
	 * @return boolean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean pay(java.sql.Date dOP, int customerId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "UPDATE HoaDon SET DaThanhToan = 1, NgayMua = ? WHERE MaKhachHang = ? AND DaThanhToan = 0";
		int result = connectDB.executeUpdate(query, new Object[] { dOP, customerId });
		connectDB.Disconnect();
		return result > 0;
	}

//============ DELETE ============
	public void delete(int billId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		String query = "DELETE FROM ChiTietHoaDon WHERE MaHoaDon = ?";
		connectDB.executeUpdate(query, new Object[] { billId });
		
		query = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
		connectDB.executeUpdate(query, new Object[] { billId });
		
		connectDB.Disconnect();
	}
		
	public void removeBook(int customerId, String bookId) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();
		
		String query = "DELETE FROM ChiTietHoaDon\r\n" + 
				"FROM ChiTietHoaDon INNER JOIN HoaDon ON ChiTietHoaDon.MaHoaDon = HoaDon.MaHoaDon\r\n" + 
				"WHERE(HoaDon.MaKhachHang = ?) AND (ChiTietHoaDon.MaSach = ?) AND (HoaDon.DaThanhToan = 0)";
		connectDB.executeUpdate(query, new Object[] { customerId, bookId });
		connectDB.Disconnect();
	}
}
