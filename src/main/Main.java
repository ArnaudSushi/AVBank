package main;

import java.util.ArrayList;
import java.util.Collection;

import component.Client;

//1.1.2 Creation of main class for tests
public class Main {
	private static Collection<Client> clients;
	
	public static void main(String[] args) {
		clients = new ArrayList<Client>();
		loadClient(3);
		printClients();
	}
	
	public static void loadClient(int clientNumber) {
		for (int clientNum = 0; clientNum < clientNumber; clientNum++) {
			String newClientName = "name" + String.valueOf(clientNum + 1);
			String newClientFirstName = "firstName" + String.valueOf(clientNum + 1);
			Client client = new Client(newClientName, newClientFirstName);
			clients.add(client);
		}
	}
	
	public static void printClients() {
		clients.stream().forEach(client -> {
			System.out.println(client.toString());
		});
	}
}
