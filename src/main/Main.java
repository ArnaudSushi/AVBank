package main;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
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
		// Main.accounts = Main.loadAccounts();
		Main.accounts = Main.loadAccountsFromXML();
		Main.accountTable = Main.loadAccountTable();
		//Main.flows = Main.loadFlows();
		Main.flows = Main.loadFlowsFromJson(); // Init Flows
		Main.printClients(); Main.printAccounts();
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
				return (int) o1.getValue().compareByBalanceTo(o2.getValue());
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
			System.out.println(
					"Warning! Account n° " + belowZeroAccount.getAccountNumber() + "'s balance is below Zero!");
		});
	}

	// 2.1 JSON file of flows
	public static Collection<Flow> loadFlowsFromJson() {
		ArrayList<Flow> flows = new ArrayList<Flow>();
		JSONParser parser = new JSONParser();
		Path jsonPath = Paths.get(new File("").getAbsolutePath() + "/ressource/flows.json");
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(jsonPath.toFile()));
			JSONArray jsonArray = (JSONArray) jsonObj.get("flows");
			Iterator iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject flow = (JSONObject) iterator.next();

				int id = Integer.parseInt(flow.get("id").toString());
				double amount = (double) flow.get("amount");
				int targetAccountNumber = Integer.parseInt(flow.get("targetAccountNumber").toString());
				LocalDate date = LocalDate.parse(flow.get("date").toString());

				switch ((String) flow.get("comment")) {
				case "Debit":
					flows.add(new Debit("Debit", id, amount, targetAccountNumber, false, date));
					break;
				case "Credit":
					flows.add(new Credit("Credit", id, amount, targetAccountNumber, false, date));
					break;
				case "Transfert":
					int issuingAccountNumber = Integer.parseInt(flow.get("issuingAccountNumber").toString());
					flows.add(new Transfert("Transfert", id, amount, targetAccountNumber, false, date,
							issuingAccountNumber));
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(flows.get(0));
		return flows;
	}

	// 2.2 XML file of account
	public static Collection<Account> loadAccountsFromXML() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		Path xmlPath = Paths.get(new File("").getAbsolutePath() + "/ressource/accounts.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlPath.toFile());

			NodeList list = doc.getElementsByTagName("account");
			
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node node = list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String label = element.getElementsByTagName("label").item(0).getTextContent();
					int clientId = Integer.parseInt(element.getElementsByTagName("client").item(0).getTextContent());
					Client client = Main.clients.stream().filter(t -> t.getClientId() == clientId).iterator().next();
					switch (label) {
					case "Current account" :
						accounts.add(new CurrentAccount(label, client));
						break;
					case "Savings account" :
						accounts.add(new SavingsAccount(label, client));
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}
}
