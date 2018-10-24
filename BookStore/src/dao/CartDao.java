package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.Cart;
import bean.Item;

public class CartDao {
	private Cart cart;

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public CartDao() {
		cart = new Cart();
	}

	public CartDao(Cart cart) {
		this.cart = cart;
	}

	public void luuHoaDon() throws ClassNotFoundException, SQLException {

		java.sql.Date ngayMua = new java.sql.Date(cart.getNgayMua().getTime());
		boolean daThanhToan = cart.isDaThanhToan();
		int maKhachHang = cart.getMaKhachHang();

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT * FROM HoaDon WHERE MaKhachHang = ?";
		ResultSet result = connectDB.executeQuery(query, new Object[] { maKhachHang });

		if (!result.next()) {
			query = "INSERT INTO HoaDon(MaKhachHang, NgayMua, DaThanhToan) VALUES(?,?,?)";
			connectDB.executeUpdate(query, new Object[] { maKhachHang, ngayMua, daThanhToan });
			result.close();
		}
		luuChiTietHoaDon(cart);

		connectDB.Disconnect();
	}

	public void luuChiTietHoaDon(Cart cart) throws ClassNotFoundException, SQLException {
		int maHoaDon = getMaHoaDonTheoMaKhachHang(cart.getMaKhachHang());
		cart.setMaHoaDon(maHoaDon);
		HashMap<String, Item> items = cart.getItems();

		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "INSERT INTO ChiTietHoaDon(MaSach, SoLuongMua, MaHoaDon) VALUES(?,?,?)";
		for (Map.Entry<String, Item> i : items.entrySet()) {
			connectDB.executeUpdate(query,
					new Object[] { i.getValue().getBook().getId(), i.getValue().getQuality(), maHoaDon });
		}
	}

	public int getMaHoaDonTheoMaKhachHang(int maKhachHang) throws ClassNotFoundException, SQLException {
		ConnectDB connectDB = new ConnectDB();
		connectDB.Connect();

		String query = "SELECT MaHoaDon FROM HoaDon WHERE MaKhachHang = ? AND DaThanhToan = 'FALSE'";
		int result = (int) connectDB.executeScalar(query, new Object[] { maKhachHang });

		return result;
	}
}
