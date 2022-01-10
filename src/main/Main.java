package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import component.Account;
import component.Client;
import component.Credit;
import component.CurrentAccount;
import component.Debit;
import component.Flow;
import component.SavingsAccount;
import component.Transfert;

//1.1.2 Creation of main class for tests
public class Main {
	
	private static Collection<Client> clients;
	private static Collection<Account> accounts;
	private static Hashtable<Integer, Account> accountTable;
	private static Collection<Flow> flows;
	
	public static void main(String[] args) {
		Main.clients = Main.loadClient(3);
		Main.accounts = Main.loadAccounts();
		Main.accountTable = Main.loadAccountTable();
		//Init Flows
		Main.printClients();
		Main.printAccounts();
		Main.printAccountTable();
	}
	
	public static Collection<Client> loadClient(int clientNumber) {
		ArrayList<Client> clientList = new ArrayList<Client>();
		for (int clientNum = 0; clientNum < clientNumber; clientNum++) {
			String newClientName = "name" + String.valueOf(clientNum + 1);
			String newClientFirstName = "firstName" + String.valueOf(clientNum + 1);
			Client client = new Client(newClientName, newClientFirstName);
			clientList.add(client);
		}
		return clientList;
	}
	
	public static void printClients() {
		Main.clients.stream().forEach(client -> {
			System.out.println(client.toString());
		});
	}
	
	//1.2.3 Creation of the table account
	public static Collection<Account> loadAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();
		Main.clients.forEach(client -> {
			String currentAccountLabel = "Current account";
			String savingsAccountLabel = "Savings account";
			Account currentAccount = new CurrentAccount(currentAccountLabel, client);
			Account savingsAccount = new SavingsAccount(savingsAccountLabel, client);
			accountList.add(currentAccount);
			accountList.add(savingsAccount);
		});
		return accountList;
	}
	
	public static void printAccounts() {
		Main.accounts.stream().forEach(account -> {
			System.out.println(account.toString());
		});
	}
	
	//1.3.1 Adaptation of the table of accounts
	public static Hashtable<Integer, Account> loadAccountTable() {
		Hashtable<Integer, Account> accountTable = new Hashtable<Integer, Account>();
		Main.accounts.forEach(account -> {
			accountTable.put(account.getAccountNumber(), account);
		});
		return accountTable;
	}
	
	public static void printAccountTable() {
		System.out.println(Main.accountTable);
	}
	
	//1.3.4 Creation of the flow array
	public static Collection<Flow> loadFlows() {
		var wrapper = new Object(){ int flowsId = 1; };
		ArrayList<Flow> flowList = new ArrayList<Flow>();
		flowList.add(new Debit("First flow", wrapper.flowsId++, 50.00, 1, false, LocalDate.now().plusDays(2)));
		Main.accounts.forEach(account -> {
			if (account.getClass() == CurrentAccount.class) {
				flowList.add(new Credit("Credit", wrapper.flowsId++, 100.50, account.getAccountNumber(), false, LocalDate.now().plusDays(2)));
			}
			if (account.getClass() == SavingsAccount.class) {
				flowList.add(new Credit("Credit", wrapper.flowsId++, 1500.00, account.getAccountNumber(), false, LocalDate.now().plusDays(2)));
			}
		});
		flowList.add(new Transfert("Transfert", wrapper.flowsId++, 50.00, 2, false, LocalDate.now().plusDays(2), 1));
		return flowList;
	}
}
