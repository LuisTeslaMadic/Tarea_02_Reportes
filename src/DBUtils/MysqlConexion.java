package DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConexion {
   
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL    = "jdbc:mysql://localhost:3306/reporte";
	private static final String USERNAME   = "root";
	private static final String PASWORD    = "esthefano123";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(">> Error en el driver : "+e);
		}
	}
	
	public static Connection getConexion() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASWORD);
		}catch(SQLException e) {
			System.out.println(">> Error en la conexión : "+e);
		}
		return con;
	}
}
