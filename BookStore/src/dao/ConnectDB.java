package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
	
	public Connection connection;
	
	public void Connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//System.out.println("Connected !");
		
		connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLSach;user=sa;password=0946901161");
		//System.out.println("Connected to database!");
	}
	
	public void Disconnect() throws SQLException {
		connection.close();
	}
	
	public ResultSet getTable(String name) throws ClassNotFoundException, SQLException {
		ResultSet resultSet;
		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + name);
		//statement.setString(1, name);
		resultSet = statement.executeQuery();
		
		return resultSet;
	}
}
