package bo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import bean.Bill;
import bean.Book;
import bean.Item;
import dao.BillDao;

public class BillBo {
	private Bill bill;

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

	public void addToBill(Book book) {
		String bookId = book.getId();

		if (bill.getItems().containsKey(bookId)) {
			int oldQuality = bill.getItems().get(bookId).getQuantity();
			bill.getItems().get(bookId).setQuantity(oldQuality + 1);
		} else {
			bill.getItems().put(book.getId(), new Item(book, 1));
		}
	}

	public void updateQuantity(String bookId, int quantity) {
		boolean check = bill.getItems().containsKey(bookId);
		if (check) {
			bill.getItems().get(bookId).setQuantity(quantity);
		}
	}

	public void removeFromBill(String key) {
		if (bill.getItems().containsKey(key)) {
			bill.getItems().remove(key);
		}
	}

	public void saveBill() throws ClassNotFoundException, SQLException {
		Date dOP = new Date(System.currentTimeMillis());
		bill.setDOP(dOP);

		BillDao billDao = new BillDao(bill);
		billDao.save();
	}

	public boolean payBill() throws ClassNotFoundException, SQLException {
		Date dOP = new Date(System.currentTimeMillis());
		bill.setDOP(dOP);

		BillDao billDao = new BillDao(bill);
		return billDao.pay();
	}

	public ArrayList<Bill> getListByCustomerId(int customerId) throws ClassNotFoundException, SQLException {
		BillDao billDao = new BillDao();
		return billDao.getListByCustomerId(customerId);
	}
}
