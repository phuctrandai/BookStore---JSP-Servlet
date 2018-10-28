package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Category;;

public class CategoryDao {
	private ConnectDB connectDB;

	public CategoryDao() {
		connectDB = new ConnectDB();
	}
	
	/**
	 * Lấy danh sách tất cả các loại sách
	 * @return ArrayList<Category>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<Category> getList() throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		ArrayList<Category> list = new ArrayList<Category>();
		ResultSet result = connectDB.getTable("Loai");

		while (result.next()) {
			Category category = new Category(result.getString(1), result.getString(2));
			list.add(category);
		}
		result.close();
		connectDB.Disconnect();
		return list;
	}
}
