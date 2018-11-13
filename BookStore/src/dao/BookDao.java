package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import bean.Book;

public class BookDao {

	private final ConnectDB connectDB;

	public BookDao() {
		connectDB = new ConnectDB();
	}

	/**
	 * Lấy danh sách các quyển sách ở trang pageNumber với số lượng là bookPerPage.
	 * Nếu pageNumber bằng 0 thì sẽ lấy tất cả sách
	 * 
	 * @param pageNumber  Số trang
	 * @param bookPerPage Số lượng sách trên mỗi trang
	 * @return HashMap<String, Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getList(int pageNumber, int bookPerPage) throws ClassNotFoundException, SQLException {
		connectDB.Connect();

		ResultSet resultSet;
		// Get by page number
		if (pageNumber > 0) {
			resultSet = connectDB.executeQuery("SELECT * FROM [dbo].[GetBookOfPage] (?, ?)",
					new Object[] { pageNumber, bookPerPage });
		}
		// Get all books
		else {
			resultSet = connectDB.getTable("Sach");
		}

		HashMap<String, Book> bookList = new HashMap<String, Book>();

		while (resultSet.next()) {
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

	/**
	 * Lấy tất cả sách hiện có
	 * 
	 * @return HashMap<String, Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getList() throws ClassNotFoundException, SQLException {
		return getList(0, 0);
	}

	/**
	 * Lấy sách theo mã sách
	 * 
	 * @param bookId Mã sách
	 * @return Book book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Book getById(String id) throws ClassNotFoundException, SQLException {
		connectDB.Connect();

		ResultSet result = connectDB.executeQuery("SELECT * FROM sach WHERE MaSach = ?", new Object[] { id });

		Book book = null;

		if (result.next()) {
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
	 * Lấy sách theo mã loại
	 * 
	 * @param categoryId Mã loại
	 * @return ArrayList<Book>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap<String, Book> getByCategoryId(String categoryId, int pageNumber, int bookPerPage)
			throws ClassNotFoundException, SQLException {
		connectDB.Connect();

		ResultSet resultSet = connectDB.executeQuery("SELECT * FROM [dbo].[GetBookOfPageByCategory] (?, ?, ?)", new Object[] { categoryId, pageNumber, bookPerPage });

		HashMap<String, Book> bookList = new HashMap<String, Book>();

		while (resultSet.next()) {
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

	/**
	 * Lấy tổng số lượng sách theo mã loại
	 * 
	 * @param categoryId Mã loại
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getTotalBook(String categoryId) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		
		String query;
		Object[] params = null;
		if (categoryId == "") {
			query = "SELECT COUNT(MaSach) FROM sach";
		}
		else {
			query = "SELECT COUNT(MaSach) FROM sach WHERE MaLoai = ?";
			params = new Object[] { categoryId };
		}
		int result = (int) connectDB.executeScalar(query, params);
		connectDB.Disconnect();
		return result;
	}
	
	/**
	 * Lấy tổng số lượng sách hiện có
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getTotalBook() throws ClassNotFoundException, SQLException {
		return getTotalBook("");
	}
	
	/**
	 * Lấy tổng số lượng sách theo tu khoa
	 * 
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getTotalBookByKeyWord(String keyword) throws SQLException, ClassNotFoundException {
		connectDB.Connect();
		int result = 0;
		
		
		
		connectDB.Disconnect();
		return result;
	}
}
