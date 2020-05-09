package Ex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class databaseConnection {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String url = "jdbc:mariadb://18.216.149.81:54321/testdb?autoReconnect=true&useSSL=false";
		String user = "alunos";
		String password = "senhadealunos";

		try {

			con = DriverManager.getConnection(url, user, password);

			st = con.createStatement();

			rs = st.executeQuery("SHOW TABLES");

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(databaseConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {

				if (rs != null) {
					rs.close();
				}

				if (st != null) {
					st.close();
				}

				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(databaseConnection.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
}
