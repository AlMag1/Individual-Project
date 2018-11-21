package neximilian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserAccount {

	private static String userInput;
	private static ArrayList<User> alist = new ArrayList<User>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public ArrayList<User> getAlist() {
		return alist;
	}

	public void setAlist(ArrayList<User> alist) {
		UserAccount.alist = alist;
	}

	public static String login() throws SQLException, IOException {

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		String query = "select * from indi.users";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");
			int id = rs.getInt("id");
			alist.add(new User(username, password, role, id));

		}

		System.out.println("Please type your username.");
		userInput = br.readLine();

		System.out.println("Please type your password");
		String userPassword = br.readLine();

		boolean bool = false;
		String role = null;
		
		for (int i = 0; i < alist.size(); i++) {
			if (alist.get(i).getUsername().equals(userInput) && alist.get(i).getPassword().equals(userPassword)) {
				role = alist.get(i).getRole();
				bool = true;
				break;
			}
		}

		if (!bool) {
			System.out.println("Wrong username/password");
		}

		return role;
	}

	public static int returnUserId() throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		String query = "select * from indi.users";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");
			int id = rs.getInt("id");
			alist.add(new User(username, password, role, id));
		}

		int id = 0;
		for (int i = 0; i < alist.size(); i++) {
			if (alist.get(i).getUsername().equals(userInput)) {
				id = alist.get(i).getId();
				break;
			}
		}

		conn.close();
		return id;

	}

	public static String returnUsername() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		String query = "select * from indi.users";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");
			int id = rs.getInt("id");
			alist.add(new User(username, password, role, id));
		}

		String name = null;
		for (int i = 0; i < alist.size(); i++) {
			if (alist.get(i).getUsername().equals(userInput)) {
				name = alist.get(i).getUsername();
				break;
			}
		}
		conn.close();
		return name;

	}
}
