package bo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import bean.Bill;
import bean.Book;
import bean.Item;
import dao.BillDao;

public class BillBo {
	private Bill bill;

	public Bill getBill(int customerId) throws ClassNotFoundException, SQLException {
		BillDao billDao = new BillDao();
		return billDao.getBill(customerId);
	}
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public BillBo() {
		bill = new Bill();
	}

	public BillBo(Bill bill) {
		this.bill = bill;
	}

	public void addToBill(Book book, boolean isLogged, int customerId) throws ClassNotFoundException, SQLException {
		String bookId = book.getId();
		if(isLogged) {
			BillDao billDao = new BillDao();
			billDao.insert(bookId, 1, customerId);
		}
		else {
			if (bill.getItems().containsKey(bookId)) {
				int oldQuality = bill.getItems().get(bookId).getQuantity();
				bill.getItems().get(bookId).setQuantity(oldQuality + 1);
			} else {
				bill.getItems().put(book.getId(), new Item(book, 1));
			}
		}
	}

	public void updateQuantity(String bookId, int quantity, boolean isLogged, int customerId) throws ClassNotFoundException, SQLException {
		if(isLogged) {
			BillDao billDao = new BillDao();
			billDao.updateQuantity(bookId, quantity, customerId);
		} else {
			boolean check = bill.getItems().containsKey(bookId);
			if (check) {
				bill.getItems().get(bookId).setQuantity(quantity);
			}
		}
	}

	public void removeBook(int customerId, String bookId, boolean isLogged) throws ClassNotFoundException, SQLException {
		if(isLogged) {
			BillDao billDao = new BillDao();
			billDao.removeBook(customerId, bookId);
		} else {
			if (bill.getItems().containsKey(bookId)) {
				bill.getItems().remove(bookId);
			}
		}
	}

	public void saveBill(int customerId, HashMap<String, Item> items) throws ClassNotFoundException, SQLException {
		BillDao billDao = new BillDao();
		billDao.save(customerId, items);
	}
	
	public void deleteBill(int billId) throws ClassNotFoundException, SQLException {
		BillDao billDao = new BillDao();
		billDao.delete(billId);
	}

	public boolean payBill(int customerId) throws ClassNotFoundException, SQLException {
		Date dOP = new Date(System.currentTimeMillis());
		bill.setDOP(dOP);

		BillDao billDao = new BillDao();
		return billDao.pay(dOP, customerId);
	}

	public ArrayList<Bill> getListByCustomerId(int customerId) throws ClassNotFoundException, SQLException {
		BillDao billDao = new BillDao();
		return billDao.getListByCustomerId(customerId);
	}
}
