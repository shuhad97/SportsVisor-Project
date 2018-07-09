package mainPackage;

import java.sql.*;


public class DBConnectionTest {
	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:User.db");

			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			//stmt.executeUpdate("INSERT INTO USER " + "VALUES ('z','z');");
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER;");
			
			while (rs.next()) {
				String username = rs.getString("User");
				String password = rs.getString("Password");

				System.out.println("username = " + username);
				System.out.println("password = " + password);
				
			}
			rs.close();
			stmt.close();
			c.close();
		}

		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}