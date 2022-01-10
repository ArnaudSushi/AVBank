package component;

//1.2.1 Creation of the account class
public abstract class Account {
	
	protected String label;
	protected double balance;
	protected int accountNumber;
	protected static int totalAccountNumber;
	protected Client client;
	
	public Account(String label, Client client) {
		totalAccountNumber++;
		this.label = label;
		this.client = client;
		this.accountNumber = totalAccountNumber;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(Flow flow) {
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public static int getTotalAccountNumber() {
		return totalAccountNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String toString() {
		return ("Account n°" + String.valueOf(this.accountNumber)
				+ ", "+ this.label
				+ ", balance : " + String.valueOf(this.balance)
				+ ", client : " + this.client);
	}
}
