package bo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.Book;
import dao.BookDao;

public class BookBo {
	private final BookDao bookDao;
	private HashMap<String, Book> bookList;

	public BookBo() {
		bookDao = new BookDao();
	}

	/**
	 * Lấy sách hiển thị ở trang thứ pageNumber với số lượng sách trên trang là bookPerPage
	 * 
	 * @param pageNumber Số trang đang hiển thị
	 * @param bookPerPage Số lượng sách trên mỗi trang
	 * @return HashMap<String, Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getList(int pageNumber, int bookPerPage) throws ClassNotFoundException, SQLException {
		bookList = bookDao.getList(pageNumber, bookPerPage);
		return bookList;
	}

	/**
	 * Lấy sách theo mã sách
	 * 
	 * @param bookId Mã sách
	 * @return Book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Book getById(String bookId) throws ClassNotFoundException, SQLException {
		return bookDao.getById(bookId);
	}

	/**
	 * Tìm kiếm theo tên sách
	 * 
	 * @param bookName Tên sách
	 * @return HashMap<String, Book>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public HashMap<String, Book> findByName(String bookName) throws ClassNotFoundException, SQLException {
		HashMap<String, Book> result = new HashMap<String, Book>();
		HashMap<String, Book> allBook = bookDao.getList();

		for (Map.Entry<String, Book> item : allBook.entrySet()) {
			if (item.getValue().getName().toLowerCase().contains(bookName.toLowerCase()))
				result.put(item.getKey(), item.getValue());
		}

		return result;
	}

	/**
	 * Lấy danh sách sách theo mã loại
	 * 
	 * @param categoryId Mã loại
	 * @return HashMap<String, Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getByCategoryId(String categoryId, int pageNumber, int bookPerPage)
			throws ClassNotFoundException, SQLException {
		bookList = bookDao.getByCategoryId(categoryId, pageNumber, bookPerPage);
		return bookList;
	}

	/**
	 * Tính tổng số lượng trang hiển thị sách theo mã loại sách
	 * 
	 * @param bookPerPage Số sách mỗi trang
	 * @param categoryId Mã loại sách
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalPage(int bookPerPage, String categoryId) throws ClassNotFoundException, SQLException {
		int totalBook;
		if (categoryId == "")
			totalBook = bookDao.getTotalBook();
		else
			totalBook = bookDao.getTotalBook(categoryId);

		if (bookPerPage > 0) {
			if (totalBook % bookPerPage != 0)
				return (totalBook / bookPerPage + 1);
			else
				return (totalBook / bookPerPage);
		}
		return 1;
	}
	
	/**
	 * Tính tổng số lượng trang hiển thị sách của tất cả sách
	 * 
	 * @param bookPerPage Số lượng sách mỗi trang
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalPage(int bookPerPage) throws ClassNotFoundException, SQLException {
		return getTotalPage(bookPerPage, "");
	}
}
