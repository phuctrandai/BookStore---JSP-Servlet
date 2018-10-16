package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import bean.Book;

public class BookDao {
	
	/***
	 * Fields
	 */
	private final ConnectDB connectDB;
	
	/**
	 * Contructors
	 */
	public BookDao() {
		connectDB = new ConnectDB();
	}
	
	/**
	 * Get books from table sach
	 * @return HashMap<String, Book> result
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getBookList(int pageNumber, int bookPerPage) throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		
		PreparedStatement pStatement;
		ResultSet resultSet;
		if(pageNumber > 0) {
			pStatement = connectDB.connection.prepareStatement("SELECT * FROM [dbo].[GetBookOfPage] (?, ?)");
			pStatement.setInt(1, pageNumber);
			pStatement.setInt(2, bookPerPage);
			resultSet = pStatement.executeQuery();
		}
		else {
			resultSet = connectDB.getTable("Sach");
		}
		
		HashMap<String, Book> bookList = new HashMap<String, Book>();
		
		while(resultSet.next()) {
			Book book = new Book();
			book.setId(resultSet.getString("MaSach"));
			book.setName(resultSet.getString("TenSach"));
			book.setAuthor(resultSet.getString("TacGia"));
			book.setImage(resultSet.getString("Anh"));
			book.setPrice(resultSet.getLong("Gia"));
			book.setChapNumber(resultSet.getInt("SoTap"));
			book.setQuantity(resultSet.getLong("SoLuong"));
			book.setInputDate(resultSet.getDate("NgayNhap"));
			book.setCategoryId(resultSet.getString("MaLoai"));
			bookList.put(book.getId(), book);
		}
		resultSet.close();
		connectDB.Disconnect();
		return bookList;
	}
	
	public HashMap<String, Book> getBookList() throws ClassNotFoundException, SQLException {
		return getBookList(0, 0);
	}
	
	/**
	 * Get book by id
	 * @param bookId
	 * @return Book book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Book getBookById(String bookId) throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		
		PreparedStatement pStatement = connectDB.connection.prepareStatement("SELECT * FROM sach WHERE MaSach = ?");
		pStatement.setString(1, bookId);
		ResultSet result = pStatement.executeQuery();
		
		Book book = null;
		
		if(result.next()) {
			book = new Book();
			book.setId(result.getString("MaSach"));
			book.setName(result.getString("TenSach"));
			book.setAuthor(result.getString("TacGia"));
			book.setImage(result.getString("Anh"));
			book.setPrice(result.getLong("Gia"));
			book.setChapNumber(result.getInt("SoTap"));
			book.setQuantity(result.getLong("SoLuong"));
			book.setInputDate(result.getDate("NgayNhap"));
			book.setCategoryId(result.getString("MaLoai"));
		}
		result.close();
		connectDB.Disconnect();
		
		return book;
	}
	
	/**
	 * Get books by the category id
	 * @param categoryId
	 * @return ArrayList<Book> result
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getBooksByCategoryId(int pageNumber, String categoryId, int bookPerPage) throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		
		PreparedStatement pStatement = connectDB.connection.prepareStatement("SELECT * FROM [dbo].[GetBookOfPageByCategory] (?, ?, ?)");
		pStatement.setString(1, categoryId);
		pStatement.setInt(2, pageNumber);
		pStatement.setInt(3, bookPerPage);
		ResultSet resultSet = pStatement.executeQuery();
		
		HashMap<String, Book> bookList = new HashMap<String, Book>();
		
		while(resultSet.next()) {
			Book book = new Book();
			book.setId(resultSet.getString("MaSach"));
			book.setName(resultSet.getString("TenSach"));
			book.setAuthor(resultSet.getString("TacGia"));
			book.setImage(resultSet.getString("Anh"));
			book.setPrice(resultSet.getLong("Gia"));
			book.setChapNumber(resultSet.getInt("SoTap"));
			book.setQuantity(resultSet.getLong("SoLuong"));
			book.setInputDate(resultSet.getDate("NgayNhap"));
			book.setCategoryId(resultSet.getString("MaLoai"));
			bookList.put(book.getId(), book);
		}
		resultSet.close();
		connectDB.Disconnect();
		return bookList;
	}
	
	public int getTotalBook(String categoryId) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		PreparedStatement pStatement;
		if(categoryId == "")
			pStatement = connectDB.connection.prepareStatement("SELECT COUNT(MaSach) FROM sach");
		else {
			pStatement = connectDB.connection.prepareStatement("SELECT COUNT(MaSach) FROM sach WHERE MaLoai = ?");
			pStatement.setString(1, categoryId);
		}
		ResultSet resultSet = pStatement.executeQuery();
		
		int result = 0;
		if(resultSet.next()) result = resultSet.getInt(1);
		
		return result;
	}
	
	public int getTotalBook() throws ClassNotFoundException, SQLException {
		return getTotalBook("");
	}
}
