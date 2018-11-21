package neximilian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Menu {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void init() throws SQLException, IOException {

		System.out.println("Welcome to Neximilian Message Application.\n");
		System.out.println("[1] Login.\n[2] Exit.");
		String input = br.readLine();

		while (!input.equals("1") && !input.equals("2")) {
			System.out.println("\nWrong input, please try again.");
			System.out.println("Please select action from below:");
			System.out.println("[1] Login.\n[2] Exit.");
			input = br.readLine();
		}

		if (input.equals("1")) {
			String role = UserAccount.login();
			if (role.equals("low")) {
				viewUserMenu();
			} else if (role.equals("medium")) {
				viewEditUserMenu();
			} else if (role.equals("high")) {
				viewEditDeleteUserMenu();
			} else if (role.equals("admin")) {
				adminMenu();
			}
		} else if (input.equals("2")) {
			System.out.println("Thank you, see you again.");
		}

	}

	public static void adminMenu() throws SQLException, IOException {
		Admin admin = new Admin();
		System.out.println("\nWelcome.\nPlease select action from below:");
		System.out.println("[1] Read your messages.\n[2] Compose a new message."
				+ "\n[3] Delete a message.\n[4] Users of the system." + "\n[5] Insert a new user.\n[6] Delete a user."
				+ "\n[7] Change a role of a user.\n[8] Change your password." + "\n[9] Logout");
		String input = br.readLine();
		boolean bool = true;

		while (bool != false) {

			if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
					&& !input.equals("5") && !input.equals("6") && !input.equals("7") && !input.equals("8")
					&& !input.equals("9")) {
				System.out.println("\nWrong input, please try again.");
				input = adminOptions();
			} else {
				switch (input) {
				case "1":
					admin.readMessages();
					input = adminOptions();
					break;
				case "2":
					admin.askAndSendMessage();
					input = adminOptions();
					break;
				case "3":
					admin.readMessages();
					System.out.println("\n");
					admin.askAndDeleteMessage();
					input = adminOptions();
					break;
				case "4":
					admin.showAllUsers();
					input = adminOptions();
					break;
				case "5":
					admin.askAndInsertUser();
					input = adminOptions();
					break;
				case "6":
					admin.showAllUsers();
					System.out.println("\n");
					admin.askAndDeleteUser();
					input = adminOptions();
					break;
				case "7":
					admin.showAllUsers();
					System.out.println("\n");
					admin.askAndChangeRole();
					input = adminOptions();
					break;
				case "8":
					admin.askAndChangePassword();
					input = adminOptions();
					break;
				case "9":
					System.out.println("Thank you, see you again.");
					bool = false;
				}
			}
		}
	}

	public static void viewUserMenu() throws SQLException, NumberFormatException, IOException {
		ViewUser vu = new ViewUser();
		System.out.println("Please select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message" + "\n[3] Logout");
		String input = br.readLine();
		boolean bool = true;

		while (bool != false) {
			if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
				System.out.println("\nWrong input, please try again.");
				input = viewOptions();
			} else {
				switch (input) {
				case "1":
					vu.readMessages();
					input = viewOptions();
					break;
				case "2":
					vu.askAndSendMessage();
					input = viewOptions();
					break;
				case "3":
					System.out.println("Thank you, see you again.");
					bool = false;
				}
			}
		}
	}

	public static void viewEditUserMenu() throws SQLException, NumberFormatException, IOException {
		ViewEditUser veu = new ViewEditUser();
		System.out.println("Please select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message" + "\n[3] Change password\n[4] Logout");
		String input = br.readLine();
		boolean bool = true;

		while (bool != false) {
			if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
				System.out.println("\nWrong input, please try again.");
				input = viewEditOptions();
			} else {
				switch (input) {
				case "1":
					veu.readMessages();
					input = viewEditOptions();
					break;
				case "2":
					veu.askAndSendMessage();
					input = viewEditOptions();
					break;
				case "3":
					veu.askAndChangePassword();
					input = viewEditOptions();
					break;
				case "4":
					System.out.println("Thank you, see you again.");
					bool = false;
				}
			}
		}

	}

	public static void viewEditDeleteUserMenu() throws SQLException, NumberFormatException, IOException {
		ViewEditDeleteUser vedu = new ViewEditDeleteUser();
		System.out.println("Please select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message"
				+ "\n[3] Delete a message\n[4] Change password\n[5] Logout");
		String input = br.readLine();
		boolean bool = true;

		while (bool != false) {
			if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
					&& !input.equals("5")) {
				System.out.println("\nWrong input, please try again.");
				input = viewEditDeleteOptions();
			} else {
				switch (input) {
				case "1":
					vedu.readMessages();
					input = viewEditDeleteOptions();
					break;
				case "2":
					vedu.askAndSendMessage();
					input = viewEditDeleteOptions();
					break;
				case "3":
					vedu.readMessages();
					System.out.println("\n");
					vedu.askAndDeleteMessage();
					input = viewEditDeleteOptions();
					break;
				case "4":
					vedu.askAndChangePassword();
					input = viewEditDeleteOptions();
					break;
				case "5":
					System.out.println("Thank you, see you again.");
					bool = false;

				}
			}
		}

	}

	public static String adminOptions() throws NumberFormatException, IOException {
		System.out.println("\nPlease select action from below:");
		System.out.println("[1] Read your messages.\n[2] Compose a new message."
				+ "\n[3] Delete a message.\n[4] Users of the system." + "\n[5] Insert a new user.\n[6] Delete a user."
				+ "\n[7] Change a role of a user.\n[8] Change your password." + "\n[9] Logout");
		String input = br.readLine();
		return input;
	}

	public static String viewOptions() throws NumberFormatException, IOException {
		System.out.println("\nPlease select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message" + "\n[3] Logout");
		String input = br.readLine();
		return input;
	}

	public static String viewEditOptions() throws NumberFormatException, IOException {
		System.out.println("\nPlease select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message" + "\n[3] Change password\n[4] Logout");
		String input = br.readLine();
		return input;
	}

	public static String viewEditDeleteOptions() throws NumberFormatException, IOException {
		System.out.println("\nPlease select action from below:");
		System.out.println("[1] Read your messages\n[2] Compose a new message"
				+ "\n[3] Delete a message\n[4] Change password\n[5] Logout");
		String input = br.readLine();
		return input;
	}

}
