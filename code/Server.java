// Created by:
// Fredercik Hendrik Snyman 13028741
// Hugo Greyvenstein        13019989

import java.io.*;
import java.net.*;
import java.security.*;

public class Server{
	private static int port;
	private static IOManip io = new IOManip(System.out);

	public static void main(String[] args){
		int argc = args.length;
		int connections = 0;
		if (argc != 1) {
			System.out.println("Not enough actual parameters. java Server <port>");
			System.exit(0);
		}

		port = Integer.parseInt(args[0]);

		try{

			ServerSocket listener = new ServerSocket(port);
			Socket server;

			while (true){
			    server = listener.accept();
			    Client client = new Client(server,connections++);
			    System.out.println("New connection. Connection nr: " + connections);
			    Thread t = new Thread(client);
			    t.start();
			}
			
				// String htmlResponse = "<?xml version=\"1.0\"?><!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" \"http://www.wapforum.org/DTD/wml_1.1.xml\"><wml><template>    <do type=\"accept\" label=\"Back\"><prev/></do></template><card id=\"home\" title=\"The Octane Group\"><p align=\"center\">   <img src=\"images/octane.wbmp\" alt=\"[Octane Logo]\"/></p><p><big><b>T</b></big>he Octane Group is an exceptional team of software engineersimmediately available to ignite the productivity of your company's engineering projects.<br/><big><b>O</b></big>ur mission is to leverage our software developmentexperience with the latest technologies to help you reduce costs,create new sources of revenue, and increase profits.<br/><big><b>W</b></big>e have a proven track record in developing a wide rangeof software applications on a variety of platforms. From software for Internet ASPsto voiceXML processing to enterprise solutions, our experience with both existingand emerging technologies provides our clients with an unprecedented edge in software development.<br/><a href=\"assets.wml\">Assets</a><a href=\"benefits.wml\">Benefits</a><a href=\"experience.wml\">Experience</a><a href=\"clients.wml\">Clients</a><a href=\"team.wml\">Team</a><a href=\"contact.wml\">Contact</a><br/><small>The Octane Group, LLC &#169; 2001</small></p></card></wml>";
			
				// ServerSocket listener = new ServerSocket(port);
				// Socket connection = listener.accept(); 
				// PrintStream pout = new PrintStream(connection.getOutputStream());
				// pout.println("HTTP/1.1 200 OK\r\n");
				// pout.println("Content-Type: text/vnd.wap.wml\r\n");
				// pout.println("Connection: Close\r\n");
				// pout.println("Content-length: " + htmlResponse.length() + "\r\n");
				// pout.println("Cache-control: no-cache\r\n\r\n");
				// pout.println(htmlResponse);                         
				// pout.flush(); 
				// connection.shutdownOutput();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			io.setColor("reset");
		}

	}
}