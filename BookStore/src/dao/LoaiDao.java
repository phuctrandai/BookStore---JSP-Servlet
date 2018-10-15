package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Loai;

public class LoaiDao {
	private ConnectDB connectDB;
	
	public LoaiDao() {
		connectDB = new ConnectDB();
	}
	public ArrayList<Loai> getLoai() throws ClassNotFoundException, SQLException {
		connectDB.Connect();
		ArrayList<Loai> list = new ArrayList<Loai>();
		ResultSet result = connectDB.getTable("loai");
		
		while(result.next()) {
			Loai loai = new Loai(result.getString(1), result.getString(2));
			list.add(loai);
		}
		result.close();
		connectDB.Disconnect();
		return list;
	}
}
