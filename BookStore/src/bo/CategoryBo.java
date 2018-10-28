package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Category;
import dao.CategoryDao;

public class CategoryBo {
	private CategoryDao loaiDao;
	
	public CategoryBo() {
		loaiDao = new CategoryDao();
	}
	
	public ArrayList<Category> getList() throws ClassNotFoundException, SQLException {
		return loaiDao.getList();
	}
}
