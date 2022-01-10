package main;

import java.util.ArrayList;
import java.util.Collection;

import component.Account;
import component.Client;
import component.CurrentAccount;
import component.SavingsAccount;

//1.1.2 Creation of main class for tests
public class Main {
	private static Collection<Client> clients;
	private static Collection<Account> accounts;
	
	public static void main(String[] args) {
		Main.clients = new ArrayList<Client>();
		Main.accounts = new ArrayList<Account>();
		Main.loadClient(3);
		Main.printClients();
		Main.loadAccounts();
		Main.printAccounts();
	}
	
	public static Collection<Client> loadClient(int clientNumber) {
		for (int clientNum = 0; clientNum < clientNumber; clientNum++) {
			String newClientName = "name" + String.valueOf(clientNum + 1);
			String newClientFirstName = "firstName" + String.valueOf(clientNum + 1);
			Client client = new Client(newClientName, newClientFirstName);
			Main.clients.add(client);
		}
		return clients;
	}
	
	public static void printClients() {
		Main.clients.stream().forEach(client -> {
			System.out.println(client.toString());
		});
	}
	
	public static Collection<Account> loadAccounts() {
		Main.clients.forEach(client -> {
			String currentAccountLabel = "Current account";
			String savingsAccountLabel = "Savings account";
			Account currentAccount = new CurrentAccount(currentAccountLabel, client);
			Account savingsAccount = new SavingsAccount(savingsAccountLabel, client);
			Main.accounts.add(currentAccount);
			Main.accounts.add(savingsAccount);
		});
		return Main.accounts;
	}
	
	public static void printAccounts() {
		Main.accounts.stream().forEach(account -> {
			System.out.println(account.toString());
		});
	}
}
