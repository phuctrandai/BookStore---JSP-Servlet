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
		// System.out.println("Connected !");

		connection = DriverManager
				.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLSach;user=sa;password=0946901161");
		// System.out.println("Connected to database!");
	}

	public void Disconnect() throws SQLException {
		connection.close();
	}

	public ResultSet getTable(String name) throws ClassNotFoundException, SQLException {
		ResultSet resultSet;

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + name);
		resultSet = statement.executeQuery();

		return resultSet;
	}

	public ResultSet executeQuery(String query, Object[] params) throws ClassNotFoundException, SQLException {
		PreparedStatement pStatement = connection.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			pStatement.setObject(i + 1, params[i]);
		}
		ResultSet result = pStatement.executeQuery();
		return result;
	}

	public int executeUpdate(String query, Object[] params) throws SQLException, ClassNotFoundException {
		PreparedStatement pStatement = connection.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			pStatement.setObject(i + 1, params[i]);
		}
		int result = pStatement.executeUpdate();
		return result;
	}

	public Object executeScalar(String query, Object[] params) throws SQLException, ClassNotFoundException {
		PreparedStatement pStatement = connection.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			pStatement.setObject(i + 1, params[i]);
		}
		ResultSet result = pStatement.executeQuery();
		Object sca = null;
		if (result.next()) {
			sca = result.getObject(1);
		}
		return sca;
	}
}
