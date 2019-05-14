package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

public class DBConnection {
	static Connection con = null;
//	static String url = "jdbc:mariadb://phj6525.cafe24.com/phj6525";
	static String url = "jdbc:mariadb://localhost:3306/phj6525";
	static String user = "phj6525";
	static String password = "rkswl7748@";
	
	//DB 연결 메서드
	public static Connection openConnection() throws SQLException, NamingException, ClassNotFoundException {
		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//DB 연결종료 메서드
	public static void closeConnection() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;
		}
	}
}