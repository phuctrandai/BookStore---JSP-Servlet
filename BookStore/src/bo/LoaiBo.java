package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Loai;
import dao.LoaiDao;

public class LoaiBo {
	private LoaiDao loaiDao;
	
	public LoaiBo() {
		loaiDao = new LoaiDao();
	}
	
	public ArrayList<Loai> getLoai() throws ClassNotFoundException, SQLException {
		return loaiDao.getLoai();
	}
}
