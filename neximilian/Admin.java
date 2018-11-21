package neximilian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Admin extends User {

	ArrayList<Message> messages = new ArrayList<Message>();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public Admin() {

	}

	public Admin(String username, String password, String role, int id) {
		super(username, password, role, id);
	}

	public void readMessages() throws SQLException {
		int userId = UserAccount.returnUserId();
		String username = UserAccount.returnUsername();
		String query = "select body,username,date,messages.id\r\n" + "from indi.messages,indi.users\r\n"
				+ "where messages.sender = users.id and messages.receiver =" + userId + ";";

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		String message = null;

		while (rs.next()) {
			message = rs.getString("body");
			String sender = rs.getString("username");
			String date = rs.getString("date");
			int id = rs.getInt("id");
			messages.add(new Message(date, message, id, new User(sender)));
		}

		if (message == null) {
			System.out.println("There are now messages");
		} else {
			for (Message msg : messages) {
				System.out.println(msg);
			}
		}

		WriteToFile.logs(username, "Read a message");

		conn.close();
	}

	public void askAndSendMessage() throws SQLException, NumberFormatException, IOException {

		System.out.println("Please type the message.");
		String message = br.readLine();
		System.out.println("Please type the id of the receiver.");

		int intReceiver = 0;

		boolean bool = true;
		while (bool) {
			String receiver = br.readLine();
			if (!receiver.matches("[\\d]+")) {
				System.out.println("Wrong input, please try again.");
			} else {
				intReceiver = Integer.parseInt(receiver);

				int maxLength = 250;
				while (message.length() > maxLength) {
					System.out.println("You have reached the limit of 250 characters.\nPlease type again.");
					message = br.readLine();
				}

				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root",
						"admin");
				Statement stmt = null;
				String query = "select users.id from indi.users;";
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				ArrayList<Integer> idList = new ArrayList<Integer>();
				int id = 0;
				while (rs.next()) {
					id = rs.getInt("id");
					idList.add(id);
				}

				if (!idList.contains(intReceiver) && id != 0) {
					System.out.println("User doesn't exist, please type correct id.");

				} else {
					sendMessage(message, intReceiver);
					bool = false;
				}

			}

		}

	}

	public void sendMessage(String message, int receiver) throws SQLException {
		String date = new SimpleDateFormat("yyy/MM/dd").format(Calendar.getInstance().getTime());
		String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		int userId = UserAccount.returnUserId();
		String username = UserAccount.returnUsername();

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;

		String query = "insert into indi.messages values(null," + "'" + message + "'" + "," + userId + "," + "'"
				+ receiver + "'" + "," + "'" + date + " " + time + "');";

		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("Message sent");
		WriteToFile.logs(username, "Sended a message");
		WriteToFile.messageLogs(username, message);

		conn.close();
	}

	public void askAndChangePassword() throws SQLException, IOException {

		System.out.println("Please type the new password");

		String pass = br.readLine();
		changePassword(pass);
	}

	public void changePassword(String password) throws SQLException {
		int userId = UserAccount.returnUserId();
		String username = UserAccount.returnUsername();
		String query = "update indi.users set users.password =" + "'" + password + "'" + " where users.id = " + userId
				+ ";";

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("Password succesfully updated");
		WriteToFile.logs(username, "Changed password");

		conn.close();
	}

	public void askAndDeleteMessage() throws SQLException, NumberFormatException, IOException {
		int userId = UserAccount.returnUserId();
		System.out.println("Please type the id of the message that you want to delete");
		int messageId = 0;
		boolean bool = true;
		while (bool) {
			String idMessage = br.readLine();
			if (!idMessage.matches("[\\d]+")) {
				System.out.println("Wrong input, please type again.");
			} else {
				messageId = Integer.parseInt(idMessage);
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root",
						"admin");
				Statement stmt = null;
				String query = "select messages.id from indi.messages where messages.receiver = " + userId + ";";
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				int id = 0;
				ArrayList<Integer> idList = new ArrayList<Integer>();
				while (rs.next()) {
					id = rs.getInt("id");
					idList.add(id);
				}

				if (!idList.contains(messageId) && id != 0) {
					System.out.println("Message doesn't exist, please try again with correct id.");

				} else {
					deleteMessage(messageId);
					bool = false;
				}

			}
		}

	}

	public void deleteMessage(int id) throws SQLException {
		int userId = UserAccount.returnUserId();
		String username = UserAccount.returnUsername();
		String query = "delete from indi.messages where messages.id = " + id + " and messages.receiver = " + userId
				+ ";";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("Message successfully deleted");
		WriteToFile.logs(username, "Deleted a message");

		conn.close();

	}

	public void showAllUsers() throws SQLException {
		String name = UserAccount.returnUsername();
		String query = "select * from indi.users";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String role = rs.getString("role");
			System.out.println("User id: " + id + ", Username: " + username + ", Role: " + role);
		}
		WriteToFile.logs(name, "Checked list of users");
		conn.close();

	}

	public void askAndInsertUser() throws SQLException, IOException {

		System.out.println("Please type the username of the new user");
		String username = br.readLine();
		System.out.println("Please type the password of the new user");
		String password = br.readLine();
		System.out.println("Please type the role of the new user. \nAccepted roles: [low - medium - high]");
		String role = br.readLine();
		while (!role.equals("low") && !role.equals("medium") && !role.equals("high")) {
			System.out.println("Wrong type of role, please try again.\nAccepted roles: [low - medium - high]");
			role = br.readLine();
		}

		insertUser(username, password, role);
	}

	public void insertUser(String username, String password, String role) throws SQLException {
		String name = UserAccount.returnUsername();
		String query = "insert into indi.users values(null," + "'" + username + "'" + "," + "'" + password + "'" + ","
				+ "'" + role + "');";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("User successfully added");
		WriteToFile.logs(name, "Inserted a user");

		conn.close();
	}

	public void askAndDeleteUser() throws SQLException, NumberFormatException, IOException {

		System.out.println("Please type the id of the user you want to delete");

		int idUser = 0;
		boolean bool = true;

		while (bool) {
			String userId = br.readLine();
			if (!userId.matches("[\\d]+")) {
				System.out.println("Wrong input, please try again.");
			} else {
				idUser = Integer.parseInt(userId);
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root",
						"admin");
				Statement stmt = null;
				String query = "select users.id from indi.users;";
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				ArrayList<Integer> idList = new ArrayList<Integer>();
				int id = 0;
				while (rs.next()) {
					id = rs.getInt("id");
					idList.add(id);
				}

				if (!idList.contains(idUser) && id != 0) {
					System.out.println("Wrong input, please try again");
				} else {
					deleteUser(idUser);
					bool = false;
				}
			}
		}
	}

	public void deleteUser(int id) throws SQLException {
		String name = UserAccount.returnUsername();
		String query = "delete from indi.users where users.id =" + id + ";";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("User successfully deleted");
		WriteToFile.logs(name, "Deleted a user");

		conn.close();
	}

	public void askAndChangeRole() throws SQLException, NumberFormatException, IOException {
		System.out.println("Please type the new role of the user");
		String role = br.readLine();
		while (!role.equals("low") && !role.equals("medium") && !role.equals("high")) {
			System.out.println("Wrong type of role, please try again.\nAccepted roles: [low - medium - high]");
			role = br.readLine();
		}

		System.out.println("Please type the id of user that you want to change the role");

		int idUser = 0;
		boolean bool = true;

		while (bool) {
			String userId = br.readLine();
			if (!userId.matches("[\\d]+")) {
				System.out.println("Wrong input please try again.");
			} else {
				idUser = Integer.parseInt(userId);
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root",
						"admin");
				Statement stmt = null;
				String query = "select users.id from indi.users;";
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				ArrayList<Integer> idList = new ArrayList<Integer>();
				int id = 0;
				while (rs.next()) {
					id = rs.getInt("id");
					idList.add(id);
				}

				if (!idList.contains(idUser) && id != 0) {
					System.out.println("Wrong input please try again.");
				} else {
					changeRole(role, idUser);
					bool = false;
				}
			}
		}
	}

	public void changeRole(String role, int id) throws SQLException {
		String name = UserAccount.returnUsername();
		String query = "update indi.users set users.role =" + "'" + role + "'" + "where users.id =" + id + ";";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/indi?useSSL=false", "root", "admin");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(query);

		System.out.println("Succesfully changed the role of the user");
		WriteToFile.logs(name, "Changed role to user");

		conn.close();
	}

}
