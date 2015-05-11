// Andre Calitz 13020006
// Frikkie Snyman 13028741

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client implements Runnable {
	private final Scanner reader;
	private final PrintWriter output;
	private final Socket socket;

	private Server server = null;

	public Client(Socket incommingSocket, Server host) throws IOException{
		socket = incommingSocket;

		output = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
		reader = new Scanner(new InputStreamReader(socket.getInputStream()));

		server = host;
	}

	public void run() {
		try {
			handleRequest();
			server.savePhonebook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeHTML(String page) {
		String htmlResponse = "";

		try {
			htmlResponse = server.getHTML(page);

			if (page.equals("index.wml")) {
				htmlResponse = htmlResponse.replace("replaceMe", server.phonebook.getPhonebook());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		output.write("HTTP/1.1 200 OK\r\n");
		output.write("Content-Type: text/vnd.wap.wml\r\n");
		output.write("Connection: Close\r\n");
		output.write("Content-length: " + htmlResponse.length() + "\r\n");
		output.write("Cache-control: no-cache\r\n\r\n");
		output.write(htmlResponse);
		output.flush();
	}

	public void writeFind(String msg) {
		String htmlResponse = "";

		try {
			htmlResponse = server.getHTML("index.wml");
			
			htmlResponse = htmlResponse.replace("replaceMe", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		output.write("HTTP/1.1 200 OK\r\n");
		output.write("Content-Type: text/vnd.wap.wml\r\n");
		output.write("Connection: Close\r\n");
		output.write("Content-length: " + htmlResponse.length() + "\r\n");
		output.write("Cache-control: no-cache\r\n\r\n");
		output.write(htmlResponse);
		output.flush();
	}

	public void handleRequest() throws Exception {
		String requestType = reader.next();
		String request = reader.next();

		server.print(requestType);
		server.print(request);

		if (request.equals("/")) {
			writeHTML("index.wml");
		}
		else if (request.equals("/add.wml")) {
			writeHTML("add.wml");
		}
		else if (request.equals("/find.wml")) {
			writeHTML("find.wml");
		}
		else if (request.equals("/update.wml")) {
			writeHTML("update.wml");
		}
		else if (request.equals("/delete.wml")) {
			writeHTML("delete.wml");
		}
		else if (request.contains("?")) {
			StringTokenizer tokenizer = new StringTokenizer(request, "\\?");

			String function = tokenizer.nextToken();
			String details = tokenizer.nextToken();

			function = function.substring(1);		

			if (function.equals("add")) {
				String name = details.substring(5, details.indexOf("&"));
				String number = details.substring(details.indexOf("&") + 8, details.length());

				server.print("Name: " + name);
				server.print("Number: " + number);

				server.phonebook.addContact(name, number);
			}
			else if (function.equals("find")) {
				String keyword = details.substring(5, details.length());

				server.print("Keyword: " + keyword);

				writeFind(server.phonebook.findContact(keyword));
				return;
			}
			else if (function.equals("update")) {
				String name = details.substring(5, details.indexOf("&"));
				String update = details.substring(details.indexOf("&") + 8, details.length());

				server.print("Name: " + name);
				server.print("Update: " + update);

				server.phonebook.updateContact(name, update);
			}
			else if (function.equals("delete")) {
				String keyword = details.substring(5, details.length());

				server.print("Keyword: " + keyword);

				server.phonebook.deleteContact(keyword);
			}

			writeHTML("index.wml");
		}
		else {
			// server.print("Ignoring");
			writeHTML("index.wml");
		}
	}
}