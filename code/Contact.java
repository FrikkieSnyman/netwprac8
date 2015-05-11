import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Contact implements Serializable {
	public String name;
	public String number;

	public Contact() {
		;
	}

	public Contact(String n, String nb) {
		name = n;
		number = nb;
	}

	public String getInfo() {
		return name + ": " + number;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
    		return new StringBuffer("")
    		.append(this.name)
    		.append(": ")
    		.append(this.number).toString();
	}
}