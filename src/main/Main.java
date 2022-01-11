package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import com.github.cliftonlabs.json_simple.*;

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
		//Main.flows = Main.loadFlows();
		Main.flows = Main.loadFlowsFromJson();
		// Init Flows
		Main.printClients();
		Main.printAccounts();
		Main.executeFlows(flows, accountTable);
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

	// 1.2.3 Creation of the table account
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

	// 1.3.1 Adaptation of the table of accounts
	public static Hashtable<Integer, Account> loadAccountTable() {
		Hashtable<Integer, Account> accountTable = new Hashtable<Integer, Account>();
		Main.accounts.forEach(account -> {
			accountTable.put(account.getAccountNumber(), account);
		});
		return accountTable;
	}

	public static void printAccountTable() {
		// Transfer as List and sort it
		ArrayList<Map.Entry<?, Account>> l = new ArrayList<Entry<?, Account>>(Main.accountTable.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<?, Account>>() {

			public int compare(Map.Entry<?, Account> o1, Map.Entry<?, Account> o2) {
				return (int)o1.getValue().compareByBalanceTo(o2.getValue());
			}
		});

		System.out.println(l);
	}

	// 1.3.4 Creation of the flow array
	public static Collection<Flow> loadFlows() {
		var wrapper = new Object() {
			int flowsId = 1;
		};
		ArrayList<Flow> flowList = new ArrayList<Flow>();
		flowList.add(new Debit("First flow", wrapper.flowsId++, 50.00, 1, false, LocalDate.now().plusDays(2)));
		Main.accounts.forEach(account -> {
			if (account.getClass() == CurrentAccount.class) {
				flowList.add(new Credit("Credit", wrapper.flowsId++, 100.50, account.getAccountNumber(), false,
						LocalDate.now().plusDays(2)));
			}
			if (account.getClass() == SavingsAccount.class) {
				flowList.add(new Credit("Credit", wrapper.flowsId++, 1500.00, account.getAccountNumber(), false,
						LocalDate.now().plusDays(2)));
			}
		});
		flowList.add(new Transfert("Transfert", wrapper.flowsId++, 50.00, 2, false, LocalDate.now().plusDays(2), 1));
		return flowList;
	}

	// 1.3.5 Updating accounts
	public static void executeFlows(Collection<Flow> flows, Hashtable<Integer, Account> accountTable) {
		flows.forEach(flow -> {
			switch (flow.getClass().getSimpleName().toString()) {
			case "Credit":
				accountTable.get(flow.getTargetAccountNumber()).setBalance(flow);
				break;
			case "Debit":
				accountTable.get(flow.getTargetAccountNumber()).setBalance(flow);
				break;
			case "Transfert":
				Transfert transfert = (Transfert) flow;
				accountTable.get(transfert.getTargetAccountNumber()).setBalance(flow);
				accountTable.get(transfert.getIssuingAccountNumber()).setBalance(flow);
				break;
			default:
				break;
			}
		});

		Main.checkBelowZeroAccounts();
		Main.printAccountTable();
	}
	
	public static void checkBelowZeroAccounts() {
		Predicate<Account> belowZero = t -> t.getBalance() < 0;
		Main.accounts.stream().filter(belowZero).forEach(belowZeroAccount -> {
			System.out.println("Warning! Account n° " + belowZeroAccount.getAccountNumber() + "'s balance is below Zero!");
		});
	}
	
	//2.1 JSON file of flows
	public static Collection<Flow> loadFlowsFromJson() {
		Path jsonPath = Paths.get("./../ressource/flows.json");
		Json
		ArrayList<Flow> flows = new ArrayList<Flow>();
		
		return flows;
	}
}
