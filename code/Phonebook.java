// Andre Calitz 13020006
// Frikkie Snyman 13028741

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Phonebook implements Serializable {
	public ArrayList<Contact> contactList = null;

	public Phonebook() {
		contactList = new ArrayList<Contact>();
	}

	@Override
	public String toString() {
		StringBuffer returnvalue = new StringBuffer("");

		Iterator iterator = contactList.iterator();
		Contact current = null;

		while (iterator.hasNext()) {
			current = (Contact) iterator.next();

			returnvalue.append(current.toString());
		}

		return returnvalue.toString();
	}

	public boolean isEmpty() {
		if (contactList.size() == 0) {
			return true;
		}

		return false;
	}

	public String getPhonebook() {
		String returnString = "";

		if (contactList.size() == 0) {
			returnString = "The Phonebook is currently empty...\n";

			return returnString;
		}

		Iterator iterator = contactList.iterator();
		Contact current = null;

		returnString += "<table><tr><td>Name</td><td>Number</td></tr>";

		while (iterator.hasNext()) {
			current = (Contact) iterator.next();
			returnString += "<tr><td>" + current.getName() + "</td><td>" + current.getNumber() + "</td></tr>";
			// returnString = returnString + current.getInfo() + "\n";
		}

		returnString += "</table>";

		return returnString;
	}

	public void addContact(String n, String nb) {
		contactList.add(new Contact(n, nb));
	}

	public String findContact(String keyword) {
		boolean found = false;
		String returnString = "";
		
		Iterator iterator = contactList.iterator();
		Contact current = null;

		returnString += "<table><tr><td>Name</td><td>Number</td></tr>";

		while (iterator.hasNext()) {
			current = (Contact) iterator.next();

			if (current.name.equals(keyword) || current.number.equals(keyword)) {
				returnString += "<tr><td>" + current.getName() + "</td><td>" + current.getNumber() + "</td></tr>";

				found = true;
			}
		}

		returnString += "</table>";
		
		if (!found) {
			returnString = keyword + " was not found in the phonebook";
		}

		return returnString;
	}

	public boolean updateContact(String name, String update) {
		boolean updated = false;
		Iterator iterator = contactList.iterator();
		Contact current = null;

		while (iterator.hasNext()) {
			current = (Contact) iterator.next();

			if (current.name.equals(name)) {
				updated = true;

				current.name = update;
			}
		}

		return updated;
	}

	public boolean deleteContact(String keyword) {
		boolean deleted = false;

		Iterator iterator = contactList.iterator();
		Contact current = null;

		while (iterator.hasNext()) {
			current = (Contact) iterator.next();

			if (current.name.equals(keyword) || current.number.equals(keyword)) {
				iterator.remove();
				deleted = true;
				break;
			}
		}

		return deleted;
	}
}