package bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import bean.Book;
import dao.BookDao;

public class BookBo {
	/**
	 * Fields
	 */
	private final BookDao bookDao;
	private HashMap<String, Book> bookList;
	
	/**
	 * Contructors
	 */
	public BookBo() {
		bookDao = new BookDao();
	}
	
	/**
	 * Get books
	 * @return ArrayList<Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getBookList(int pageNumber, int bookPerPage) throws ClassNotFoundException, SQLException {
		bookList =  bookDao.getBookList(pageNumber, bookPerPage);
		return bookList;
	}
	
	/**
	 * Get book by id
	 * @param bookId
	 * @return Book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Book getBookById(String bookId) throws ClassNotFoundException, SQLException {
		return bookDao.getBookById(bookId);
	}
	
	/**
	 * Find book by name
	 * @param bookList
	 * @param bookName
	 * @return ArrayList<Book>
	 */
	public ArrayList<Book> findByName(ArrayList<Book> bookList, String bookName) {
		ArrayList<Book> result = null;
		for(Book b : bookList) {
			if(b.getName().contains(bookName)) {
				if(result == null) {
					result = new ArrayList<Book>();
				}
				result.add(b);
			}
		}
		return result;
	}
	
	/**
	 * Get books by category id
	 * @param categoryId
	 * @return ArrayList<Book> bookList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getBooksByCategoryId(int pageNumber, int bookPerPage, String categoryId) throws ClassNotFoundException, SQLException {
		bookList = bookDao.getBooksByCategoryId(pageNumber, categoryId, bookPerPage);
		return bookList;
	}
	
	/**
	 * Get total page
	 * @param bookPerRow
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalPage(int bookPerPage, String categoryId ) throws ClassNotFoundException, SQLException {
		int totalBook;
		if(categoryId == "")
			totalBook = bookDao.getTotalBook();
		else
			totalBook = bookDao.getTotalBook(categoryId);
		
		if(bookPerPage > 0) {
			if(totalBook % bookPerPage != 0) return (totalBook / bookPerPage + 1);
			else return (totalBook / bookPerPage);
		}
		return 1;
	}
	
	public int getTotalPage(int bookPerPage) throws ClassNotFoundException, SQLException {
		return getTotalPage(bookPerPage, "");
	}
}
