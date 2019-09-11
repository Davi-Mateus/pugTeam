package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {
	
	private Connection connection;
	
	public Connection conectar() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
				String url = "jdbc:mysql://localhost/dbcolegio";
				String user = "root";
				String password = "";
				
				connection = DriverManager.getConnection(url, user, password);
				return connection;
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
}
