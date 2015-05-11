public class Main {
	public static void main(String[] args) {
		Server server = new Server();
		int port = 8000;

		server.constructPhonebook(); 

		if (args.length != 1) {
			System.out.println("Enter the desired port: ");
		}
		else {
			port = Integer.parseInt(args[0]);
		}

		try {
			server.start(port);
		} finally {
			server.savePhonebook();
		}
	}
}