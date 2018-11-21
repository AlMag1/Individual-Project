package neximilian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteToFile {
	public static void logs(String user, String action) {

		try {
			String date = new SimpleDateFormat("dd/MM/yyy").format(Calendar.getInstance().getTime());
			String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
			BufferedWriter writer;
			BufferedReader reader;
			String fileName = new String("logs.txt");

			// Creates or uses the same file
			FileWriter file;
			file = new FileWriter(fileName, true);
			reader = new BufferedReader(new FileReader(fileName));
			writer = new BufferedWriter(file);

			// Creates file header if the file is empty
			if (reader.readLine() == null) {
				writer.append("Date\t\tTime\t\tUser\t\tAction");
				writer.newLine();
			}

			writer.newLine();
			writer.append(date + "\t" + time + "\t" + user + "\t\t" + action);

			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void messageLogs(String user, String message) {
		try {
			String date = new SimpleDateFormat("dd/MM/yyy").format(Calendar.getInstance().getTime());
			String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
			BufferedWriter writer;
			BufferedReader reader;
			String fileName = new String("Message_Logs.txt");

			// Creates or uses the same file
			FileWriter file;
			file = new FileWriter(fileName, true);
			reader = new BufferedReader(new FileReader(fileName));
			writer = new BufferedWriter(file);

			// Creates file header if the file is empty
			if (reader.readLine() == null) {
				writer.append("Date\t\tTime\t\tUser\t\tMessage");
				writer.newLine();
			}

			writer.newLine();
			writer.append(date + "\t" + time + "\t" + user + "\t\t" + message);

			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
