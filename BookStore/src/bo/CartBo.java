package bo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import bean.Book;
import bean.Cart;
import bean.Item;
import dao.CartDao;

public class CartBo {
	private Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public CartBo() {
		cart = new Cart();
	}
	
	public CartBo(Cart cart) {
		this.cart = cart;
	}
	
	public void addToCart(Book book) {
		String bookId = book.getId();
		
		if(cart.getItems().containsKey(bookId)) {
			int oldQuality = cart.getItems().get(bookId).getQuality();
			cart.getItems().get(bookId).setQuality(oldQuality + 1);
		}
		else {
			cart.getItems().put(book.getId(), new Item(book, 1));
		}
	}
	
	public void updateQuantity(String bookId, int quantity) {
		boolean check = cart.getItems().containsKey(bookId);
		if (check) {
			cart.getItems().get(bookId).setQuality(quantity);
		}
	}
	
	public void removeFromCart(String key) {
		if(cart.getItems().containsKey(key)) {
			cart.getItems().remove(key);
		}
	}
	
	public int getTotalItem() {
		return cart.getItems().size();
	}
	
	public long getTotalPrice() {
		long totalPrice = 0;
		for(Map.Entry<String, Item> s : cart.getItems().entrySet()) {
			totalPrice += s.getValue().getQuality() * s.getValue().getBook().getPrice();
		}
		return totalPrice;
	}
	
	public void luuHoaDon() throws ClassNotFoundException, SQLException {
		Date ngayMua = new Date(System.currentTimeMillis());
		cart.setNgayMua(ngayMua);
		
		CartDao cartDao = new CartDao(cart);
		cartDao.luuHoaDon();
	}
	
	public boolean thanhToan() throws ClassNotFoundException, SQLException {
		Date ngayMua = new Date(System.currentTimeMillis());
		cart.setNgayMua(ngayMua);
		
		CartDao cartDao = new CartDao(cart);
		return cartDao.thanhToan();
	}
	
	public ArrayList<Cart> getDanhSachHoaDon(int maKhachHang) throws ClassNotFoundException, SQLException {
		CartDao cartDao = new CartDao(cart);
		return cartDao.getDanhSachHoaDon(maKhachHang);
	}
}
