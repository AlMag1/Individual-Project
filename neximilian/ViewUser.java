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

public class ViewUser extends User {

	ArrayList<Message> messages = new ArrayList<Message>();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public ViewUser() {
	}

	public ViewUser(String username, String password, String role, int id) {
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

	public void askAndSendMessage() throws SQLException, IOException, NumberFormatException {

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

}
