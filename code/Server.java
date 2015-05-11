import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Server {
	public static Phonebook phonebook = null;

	public void start(int port) {
		try {
			ServerSocket server = new ServerSocket(port);

			while (true) {
				// System.out.println("Waiting for request");

				Socket socket = server.accept();

				Client client = new Client(socket, this);
				Thread t = new Thread(client);

				t.start();

				// System.out.println("Request revieved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			savePhonebook();
		}
	}

	public String getHTML(String fileName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String html = "";
		String temp = "";

		while ((temp = br.readLine()) != null) {
			html += temp;
		}

		return html;
	}

	public void print(String text) {
		System.out.println(text);
	}

	@SuppressWarnings("unchecked")
	public void constructPhonebook() {
		try {
			FileInputStream fileIn = new FileInputStream("phonebook.txt");
			ObjectInputStream in;

			phonebook = new Phonebook();

			if (fileIn.available() != 0) {
				in = new ObjectInputStream(fileIn);

				phonebook.contactList = (ArrayList<Contact>) in.readObject();

				in.close();
			}

			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Contact class not found");
			c.printStackTrace();
		}

		System.out.println(phonebook.getPhonebook());
	}

	@SuppressWarnings("unchecked")
	public void savePhonebook() {
		System.out.println(phonebook.getPhonebook());

		if (phonebook.isEmpty()) {
			return;
		}

		try {
			OutputStream fileOut = new FileOutputStream("phonebook.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(phonebook.contactList);

			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}